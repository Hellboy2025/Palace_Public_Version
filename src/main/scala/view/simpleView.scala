package view

import scala.swing._
import controller._
import java.awt.Font
import BorderPanel.Position._

/**  Provides a basic Scala Swing GUI with buttons for game control commands and
 * textAreas for the PlayerOrder and GameArea.
  */
class SimpleView extends MainFrame with View {

  // Components

  // Player Order Panel
  object playerOrder extends TextArea
  object advancePO_Btn extends Button(">> Advance")
  object playerOrderPanel extends FlowPanel {
    contents += new Label("Player Order:")
    contents += playerOrder
    contents += advancePO_Btn
  }

  // Game Control Buttons Panel
  object initialize_Btn extends Button("Initialize Game")
  object checkWinner_Btn extends Button("Winner?")
  object doMove_Btn extends Button("Do Move")
  object doTurn_Btn extends Button("Do Turn")
  object doGame_Btn extends Button("Do Game")
  object doshuffle extends Button("Shuffle Mode")
  object gameControlsPanel extends BoxPanel(Orientation.Vertical) {
    contents += new Label("Start Game:")
    contents ++= Seq(initialize_Btn)
    contents += new Label("\n")
    contents += new Label("Game On:")
    contents ++= Seq(checkWinner_Btn, doMove_Btn, doTurn_Btn, doGame_Btn)
    contents += new Label("\n")
    contents += new Label("Randomize the Deck:")
    contents ++= Seq(doshuffle)
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  object gameText extends TextArea {
    font = new Font ("Times New Roman", Font.BOLD , 12)
  }
  // object gameAreaPanel extends ScrollPane(gameText)
  object gameAreaPanel extends ScrollPane(gameText){
    border = Swing.EmptyBorder(0, 0, 50, 10)
  }

  // Strategy Panel Pieces
  //Player 1 Strategies
  object defaultMove1 extends Button("Default Move")
  object highMove1 extends Button("Highest Move")
  object defaultGuard1 extends Button("Default Guard")
  object strongGuard1 extends Button("Strong Guard")
  object weakGuard1 extends Button("Weak Guard")
  object strategyPanelA1 extends BoxPanel(Orientation.Horizontal) {
    contents += new Label("Bryan:     ")
    contents ++= Seq(defaultMove1, highMove1, defaultGuard1, strongGuard1, weakGuard1)
  }

  //Player 2 Strategies
  object defaultMove2 extends Button("Default Move")
  object highMove2 extends Button("Highest Move")
  object defaultGuard2 extends Button("Default Guard")
  object strongGuard2 extends Button("Strong Guard")
  object weakGuard2 extends Button("Weak Guard")
  object strategyPanelA2 extends BoxPanel(Orientation.Horizontal) {
    contents += new Label("Bryana:   ")
    contents ++= Seq(defaultMove2, highMove2, defaultGuard2, strongGuard2, weakGuard2)
  }

  //Player 3 Strategies
  object defaultMove3 extends Button("Default Move")
  object highMove3 extends Button("Highest Move")
  object defaultGuard3 extends Button("Default Guard")
  object strongGuard3 extends Button("Strong Guard")
  object weakGuard3 extends Button("Weak Guard")
  object strategyPanelA3 extends BoxPanel(Orientation.Horizontal) {
    contents += new Label("Brayden: ")
    contents ++= Seq(defaultMove3, highMove3, defaultGuard3, strongGuard3, weakGuard3)
  }

  //Player 4 Strategies
  object defaultMove4 extends Button("Default Move")
  object highMove4 extends Button("Highest Move")
  object defaultGuard4 extends Button("Default Guard")
  object strongGuard4 extends Button("Strong Guard")
  object weakGuard4 extends Button("Weak Guard")
  object strategyPanelA4 extends BoxPanel(Orientation.Horizontal) {
    contents += new Label("Angel:      ")
    contents ++= Seq(defaultMove4, highMove4, defaultGuard4, strongGuard4, weakGuard4)
  }

  /* 
  object strategyDescriptionArea extends BoxPanel(Orientation.Vertical) {
    contents += new Label("Strategies")
    contents += new Label("All Players Assigned Default at Initialization")
    contents += new Label("Change Player Strategies Below (Initialized w/ Default)")
  }
  */

  object strategyPanel extends GridPanel(5, 5) {
    // contents += strategyDescriptionArea
    contents += new Label("Change Player Strategies Below (Initialized w/ Default)")
    // contents += new Label("\n")
    contents += strategyPanelA1
    contents += strategyPanelA2
    contents += strategyPanelA3
    contents += strategyPanelA4
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }


  // MainFrame Contents Panel
  object borderPanel extends BorderPanel {
    layout += playerOrderPanel -> North
    layout += gameControlsPanel -> West
    layout += gameAreaPanel -> Center
    // layout += strategyPanel -> South
    layout += strategyPanel -> South
  }
  
  title = "PALACE"
  contents = borderPanel
  centerOnScreen()

  // size = new Dimension(1500, 400)
  size = new Dimension(1500, 800)

  /** Prepare this View class for initial use by invoking the superclass init 
   * to store the reference to the controller, hook-up triggers to controller methods, and 
   * set visible.
   *  @param controller The MVC Controller
   */
  override def init(controller: Controller): Unit = {
    super.init(controller)

    update_PlayerOrder
    update_GameArea
    
    advancePO_Btn.action = controller.advancePlayerOrder
    initialize_Btn.action = controller.init_game
    checkWinner_Btn.action = controller.check_winner
    doMove_Btn.action = controller.do_move
    doTurn_Btn.action = controller.do_turn
    doGame_Btn.action = controller.do_game
    doshuffle.action=controller.shuffle

    // ALL PLAYER STRATEGY BUTTONS

    // Player 1
    defaultMove1.action = controller.defaultM1
    highMove1.action = controller.highM1
    defaultGuard1.action = controller.defaultG1
    strongGuard1.action = controller.strongG1
    weakGuard1.action = controller.weakG1

    // Player 2
    defaultMove2.action = controller.defaultM2
    highMove2.action = controller.highM2
    defaultGuard2.action = controller.defaultG2
    strongGuard2.action = controller.strongG2
    weakGuard2.action = controller.weakG2

    // Player 3
    defaultMove3.action = controller.defaultM3
    highMove3.action = controller.highM3
    defaultGuard3.action = controller.defaultG3
    strongGuard3.action = controller.strongG3
    weakGuard3.action = controller.weakG3

    // Player 4
    defaultMove4.action = controller.defaultM4
    highMove4.action = controller.highM4
    defaultGuard4.action = controller.defaultG4
    strongGuard4.action = controller.strongG4
    weakGuard4.action = controller.weakG4
    
    visible = true
  }
  
  def update_PlayerOrder: Unit = {
    playerOrder.text = controller.get.showPlayerOrder
  }
  
  def update_GameArea: Unit = {
    gameText.text = controller.get.showGameArea
  }
  
  def showWinner(result: String): Unit = {
    Dialog.showMessage(this, result, title="and the winner is...")    
  }

}
