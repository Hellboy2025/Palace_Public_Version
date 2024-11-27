package controller

import scala.swing._
import model._
import view._

/**  Controller for an MVC architecture
  *  @param model Model for the MVC architecture 
  *  @param view View for MVC architecture
  */

class Controller(model: Model, view: View) {

  /**  Displays everything necessary to observe and understand the progress of the game
  */
  def showGameArea: String = {
    model.menu.Show_game_area_Gui
  }

  /**  shows player names in the order they will play
  */

  def showPlayerOrder: String = {
    model.menu.showPlayerOrder
  }
  /**
    * This Button will Set the Deck into shuffle mode or standard deck mode.
    */
  def shuffle = Action("Shuffle Mode"){
    model.menu.Shuffle_setter
  }
  /**   moves the player who is currently "up" to the end of the playing order, and advances the "next" player to be "up"
  */

  def advancePlayerOrder = Action("Advance") {
    model.menu.advancePlayerOrder
    view.update_PlayerOrder
  }

  /**  performs all actions necessary to prepare the game for the first move; can also be used to reset the game simulation
  */
  def init_game = Action("Initialize") {
    model.menu.init_game
    view.update_PlayerOrder
    view.update_GameArea
  }

  /**  at any point in the game simulation, determine whether any player has won the game, and return the winning player's name or "none"
  */

  def check_winner = Action("Winner?") {    
    view.showWinner(model.menu.check_winner)
  }

  /**  the player who is currently "up" in the player order performs all move actions, and the player order advances
  */
  def do_move = Action("Do Move") {
    val result = model.menu.do_move
    view.update_PlayerOrder
    view.update_GameArea
    if result == true then view.showWinner(model.menu.check_winner)     
  }
  
  /**  performs DO MOVE four times (or more specfically, the length of the player order), as well as CHECK FOR WINNER at appropriate times
  */
  def do_turn = Action("Do Turn") {    
    val result = model.menu.do_turn
    view.update_PlayerOrder
    view.update_GameArea
    if result == true then view.showWinner(model.menu.check_winner)   
  }
  
  /**  performs DO TURN until the game is won
  */
  def do_game = Action("Do Game") {
    val result = model.menu.do_game
    view.update_PlayerOrder
    view.update_GameArea
    if result then view.showWinner(model.menu.check_winner)   
  }

 // PLAYER STRATEGY BUTTON ACTIONS

  // try: model.menu.showOGPlayerOrder

  // Player 1 - Bryan
  /**  sets Player 1's pick card strategy as the default
  */
  def defaultM1 = Action("Default Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryan" then
        model.menu.Set(player.get_name, 1)
    view.update_GameArea
  }
  /**  sets Player 1's pick card strategy to play their highest card
  */
  def highM1 = Action("Highest Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryan" then
        model.menu.Set(player.get_name, 2)
    view.update_GameArea
  }
  /**  sets Player 1's guard strategy as the default
  */
  def defaultG1 = Action("Default Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryan" then
        model.menu.Set(player.get_name, 3)
    view.update_GameArea
  }
  /**  sets Player 1's guard strategy to strong
  */
  def strongG1 = Action("Strong Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryan" then
        model.menu.Set(player.get_name, 4)
    view.update_GameArea
  }
  /**  sets Player 1's guard strategy to weak
  */
  def weakG1 = Action("Weak Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryan" then
        model.menu.Set(player.get_name, 5)
    view.update_GameArea
  }

  // Player 2 - Bryana
  /**  sets Player 2's pick card strategy as the default
  */
  def defaultM2 = Action("Default Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryana" then
        model.menu.Set(player.get_name, 1)
    view.update_GameArea
  }
  /**  sets Player 2's pick card strategy to play their highest card
  */
  def highM2 = Action("Highest Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryana" then
        model.menu.Set(player.get_name, 2)
    view.update_GameArea
  }
  /**  sets Player 2's guard strategy as the default
  */
  def defaultG2 = Action("Default Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryana" then
        model.menu.Set(player.get_name, 3)
    view.update_GameArea
  }
  /**  sets Player 2's guard strategy to strong
  */
  def strongG2 = Action("Strong Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryana" then
        model.menu.Set(player.get_name, 4)
    view.update_GameArea
  }
  /**  sets Player 2's guard strategy to weak
  */
  def weakG2 = Action("Weak Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Bryana" then
        model.menu.Set(player.get_name, 5)
    view.update_GameArea
  }

  // Player 3 - Brayden
  /**  sets Player 3's pick card strategy as the default
  */
  def defaultM3 = Action("Default Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Brayden" then
        model.menu.Set(player.get_name, 1)
    view.update_GameArea
  }
  /**  sets Player 3's pick card strategy to play their highest card
  */
  def highM3 = Action("Highest Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Brayden" then
        model.menu.Set(player.get_name, 2)
    view.update_GameArea
  }
  /**  sets Player 3's guard strategy as the default
  */
  def defaultG3 = Action("Default Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Brayden" then
        model.menu.Set(player.get_name, 3)
    view.update_GameArea
  }
  /**  sets Player 3's guard strategy to strong
  */
  def strongG3 = Action("Strong Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Brayden" then
        model.menu.Set(player.get_name, 4)
    view.update_GameArea
  }
  /**  sets Player 3's guard strategy to weak
  */
  def weakG3 = Action("Weak Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Brayden" then
        model.menu.Set(player.get_name, 5)
    view.update_GameArea
  }

  // Player 4 - Angel
  /**  sets Player 4's pick card strategy as the default
  */
  def defaultM4 = Action("Default Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Angel" then
        model.menu.Set(player.get_name, 1)
    view.update_GameArea
  }
  /**  sets Player 4's pick card strategy to play their highest card
  */
  def highM4 = Action("Highest Move") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Angel" then
        model.menu.Set(player.get_name, 2)
    view.update_GameArea
  }
  /**  sets Player 4's guard strategy as the default
  */
  def defaultG4 = Action("Default Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Angel" then
        model.menu.Set(player.get_name, 3)
    view.update_GameArea
  }
  /**  sets Player 4's guard strategy to strong
  */
  def strongG4 = Action("Strong Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Angel" then
        model.menu.Set(player.get_name, 4)
    view.update_GameArea
  }
  /**  sets Player 4's guard strategy to weak
  */
  def weakG4 = Action("Weak Guard") {
    for player <- model.menu.getOriginalPlayerOrder do
      if player.get_name == "Angel" then
        model.menu.Set(player.get_name, 5)
    view.update_GameArea
  }

  /**  terminates the application
  */
  def exit = Action("Exit") {
    sys.exit(0)
  }

}
