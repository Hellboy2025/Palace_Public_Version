package model.game_components
import scala.collection.mutable.Stack 
import model.game_components.Cards
import scala.compiletime.ops.string
import model.game_components.Player
import scala.util.Random
/** The cards Players will hold
  *
  * @constructor This Object handles the Deck and all of its actions.
  *  
  */
object Deck {
    var shuffle_mode: Int = 0
    private val suits : List[String] = List("S","H","C","D")
    private val numbers :List[String]=List("A","K","Q","J","10","9","8","7","6","5","4","3","2")
    private var real_deck=Stack[Cards]()
    /**
      * This function creates a brand new Deck and cleans out the previous Deck.
      */
    def create_DECK:Unit={
        real_deck=Stack[Cards]()
        for suit <- suits do{
            for number <- numbers do{
                var card=Cards(number+suit)
                
                card.add_number(number)
                card.add_suit(suit)
                if number=="J" then {
                    card.add_point(11)
                }
                else if number=="Q" then {
                    card.add_point(12)
                }
                else if number=="K" then {
                    card.add_point(13)
                }
                else if number=="A" then {
                    card.add_point(14)
                }
                else{
                    card.add_point(number.toInt)
                }
                real_deck.push(card)
            }
        }  
        if shuffle_mode == 1 then{
            shufle_deck
        }
    }
    /**
      * 
      * This method will deal out the hidden guard which is step one in preparing the players for the game.
      * @param list_of_Player In order for the Deck to deal the hidden guard it needs to have a list of the Player classes
      */
    def deal_hidden_Guard(list_of_Player:List[Player]): Unit ={
        var player_count=0
        for i <- 0 to 11 do{
            var player = list_of_Player(player_count)
            player.add_hidden_guard(real_deck.pop())

            if player_count == 3 then{
                player_count=0
            }
            else{
                player_count+=1
            }
        }   
    }
    /**
      * 
      * This deck will give each Player six cards to each player.
      * @param list_of_Player In order for the Deck to deal the hand it needs to have a list of the Player classes
      */
    def deal_hand(list_of_Player:List[Player]) : Unit ={

        var player_count=0

        for i <- 0 to 23 do{
            var player=list_of_Player(player_count)
            player.add_hand(real_deck.pop())
            
            if player_count == 3 then{
                player_count=0
            }
            else{
                player_count+=1
            }
        }
        
        
    }
    /**
      * 
      * This function will take the top card from the deck and return it 
      * @return This function returns the top card from the Deck, this function is used when Players need to pick up a card.
      */
    def remove_card: Cards ={
        return real_deck.pop()
    }
    /**
      * 
      * This fuction returns the amount of cards left on the Deck.
      * @return The amount of cards left on the deck 
      */
    def get_size_of_deck: Int={
        return real_deck.size
    }
    /**
      * 
      * This function looks at the next card on the deck; however it does not remove it (This is used primarily for testing).
      * @return This fuction peeks at the top card of on the deck without removing it. 
      */
    def get_head:Cards={
        return real_deck.head
    }
    /**
      * This function clears the deck and leaves it empty
      */
    def clear_deck:Unit={
        real_deck=Stack[Cards]()
    }
    /**
      * This Shuffles the deck 
      */
    def shufle_deck:Unit={
        var shuffled =Random.shuffle(real_deck.toList)
        real_deck=Stack(shuffled:_*)

    }
    /**
      * This will flip between the two different modes for the creation of the deck wither Shuffle where the deck is suffled right after it is created or Default which is a cold deck based on how the deck is built (Deck will always be the same in this mode)
      */
    def Shuffle_set:Unit={
        if shuffle_mode==0 then{
            shuffle_mode=1
        }
        else{
            shuffle_mode=0
        }
    }
}   
