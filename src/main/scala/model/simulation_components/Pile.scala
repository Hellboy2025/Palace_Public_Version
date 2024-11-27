package model.simulation_components

import model.game_components.Cards
import javax.smartcardio.Card
import scala.annotation.retains
import scala.compiletime.ops.int
/**
  * This Object will handel the Pile object where card will be place during gameplay.
  */
object Pile {
  private var pile:List[Cards]=List()
  
  /**
    * 
    *
    * @return Shows the top to the Pile without removing it 
    */
  def get_top_card:Cards ={
    return pile(0)
  }
  /**
    * This function empties the Pile
    */
  def clean_pile:Unit={
    pile=pile.empty
  }
  /**
    * 
    *
    * @return Returns the entire Pile and then clears out the Pile
    */
  def pickup_pile:List[Cards]={
    
    return pile
  }
  /**
    * 
    *
    * @param card Give a card to be added to the Pile aka when a player plays.
    */
  def add_pile(card: Cards):Unit={
    pile=card::pile
  }
  /**
    * 
    *
    * @return This returns teh amount of cards in the Pile.
    */
  def size_of_pile:Int={
    return pile.size
  }
}
