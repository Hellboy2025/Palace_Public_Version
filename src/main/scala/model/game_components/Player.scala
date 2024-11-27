package model.game_components
import model.simulation_components.Pile
import javax.lang.model.element.Element
import scala.compiletime.ops.string
import java.security.Guard
import javax.smartcardio.Card
import model.simulation_components.PlayerOrder.playersNames
import scala.util.control.Breaks.{break, breakable}
import scala.annotation.retains
import scala.compiletime.ops.double // does not show up on our current Palace as of 172154APR24 (curious...)

/******* PLAYER *******/
class Player(private val name: String) {
    private var guard_strat : Guard_strategy = new Guard_strategy_Default
    private var move_strat : Move_strategy = new Move_strategy_Default
    private var hand :List[Cards] = List()
    private var guard :List[Cards] = List()
    private var hiddenGuard :List[Cards] = List()
    private var totalCardCount :Int = 0 // this is the initial card total before cards have been dealt (i.e. the totalCardCount for the initial configuration); currently unused
    
    /**
      * This abstract class holds the Guard Strategy
      */
    abstract class Guard_strategy{
      def pick_guard :Unit ={
        ???
      }
      def show :String ={
        ???
      }
    }
    /**
      * This class extends Guard strategy and has its own method of picking the guard. This holds the default cution of picking the first 3 cards in the players hand. 
      */
    class Guard_strategy_Default extends Guard_strategy{
      override def pick_guard: Unit = pick_guard_default
      override def show: String = return "First 3 Cards"
    }
    /**
      * This class extends Guard strategy and has its own method of picking the guard by finding the 3 highest value cards in the player hand. 
      */
    class Guard_strategy_High_Guard extends Guard_strategy{
      override def pick_guard: Unit = pick_guard_high
      override def show: String = return "Strong Guard"
    }
    /**
      * This class is for the Low Guard strategy where the player will pick the lowest cards to be the guard.
      */
    class Guard_strategy_Low_Guard extends Guard_strategy{
      override def pick_guard: Unit = pick_guard_low
      override def show: String = return "Weak Guard"
    }
    /**
      * This abstract class holds the Move Strategy
      */
    abstract class Move_strategy{
      def pick_card(source: String):List[Cards]={
        ???
      }
      def show: String={
        ???
      }
    }
    /**
      * This class extends Move strategy and has the Dedault move strategy which is to find the first valid card from left to right
      */
    class Move_strategy_Default extends Move_strategy{
      override def pick_card(source:String): List[Cards] = pick_card_default(source=source)
      override def show: String = return "First Valid"
    }
    /**
      * This class extends Move stategy and has the Highest card move strategy whihc plays the highest point card in the players hand.
      */
    class Move_strategy_Highest_Card extends Move_strategy{
      override def pick_card(source:String): List[Cards] = pick_card_Highest(source = source)
      override def show: String = return "Highest Option"
    }
    
    /**
      * 
      *
      * @param strat This value can be the integer 1 or 2 if it 1 the Move strategy will be set to the default move strategy and if the number is 2 then the do move is set to the highest option strategy.
      */
    def Set_Mov_Strat(strat:Int):Unit={
      if strat == 1 then{
        move_strat= new Move_strategy_Default
      }
      else if strat == 2 then{
        move_strat= new Move_strategy_Highest_Card
      }
    }
    /**
      * 
      *
      * @param strat This value can be the intiger 3 or 4 if it is 3 the guard strategy is set to the deafault guard strategy and if the value is 4 the gaurd strategy is set to Strong Guard strategy.
      */
    def Set_Guard_Strat(strat:Int):Unit={
      if strat == 3 then{
        guard_strat=new Guard_strategy_Default
      }
      else if strat == 4 then{
        guard_strat= new Guard_strategy_High_Guard
      }
      else if strat == 5 then{
        guard_strat = new Guard_strategy_Low_Guard
      }
    }
    def show_strategies:String={
      var result = f"${name}: ${move_strat.show}, ${guard_strat.show}\n"
      return result
    }



    /**
      * 
      *
      * @return This returns the string to be show the hand of the player for the Show_game_area for GUI
      */
    def get_hand_for_show_GUI: String ={
      if get_hand_count == 0 then{
        return " "
      }
      val sb = new StringBuilder("")
      var last_card=hand.last
      for i <- hand do{
        if i == last_card then {
          sb++=f"${i.card_number}${i.card_suit}"
        }
        else{
          sb++=f"${i.card_number}${i.card_suit}, "
        }
      }
      sb++=""
      return sb.toString()
    }
    /**
      * 
      *
      * @return This returns the string to be show the guard of the player for the Show_game_area for GUI
      */
    def get_guad_for_show_GUI: String ={
      val sb = new StringBuilder("")
      if get_guard_count == 0 then{
        return " "
      }
      var last_card= guard.last
      for i <- guard do{
        if i== last_card then{
          sb++=f"${i.card_number}${i.card_suit}"
        }
        else{
          sb++=f"${i.card_number}${i.card_suit}, "
        }
      }
      sb++=""
      return sb.toString()
    }
    /**
      * 
      *
      * @return This returns the string to be show the hidden guard of the player for the Show_game_area for GUI
      */
    def get_hiddem_Guard_for_show_GUI: String ={
      val sb = new StringBuilder("")
      if get_hidden_Guard_count == 0 then{
        return " "
      }
      var last_card=hiddenGuard.last
      for i <- hiddenGuard do{
        if i == last_card then{
          sb++=f"${i.card_number}${i.card_suit}"
        }
        else{
        sb++=f"${i.card_number}${i.card_suit}, "
        }
      }
      sb++=""
      return sb.toString()
    }



