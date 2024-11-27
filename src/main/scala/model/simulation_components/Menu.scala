package model.simulation_components

import model.game_components.Table
import scala.collection.mutable
import model.game_components.Deck
import scala.annotation.retains

/**
  * This Object will be the Player Menu in which Player will be able to display games.
  */
object Menu {
    /**
      * 
      *
      * @return Returns the Player Order.
      */
    def showPlayerOrder: String = {
        return PlayerOrder.show
    }
    /**
      * 
      *
      * @return Returns the Original Player Order.
      */
    def getOriginalPlayerOrder: Array[model.game_components.Player] = {
        return PlayerOrder.getOriginalOrder
    }
    /**
      * 
      *
      * @return Returns the Name of the Player.
      */
    def getPlayerName: String = {
        return Table.getPlayerName.toString()
    }
    /**
      * Advances the Player Order
      */
    def advancePlayerOrder: Unit = {
        PlayerOrder.advance
    }
    /**
      * 
      *
      * @return This returns a special show game area that inclued the top card in the Pile.
      */
    def Show_game_area_Gui: String={
      val sb = mutable.StringBuilder()
        sb ++= Table.show_for_Gui + "\n"
      sb.toString  
    }
    /**
      * 
      *
      * @return Shows the Game area. 
      */
    def showGameArea: String = {
        val sb = mutable.StringBuilder()
        sb ++= Table.show + "\n"
        /* sb ++= CardCount.show - commented out because this was not needed to pass Bryan's tests, but rather 
        was an idea I had for implementation later in the game to make it easier to identify the winner */

        sb.toString
    }
    /**
      * This initializes the game and resets everything
      */
    def init_game : Unit={
        Table.init_game
    }
    /**
      * This does a move by the current player
      */
    def do_move :Boolean ={

      if Menu.check_winner == "False" then{
        Table.do_mov
        
        if Menu.check_winner != "False" then{
          // PlayerOrder.advance// This line is only used to ease testing cases in case there is a winner that does not matter in testing. 
          return true
        }
        else{
          PlayerOrder.advance
          return false
        }
        
      }
      else{
        // This line is only used to ease testing cases
        return true
      }
      
    }
    /**
      * This Plays the whole game until a winner is declared 
      */
    def do_game:Boolean ={

      Table.do_gam
      return true

    }
    /**
      * This lets all four player do a move
      */
    def do_turn:Boolean ={

      if Menu.check_winner == "False" then{
        Table.do_tur
        if Menu.check_winner != "False" then{
          return true
        }
        else{
          return false
        }
      }
      else{

        return true
      }
    
    }
    /**
      * 
      *
      * @return This returns the winner or "False"
      */
    def check_winner:String ={
      Table.check_for_winner
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
    def Set(player: String, strat : Int): Unit ={
      Table.set(player = player,strat = strat)
    }
    /**
      * This will flip between the two different modes for the creation of the deck wither Shuffle where the deck is suffled right after it is created or Default which is a cold deck based on how the deck is built (Deck will always be the same in this mode)
      */
    def Shuffle_setter:Unit={
      Deck.Shuffle_set
    }
}   

/* lines above were sourced from the gridmaster4 milestone 2 example */
/* only modifications were made to "showGameArea" and the replacement of "Board" with "Table" on lines 3 and 17 */
