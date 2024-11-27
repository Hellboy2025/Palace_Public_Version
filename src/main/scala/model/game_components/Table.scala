package model.game_components
import model.simulation_components.Menu
import scala.collection.mutable
import model.simulation_components.PlayerOrder
import model.simulation_components.Pile
import model.simulation_components.PlayerOrder.playersNames
import scala.annotation.retains
import javax.smartcardio.Card
/******* TABLE *******/ 
/* called table instead of board as this card game is played on a table */
object Table {
  /* shows the hand, guard, and hidden guard for each player */
  private val list_players :List[Player]= List(Player("Bryan"),Player("Bryana"),Player("Brayden"),Player("Angel"))
  /**
    * 
    *
    * @return This returns a list of the Player obejcts that will be used during the game.
    */
  def get_players :List[Player]={
    return list_players
  }
  /**
    * 
    *
    * @return This returns a list of the Player obejcts that will be used during the game.
    */
  def getPlayerName(player: Player): String = {
    return player.get_name
  }
  /**
    * 
    *
    * @return This returns the exact same game area as the fuction show but this one adds a view of the Pile at the bottom of the game area.
    */
  def show_for_Gui = {
    val sb = new StringBuilder("")
    var index = 0

    for p <- list_players
    // bases the revealing of hands, guards, and hiddenGuards on the original player order
      do
        sb ++= p.get_name + "'s Hand: " + p.get_hand_for_show_GUI + "\n" + 
               "Guard: " + p.get_guad_for_show_GUI + ", Hidden Guard: " + p.get_hiddem_Guard_for_show_GUI
        if index < 3 then // this if statement ensures there are newlines separating each player from one another, but not a final newline after the last player's info is illustrated
          sb ++= "\n"
        index += 1
    sb++="\n"   
    sb++="\n"
    if Pile.size_of_pile != 0 then{
          sb++=s"Top Pile Card: ${Pile.get_top_card.get_id}\n"
    }
    else{
      sb++=s"Top Pile Card: None\n"
    }
    sb++="\n"
    sb++=Table.show_strategey
    sb.toString
    }
  /**
    * 
    *
    * @return This fucntion returns a String that displays player hand, guard, and hidden guard 
    */
  def show = {
    val sb = new StringBuilder("Table: \n")
    var index = 0

    for p <- list_players
    // bases the revealing of hands, guards, and hiddenGuards on the original player order
      do
        sb ++= p.get_name + "'s Hand: " + p.get_hand_for_show + "\n" + 
               "Guard: " + p.get_guad_for_show + ", Hidden Guard: " + p.get_hiddem_Guard_for_show
        if index < 3 then // this if statement ensures there are newlines separating each player from one another, but not a final newline after the last player's info is illustrated
          sb ++= "\n"
        index += 1
    
    sb.toString
    }
    /**
      * This function initializes the game by, creating the deck, dealing the hand, and initiating the pile.
      */
  def init_game : Unit={
        PlayerOrder.initialize
        for i <- 0 to 3 do{
          PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
          PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count)
          PlayerOrder.current.remove_hidden_guard(PlayerOrder.current.get_hidden_Guard_count)
          PlayerOrder.advance
        }
        Pile.clean_pile
        Deck.create_DECK
        Deck.deal_hidden_Guard(list_players)
        Deck.deal_hand(list_players)
        for i <- list_players do {
          i.get_guard_strat.pick_guard
        }
        Pile.add_pile(Deck.remove_card)
        
       
    }
  /**
    * 
    *This function checks for the winer of the game.
    * @param list_player Needs a List of Player classes to check for all of their hands
    * @return This will either retun the string False or the name of the Player
    */
  def check_for_winner: String= {
    var player=PlayerOrder.current
    var hand=player.get_hand_count
    var guard=player.get_guard_count
    var hidden_guard=player.get_hidden_Guard_count
    if Deck.get_size_of_deck == 0 then{
      if hand == 0 then{
        if guard == 0 then{
          if hidden_guard == 0 then{
            return player.get_name
          }
          else{
            return "False"
          }
        }
        else{
          return "False"
        }
      }
      else{
        return "False"
      }
    }
    else{
      return "False"
    }
  }
  /**
    * 
    *
    * @return This function is a assistant fucntion to do_move it returns either the card played by the player which will be added to the pile or it will return the pile which will be added to the player hand.
    */
  def picked_card : List[Cards]={
    var picked:List[Cards]=List()
    var player=PlayerOrder.current
    
    if player.get_hand_count != 0 then{
      return player.get_mov_strat.pick_card("Hand")
    }
    else if player.get_guard_count != 0 then{
      return player.get_mov_strat.pick_card("Guard")
    }
    else if player.get_hidden_Guard_count != 0 then{
      return player.get_mov_strat.pick_card("Hidden Guard")
    }
    else{
      return picked
    }
  }
  /**
    * This fuction will do one single move AKA one player gets to play. It implements logic to know if the list retuned from picked_card is the Pile or the card to be played which will be added to the pile.
    */
  def do_mov :Unit ={
    var player= PlayerOrder.current
    var top_card = Cards("5X")// This is used to implement the logic since there are case when the pile is empty so we have to artifically create a top card which will be replaced if there is a card on the pile.
    if Pile.size_of_pile != 0 then{ 
      top_card=Pile.get_top_card 
    }
    var picked : List[Cards] = picked_card // This returns a list of cards that one of two things the card to be played or the pile to be added to the player. 
    
    if !(picked.isEmpty) && Pile.size_of_pile == 0 && picked.size == 1 then{ // This is for the case in which the pile has been picked up and the next player can put down whichever card they want.
      Pile.add_pile(picked(0))
    }

    
    else if picked(0) == top_card then{ //This is if the Pile was returned it is added to the current players hand
      for i <- picked do{
        PlayerOrder.current.add_hand(i)

      }
    }

    else{ // This is for the normal situation when the player just plays a card
      Pile.add_pile(picked(0))
    }

    if Deck.get_size_of_deck != 0 && player.get_hand_count < 3 then{ // This is to make sure the player has 3 cards in his hand if the Deck is still not empty. 
      while (player.get_hand_count < 3 && Deck.get_size_of_deck != 0) do{
        player.add_hand(Deck.remove_card)
      }
    }
      
    //advance to the next player
  }
  /**
    * This function will play a whole turn AKA all player get to play once.
    */
  def do_tur: Unit={
    for i <- list_players do{
      Menu.do_move
    }
  }
  /**
    * This function will play the game unctill its over.
    */
  def do_gam:Unit ={
    while check_for_winner=="False" do{ // plays untill a winner is declared.
      Menu.do_move
    }
  }

  /**
    * 
    *
    * @param player String of Players name
    * @param strat Number according to which strat you want the player to have
    * 1:Fist Valid card
    * 2:Highest card
    * 3:First 3 Cards
    * 4:Strong Guard
    * 5:Weak Guard
    */
  def set(player: String, strat : Int): Unit ={
      if strat<=2 then{
        if player=="Bryan" then{
          list_players(0).Set_Mov_Strat(strat)
        }
        if player=="Bryana" then{
          list_players(1).Set_Mov_Strat(strat)
        }
        if player=="Brayden" then{
          list_players(2).Set_Mov_Strat(strat)
        }
        if player=="Angel" then{
          list_players(3).Set_Mov_Strat(strat)
        }
      }
      else if strat>=3 then{
        if player=="Bryan" then{
          list_players(0).Set_Guard_Strat(strat)
        }
        if player=="Bryana" then{
          list_players(1).Set_Guard_Strat(strat)
        }
        if player=="Brayden" then{
          list_players(2).Set_Guard_Strat(strat)
        }
        if player=="Angel" then{
          list_players(3).Set_Guard_Strat(strat)
        }
      }
    }
  /**
    * 
    *
    * @return String displayin the players move and guard strategy.
    */
  def show_strategey : String ={
    var result= new StringBuilder("")
    for i <- list_players do{
      result++=i.show_strategies
    }
    return result.toString()
  }
  
} 