    /**
      * 
      *
      * @return This returns the string to be show the hand of the player for the Show_game_area
      */
    def get_hand_for_show: String ={
      if get_hand_count == 0 then{
        return "List()"
      }
      val sb = new StringBuilder("List(")
      var last_card=hand.last
      for i <- hand do{
        if i == last_card then {
          sb++=f"Cards(${i.card_number}${i.card_suit})"
        }
        else{
          sb++=f"Cards(${i.card_number}${i.card_suit}),"
        }
      }
      sb++=")"
      return sb.toString()
    }
    /**
      * 
      *
      * @return This returns the string to be show the guard of the player for the Show_game_area
      */
    def get_guad_for_show: String ={
      val sb = new StringBuilder("List(")
      if get_guard_count == 0 then{
        return "List()"
      }
      var last_card= guard.last
      for i <- guard do{
        if i== last_card then{
          sb++=f"Cards(${i.card_number}${i.card_suit})"
        }
        else{
          sb++=f"Cards(${i.card_number}${i.card_suit}),"
        }
      }
      sb++=")"
      return sb.toString()
    }
    /**
      * 
      *
      * @return This returns the string to be show the hidden guard of the player for the Show_game_area
      */
    def get_hiddem_Guard_for_show: String ={
      val sb = new StringBuilder("List(")
      if get_hidden_Guard_count == 0 then{
        return "List()"
      }
      var last_card=hiddenGuard.last
      for i <- hiddenGuard do{
        if i == last_card then{
          sb++=f"Cards(${i.card_number}${i.card_suit})"
        }
        else{
        sb++=f"Cards(${i.card_number}${i.card_suit}),"
        }
      }
      sb++=")"
      return sb.toString()
    }
    /**
      * 
      *
      * @return This returns the Players hand.
      */
    def get_hand: List[Cards]={
      
      return hand
    }
    /** Gets the guard of the Player */
    def get_guard: List[Cards]={
      return guard
    }
    /** Gets the hiden guard of the Player */
    def get_hidden_Guard: List[Cards]={
      return hiddenGuard
    }
    /** Gets the amount of cards in the hand */
    def get_hand_count: Int ={
      return hand.length
    }
    /** Gets the amount of cards left in guard */
    def get_guard_count: Int ={
      return guard.length
    }
    /** Gets the amount of cards left in the hiden guard  */
    def get_hidden_Guard_count: Int={
      return hiddenGuard.length
    }
    /** Gets the total amount of cards the player has hand, guard, hidden guard */
    def get_total_card_count: Int={
      var get_total_card_count= hand.length + hiddenGuard.length + guard.length
      return totalCardCount
    }
    /** Gets the name of the Player */
    def get_name: String ={
      return name
    }
    /** Adds card to the Hidden Guard */
    def add_hidden_guard(card : Cards) : Unit ={
      hiddenGuard= hiddenGuard :+ card
    }
    /** Adds a card to the Guard (thi*/
    def add_guard(card : Cards) : Unit ={
      guard= guard :+ card
    }
    def remove_guard(amount: Int):Unit={  //this is used only for testing
      guard=guard.drop(amount)
    }
    /** Removes card from guard */
    def remove_hidden_guard(amount:Int):Unit={ //This is used for testing 
      hiddenGuard=hiddenGuard.drop(amount)
    }
    /** Player picks card for his guard.*/
    def pick_guard_default: Unit={
      for i <-  0 to 2 do{
        guard= guard:+ hand(i) 
      }
      hand=hand.drop(3)
    }

    /** this function will enable the player in question to pick the 3 highest cards for their guard **/
    def pick_guard_high: Unit = {
      implicit val handOrdering: Ordering[Cards] = Ordering.by(_.get_point)

      hand = hand.sorted.reverse

      for i <-  0 to 2 do {
          guard = guard :+ hand(i) 
      }
        
      hand = hand.drop(3)
    }
    /**
      * This function picks the 3 lowest cards from the players hand and placing in the gaurd this is the function for pic card in the Low Guard class.
      */
    def pick_guard_low: Unit ={
      implicit val handOrdering: Ordering[Cards] = Ordering.by(_.get_point)
      hand= hand.sorted
      for i <- 0 to 2 do {
        guard = guard :+ hand(i)
      }

      hand = hand.drop(3)
    }

