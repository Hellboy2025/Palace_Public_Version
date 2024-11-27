package _main

import model._
import view._
import controller._
import simulation_components.Menu
/**
  * Game simulation for the Palace game, a turn-based, 4-player game.
  */
object Palace {
  
  @main def main(): Unit = {
      
    val model = new Model
    val view  = new SimpleView   
    val controller = new Controller(model, view)
    Menu.init_game
    view.init(controller)    
  }
  
}