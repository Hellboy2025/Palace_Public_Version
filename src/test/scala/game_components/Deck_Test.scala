package model.game_components
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class Deck_Test extends AnyFunSpec with Matchers{
    describe("This class manages testing the Deck."){
        describe("These tests check for the utility of the deck."){
            
            it("Tests that the deck is created properly"){
                Deck.create_DECK
                Deck.get_size_of_deck should be(52)
                var card:Cards=Deck.get_head
                card.card_suit should be("D")
                card.card_number should be("2")
                card.get_point should be (2)
                Deck.remove_card
                card=Deck.get_head
                Deck.get_size_of_deck should be(51)
                card.card_suit should be("D")
                card.card_number should be("3")
                card.get_point should be (3)
            }
            
        }
    }
}
