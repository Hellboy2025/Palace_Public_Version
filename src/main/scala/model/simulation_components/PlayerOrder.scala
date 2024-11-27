package model.simulation_components

import model.game_components.Player
import java.util.Queue
import model.game_components.Table
/******* PLAYER_ORDER *******/
object PlayerOrder extends scala.collection.mutable.Queue[Player] {

    /* the following section of code initiates all new players, in this case, only 4 for the simulation */
    private val playersNames = Table.get_players

    playersNames.foreach { player =>this.enqueue(player)
    }
    val originalPlayerOrder = PlayerOrder.toArray
    this.clear()
    
    initialize

    /** Gets the Original Player Order */
    def getOriginalOrder: Array[Player] ={
      // return playersNames.toArray
      return originalPlayerOrder
    }

    /**
      * This initializes the Player order based on the original order
      */
    def initialize:Unit={
      this.clear()
      playersNames.foreach{player =>this.enqueue(player)}

    }
  

    /**
      * 
      * the advance function advances player order by dequeing and then "re"-queing the head of the queue 
      */
    def advance: Unit = {
        this += this.dequeue
    }

    /**
      * the show function creates a new string, adding in each of the queued players' names 
      *
      * @return String of the Order of player.
      */ 
    def show: String = {
        val sb = new StringBuilder("")
        for p <- this.toArray yield sb ++= p.get_name + ", "
        sb.toString.substring(0, sb.toString.length - 2)
    }

    /**
      * the current function returns the current player / first player in the order 
      *
      * @return Returns the current Player
      */ 
    def current: Player = {
        return this.head
    }
    
} 

/* code copied with only names modified from gridmaster4 course example */
