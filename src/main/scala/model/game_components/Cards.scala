package model.game_components

import scala.compiletime.ops.string
/** The cards Players will hold
  *
  * @constructor Creates a new card based on the id.
  * @param card_id the card Value and Suit placed together in a string 
  */
class Cards(card_id: String) {
    private var points=0
    private var suit:String=card_id(1).toString()
    private var number:String=card_id(0).toString()
    /**
      * 
      * This fucntion is used to add on points to the card so it has a value. 
      * @param ammount The amount of points the card is worth aka the number of the card.
      */
    def add_point(ammount:Int):Unit={
      points+=ammount
    }
    /**
      * 
      *
      * @param suit_of_card Adds the suit to the suit value in the Class
      */
    def add_suit(suit_of_card:String):Unit={
      suit=suit_of_card
    }
    /**
      * 
      *
      * @param num Adds the number to the num value in the Class
      */
    def add_number(num:String):Unit={
      number=num
    }
    /**
      * 
      *
      * @return The Card Id which will be the formated as such {Number Suit}
      */
    def get_id:String={
      return card_id
    }
    /**
      * 
      *
      * @return Returns the suit of the card.
      */
    def card_suit:String={
        return suit
    }
    /**
      * 
      *
      * @return Return the number of the card.
      */
    def card_number:String={
        return number
    }
    /**
      * 
      *
      * @return Return the amount of points the card is worth.
      */
    def get_point: Int={
        return points
    }
}