    /** Add cards to the hand */
    def add_hand(card : Cards): Unit ={
      hand= hand :+ card
    }
    /** This function will move a card from the hand to the Pile */
    def remove_card(number_of_cards:Int): Unit ={
      hand=hand.drop(number_of_cards)
    }
    

    /**
      * 
      *
      * @param source This is a string that will set the target of where the player will pick a card from.
      * @return This will retun the Highets value card that the player has in his hand or the Pile if there is no valid card to play. 
      */
    def pick_card_Highest(source: String):List[Cards] ={
      var target: List[Cards]=List()
      var played_cards:List[Cards]=List()
      implicit val handOrdering: Ordering[Cards] = Ordering.by(_.get_point)
      if source == "Hand" then{
        target = hand.sorted.reverse
      }
      else if source == "Guard" then{
        target = guard.sorted.reverse
      }
      else if source =="Hidden Guard" then{
        target = hiddenGuard
      }

      
      
      breakable {
        for card <- target do{
          
          if Pile.size_of_pile ==0  then{
            var card=target(0)
            played_cards=played_cards:+target(0)
            
            if source == "Hand" then{
              hand=target.filter(_ != card)
            }
            else if source == "Guard" then{
              guard= target.filter(_ != card)
            }
            else if source =="Hidden Guard" then{
              hiddenGuard = target.filter(_ != card)
            }
            break()
          }
          else if source == "Hidden Guard" then{
            if card.get_point >= Pile.get_top_card.get_point || card.get_point == 2 then{
              played_cards=played_cards:+card
              hiddenGuard = target.filter(_ != card)

            }
            break()
        
          }
          else if card.get_point == 2 then{
            played_cards=played_cards:+card
            
            if source == "Hand" then{
              hand=target.filter(_ != card)
            }
            else if source == "Guard" then{
              guard= target.filter(_ != card)
            }
            else if source =="Hidden Guard" then{
              hiddenGuard = target.filter(_ != card)
            }
            break()
          }
          else if card.get_point >= Pile.get_top_card.get_point then{
            played_cards=played_cards:+card
            if source == "Hand" then{
              hand=target.filter(_ != card)
            }
            else if source == "Guard" then{
              guard= target.filter(_ != card)
            }
            else if source =="Hidden Guard" then{
              hiddenGuard = target.filter(_ != card)
            }
            break()
          }
        }
      }
      
      
      if played_cards.isEmpty then{
        var cards=Pile.pickup_pile
        Pile.clean_pile
        return cards  
      }
      else{
        return played_cards
      }
      
    }

    /** This method will pick a Card to send to the Pile 
      * @param from This parameter is meant to be what hand is the player going to play a card from aka the hand, guard, hidden guard. (to get the you can call one of the methods in the Class)
      * This method will pick a card by comparing the card on the top of the pile to the cards from the given hand, it will pick the first valid card including the reset card of value 2.
      */
    def pick_card_default(source: String):List[Cards] ={
      var target: List[Cards]=List()
      var played_cards:List[Cards]=List()
      if source == "Hand" then{
        target = hand
      }
      else if source == "Guard" then{
        target = guard
      }
      else if source =="Hidden Guard" then{
        target = hiddenGuard
      }
      
      breakable {
        for card <- target do{
          
          if Pile.size_of_pile ==0  then{
            var card=target(0)
            played_cards=played_cards:+target(0)
            
            if source == "Hand" then{
              hand=target.filter(_ != card)
            }
            else if source == "Guard" then{
              guard= target.filter(_ != card)
            }
            else if source =="Hidden Guard" then{
              hiddenGuard = target.filter(_ != card)
            }
            break()
          }
          else if source == "Hidden Guard" then{
            if card.get_point >= Pile.get_top_card.get_point || card.get_point == 2 then{
              played_cards=played_cards:+card
              hiddenGuard = target.filter(_ != card)

            }
            break()
        
          }
          else if card.get_point == 2 then{
            played_cards=played_cards:+card
            
            if source == "Hand" then{
              hand=target.filter(_ != card)
            }
            else if source == "Guard" then{
              guard= target.filter(_ != card)
            }
            else if source =="Hidden Guard" then{
              hiddenGuard = target.filter(_ != card)
            }
            break()
          }
          else if card.get_point >= Pile.get_top_card.get_point then{
            played_cards=played_cards:+card
            if source == "Hand" then{
              hand=target.filter(_ != card)
            }
            else if source == "Guard" then{
              guard= target.filter(_ != card)
            }
            else if source =="Hidden Guard" then{
              hiddenGuard = target.filter(_ != card)
            }
            break()
          }
        }
      }
      
      
      if played_cards.isEmpty then{
        var cards=Pile.pickup_pile
        Pile.clean_pile
        return cards  
      }
      else{
        return played_cards
      }
      
    }
    /**
      * This fuction makes the Player pick up the pile and the Pile gets emptied.
      */
    def pickup_pile_player: Unit = {
      hand=hand:::Pile.pickup_pile
    }
  
    def get_guard_strat:Guard_strategy ={
      return guard_strat
    } 
    def get_mov_strat:Move_strategy ={
      return move_strat
    }

  }

    

