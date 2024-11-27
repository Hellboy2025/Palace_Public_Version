package model.simulation_components

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import model.game_components.Table
import model.game_components.Deck
import model.game_components.Cards
import model.game_components.Player
import model.game_components.Table.list_players
import model.simulation_components.Pile.pickup_pile
import javax.smartcardio.Card


class Menu_Test extends AnyFunSpec with Matchers{
  describe("The Palace Card Game") {
    describe("Has an Initial Configuration") {

      /* PLAYER ORDER */
      it("Show Player Order") {  
        PlayerOrder.initialize   
        val expectedResult = "Bryan, Bryana, Brayden, Angel"
        Menu.showPlayerOrder should be(expectedResult)  
      }

      /* ADVANCE ORDER */
      it("Advance Player Order") {     
        val expectedResult = "Bryana, Brayden, Angel, Bryan"

        Menu.advancePlayerOrder // CDT Kim saw missing advancePlayerOrder
        Menu.showPlayerOrder should be(expectedResult)
      }
      /* SHOW GAME AREA */
      it("Show Game Area: Game Table and Players Hands, Guards, and Hidden Guards") {
        val expectedResult = "Table: \n" +
        "Bryan's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Brayden's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Angel's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n"
    
        Menu.showGameArea should be(expectedResult)
      }
      /* SHOW INITIALIZED GAME */
      it("Checks for clar board"){
        val expectedResult1 = "Table: \n" +
        "Bryan's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Brayden's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Angel's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n"
        Menu.showGameArea should be(expectedResult1)
      }
      it("It initializes the game.") {
        
        Menu.init_game
        val expectedResult2 = "Table: \n" +
        "Bryan's Hand: List(Cards(KC),Cards(4H),Cards(8H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        Menu.showGameArea should be(expectedResult2)
        Pile.size_of_pile should be(1)
        
        // conduct "evil testing"
        Deck.create_DECK
        Deck.remove_card.get_id should be("2D") // should be 2 of D
        Deck.remove_card.get_id should be("3D") // should be 3 of D
        Deck.remove_card.get_id should be("4D") // should be 4 of D

        PlayerOrder.advance // PlayerOrder.current should now be Bryana
        PlayerOrder.current.add_hand(Cards("2H")) // this is Bryana
        PlayerOrder.current.add_hand(Cards("3f")) // this is Bryana || add a false card
        PlayerOrder.current.add_hidden_guard(Cards("3f")) // this is Bryana || add a duplicate false card
        var expectedResult3 = "Bryana, Brayden, Angel, Bryan"
        Menu.showPlayerOrder should be(expectedResult3)

        var expectedResult4 = "Table: \n" +
        "Bryan's Hand: List(Cards(KC),Cards(4H),Cards(8H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H),Cards(2H),Cards(3f))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD),Cards(3f))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        Menu.showGameArea should be(expectedResult4)

        
        
        
        var card=Cards("AH")
        Pile.add_pile(card)
        Pile.get_top_card should be(card)

        Menu.showGameArea should be(expectedResult4)
        // check that it reinintializes the game correctly
        Menu.init_game
        
        /* expected result should be shown in the format of "PlayerName: (# in Hand, # in Guard, # in Hidden Guard) */
        
        Menu.showGameArea should be(expectedResult2)
        Pile.size_of_pile should be(1)
        Deck.get_size_of_deck should be(15)
      }
    }

    describe("Checks for Winers") {
      it("Checks for winners") {
        // does this make sense here? @ AI
        Menu.init_game

        val expectedResult1 = "Table: \n" +
        "Bryan's Hand: List(Cards(KC),Cards(4H),Cards(8H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

        Menu.showGameArea should be(expectedResult1)
        Pile.size_of_pile should be(1)
      }
      it("TEST 1 - Checks for Winner with No Cards and that the Player cant win if threre are cards in the deck."){
        Menu.init_game
        PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
        PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count)
        PlayerOrder.current.remove_hidden_guard(PlayerOrder.current.get_hidden_Guard_count)// last 3 calls will make sure that the player has absolutely no cards at all so that I can adda and remove cards to test every case.
        
        // SHOW GAME AREA
        var expected_result1="Table: \n" +
        "Bryan's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

        Menu.showGameArea should be(expected_result1)
        Deck.get_size_of_deck should be(15)//Still cards left in deck
        Menu.check_winner should be("False")// Because there are still cards on the Deck

        // Now we check with in empty deck
        Deck.clear_deck
        Deck.get_size_of_deck should be(0)
        Menu.check_winner should be("Bryan")
        // CHECK FOR WINNER (this is test #1 - we have a valid winner)
      }
      it("Test-2 Tests that player has no cards in every hand"){
        Menu.init_game
        PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
        PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count)
        PlayerOrder.current.remove_hidden_guard(PlayerOrder.current.get_hidden_Guard_count)
        Deck.clear_deck

        var expected_result1="Table: \n" +
        "Bryan's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

        Menu.showGameArea should be(expected_result1)
      }
      it("Test-3 Checks that Player cant have a card in the hand in order to win."){
      Menu.init_game
      PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
      PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count)
      PlayerOrder.current.remove_hidden_guard(PlayerOrder.current.get_hidden_Guard_count)
      Deck.clear_deck
      PlayerOrder.current.add_hand(Cards("2H"))
      var expected_result1="Table: \n" +
      "Bryan's Hand: List(Cards(2H))" + "\n" +
      "Guard: List(), " + "Hidden Guard: List()" + "\n" +
      "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
      "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
      "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
      "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
      "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
      "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

      Menu.showGameArea should be(expected_result1)
      var expectedResult="False"
      Menu.check_winner should be(expectedResult)
      
      PlayerOrder.current.remove_card(1)
      expectedResult="Bryan"
      Menu.check_winner should be(expectedResult)
      }

      it("Test-4 Checks that Player has no hidden guard cards in order to win."){
      PlayerOrder.current.add_hidden_guard(Cards("2H"))
      var expectedResult="False"
      var expected_result1="Table: \n" +
      "Bryan's Hand: List()" + "\n" +
      "Guard: List(), " + "Hidden Guard: List(Cards(2H))" + "\n" +
      "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
      "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
      "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
      "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
      "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
      "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

      Menu.showGameArea should be(expected_result1)
      Menu.check_winner should be(expectedResult)

      PlayerOrder.current.remove_hidden_guard(1)
      expectedResult="Bryan"
      Menu.check_winner should be(expectedResult)
      }
      it("Test-5 Checks that the player does not have any guard cards in order to win."){
      PlayerOrder.current.add_guard(Cards("2H"))
      var expectedResult="False"
      var expected_result1="Table: \n" +
      "Bryan's Hand: List()" + "\n" +
      "Guard: List(Cards(2H)), " + "Hidden Guard: List()" + "\n" +
      "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
      "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
      "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
      "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
      "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
      "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

      Menu.showGameArea should be(expected_result1)
      Menu.check_winner should be(expectedResult)

      PlayerOrder.current.remove_guard(1)
      expectedResult="Bryan"
      Menu.check_winner should be(expectedResult)
      
    }
      
    }
    describe("Does a Move"){
  
      it("Checks for Moves") {
        // Step 1 - initializeGame

        Menu.init_game

        var expectedMovesResult = "Table: \n" +
        "Bryan's Hand: List(Cards(KC),Cards(4H),Cards(8H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

        Menu.showGameArea should be(expectedMovesResult)

        // Step 2 - delete all cards from current player and showGameArea 
        //-  remember that playerOrder is reset as well so the current player is Bryan

        for i <- 0 to 3 do{
            PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
            PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count)
            PlayerOrder.current.remove_hidden_guard(PlayerOrder.current.get_hidden_Guard_count)
            PlayerOrder.advance
          }

        var expectedResult = "Table: \n" +
        "Bryan's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Brayden's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Angel's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n"
        Menu.showGameArea should be(expectedResult)


        // Step 3 - add testable cards to current player's (Bryan's) hand, guard, and hiddenGuard
        var card=Cards("3H")
        card.add_point(3)
        var card2=Cards("3S")
        card2.add_point(3)
        var card3=Cards("3D")
        card3.add_point(3)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.current.add_hidden_guard(card3)
        PlayerOrder.advance
        card=Cards("4H")
        card.add_point(4)
        card2=Cards("4S")
        card2.add_point(4)
        card3=Cards("4D")
        card3.add_point(4)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.current.add_hidden_guard(card3)
        PlayerOrder.advance
        card=Cards("5H")
        card.add_point(5)
        card2=Cards("5S")
        card2.add_point(5)
        card3=Cards("5D")
        card3.add_point(5)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.current.add_hidden_guard(card3)
        PlayerOrder.advance
        card=Cards("6H")
        card.add_point(6)
        card2=Cards("6S")
        card2.add_point(6)
        card3=Cards("6D")
        card3.add_point(6)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.current.add_hidden_guard(card3)
        PlayerOrder.advance
        expectedResult = "Table: \n" +
        "Bryan's Hand: List(Cards(3H))" + "\n" +
        "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
        "Bryana's Hand: List(Cards(4H))" + "\n" +
        "Guard: List(Cards(4S)), " + "Hidden Guard: List(Cards(4D))" + "\n" +
        "Brayden's Hand: List(Cards(5H))" + "\n" +
        "Guard: List(Cards(5S)), " + "Hidden Guard: List(Cards(5D))" + "\n" +
        "Angel's Hand: List(Cards(6H))" + "\n" +
        "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"

        Menu.showGameArea should be(expectedResult)
        Pile.size_of_pile should be (1)

        // Step 4a - the overall test makes sure that the player "loses" a card to the pile (i.e. plays a card / discards it), assuming the player is able to put a card down (they have a valid one to play)
        // Step 4b - order of precedence for playing a card goes: hand, guard, hiddenGuard (players must still play a valid card)
        // Step 4c - if a player still has cards in their hand, but does not have a valid one to play, they must pick up the pile
        // Step 4d - if a player cannot play at all, they will pick up the pile
        // Step 4e - after a player plays a card from their hand, they will check to see if the deck is empty, if not, then they will draw a card (if they play more than one card, they will draw however many cards they need to be equal to three so long as that many cards are available to draw)
        // Step 4f - the overall test will also make sure the the player order is advanced

        // TEST 1 - player plays 1st valid card from hand
        }
        it("player plays 1st valid card from hand"){
          Pile.size_of_pile should be(1)
          Pile.pickup_pile
          var card=Cards("2H")
          card.add_point(2)
          Pile.add_pile(card)
          Pile.get_top_card.get_point should be(2)
          PlayerOrder.current.get_hand(0).get_point should be (3)
          Menu.do_move //Bryan's Move
          Pile.size_of_pile should be(3)
          Pile.get_top_card.get_point should be (3)

          var expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(KH),Cards(AH),Cards(2S))" + "\n" +
          "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
          "Bryana's Hand: List(Cards(4H))" + "\n" +
          "Guard: List(Cards(4S)), " + "Hidden Guard: List(Cards(4D))" + "\n" +
          "Brayden's Hand: List(Cards(5H))" + "\n" +
          "Guard: List(Cards(5S)), " + "Hidden Guard: List(Cards(5D))" + "\n" +
          "Angel's Hand: List(Cards(6H))" + "\n" +
          "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"
          
          Pile.size_of_pile should be(3)
          Menu.showGameArea should be(expectedResult)// This tests that the player can play a valid card from his hand, and that Player will pikc cards from the deck till their hand has 3 cards
          
          // PlayerOrder.advance This will be done by the do move
          expectedResult = "Bryana, Brayden, Angel, Bryan"
          Menu.showPlayerOrder should be(expectedResult)// We know that do_move is advancing player order at the end 
          Pile.get_top_card.get_point should be (3)// This tested that a player places the card on the Pile
        }
        // TEST 2 - player runs out of cards in hand (deck is empty) and plays from their guard
        it("TEST 2 - player runs out of cards in hand (deck is empty) and plays from their guard"){
          PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
          Deck.clear_deck
          var expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(KH),Cards(AH),Cards(2S))" + "\n" +
          "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(Cards(4S)), " + "Hidden Guard: List(Cards(4D))" + "\n" +
          "Brayden's Hand: List(Cards(5H))" + "\n" +
          "Guard: List(Cards(5S)), " + "Hidden Guard: List(Cards(5D))" + "\n" +
          "Angel's Hand: List(Cards(6H))" + "\n" +
          "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"
          Menu.showGameArea should be(expectedResult)

          Menu.do_move

          expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(KH),Cards(AH),Cards(2S))" + "\n" +
          "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List(Cards(4D))" + "\n" +
          "Brayden's Hand: List(Cards(5H))" + "\n" +
          "Guard: List(Cards(5S)), " + "Hidden Guard: List(Cards(5D))" + "\n" +
          "Angel's Hand: List(Cards(6H))" + "\n" +
          "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"
          
          Menu.showGameArea should be(expectedResult)// The card in the guard is palced in the Pile and played as the test expected
          Pile.get_top_card.get_id should be("4S")

        }
        // TEST 3 - player runs out of cards in hand and guard and plays from their hiddenGuar
        it("Test 3 - player runs out of cards in hand and guard and plays from their hiddenGuar"){
          PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
          PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count) // Bryaden is getting his hand and guard cleared.
          var expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(KH),Cards(AH),Cards(2S))" + "\n" +
          "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List(Cards(4D))" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List(Cards(5D))" + "\n" +
          "Angel's Hand: List(Cards(6H))" + "\n" +
          "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"
          Menu.showGameArea should be(expectedResult) // This is what it looks like before the test

          Menu.do_move
          expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(KH),Cards(AH),Cards(2S))" + "\n" +
          "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List(Cards(4D))" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Angel's Hand: List(Cards(6H))" + "\n" +
          "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"
          Menu.showGameArea should be(expectedResult)
          Pile.get_top_card.get_id should be("5D")
          PlayerOrder.advance
        }
        // TEST 4 - player's first valid card is 1st one in hand
        it("Test 4 - player's first valid card is 1st one in hand"){
          var card=Cards("2S")
          card.add_point(2)
          PlayerOrder.current.add_hand(card)
          var expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(KH),Cards(AH),Cards(2S))" + "\n" +
          "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List(Cards(4D))" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Angel's Hand: List(Cards(6H),Cards(2S))" + "\n" +
          "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"
          Menu.showGameArea should be(expectedResult)
          Menu.do_move// We know that the 6H is a valid cards because the last card on the pile was the 5D 

          expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(KH),Cards(AH),Cards(2S))" + "\n" +
          "Guard: List(Cards(3S)), " + "Hidden Guard: List(Cards(3D))" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List(Cards(4D))" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Angel's Hand: List(Cards(2S))" + "\n" +
          "Guard: List(Cards(6S)), " + "Hidden Guard: List(Cards(6D))" + "\n"
          Menu.showGameArea should be(expectedResult)
          Pile.get_top_card.get_id should be ("6H")

        }
          
        // TEST 5 - player's first valid card is the last one in hand
        it("Test 5 - player's first valid card is the last one in hand"){
          for i <- 0 to 3 do{
          PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
          PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count)
          PlayerOrder.current.remove_hidden_guard(PlayerOrder.current.get_hidden_Guard_count)
          PlayerOrder.advance
          } //Clearing out everything
          
          var card=Cards("4H")
          card.add_point(4)
          var card2=Cards("3C")
          card2.add_point(3)
          var card3=Cards("8H")
          card3.add_point(8)

          PlayerOrder.current.add_hand(card)
          PlayerOrder.current.add_hand(card2)
          PlayerOrder.current.add_hand(card3)

          var expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(4H),Cards(3C),Cards(8H))" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Angel's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n"
        
          Menu.showGameArea should be(expectedResult)

          Menu.do_move
          expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(4H),Cards(3C))" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Bryana's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Angel's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n"

          Menu.showGameArea should be(expectedResult)
          Pile.get_top_card.get_id should be("8H")


        }
        // TEST 6 - player has no valid move and must pick up the pile (i.e. meaning if they have cards in hand or guard or hidden guard if there is no valid move they must pick up the pile
        it("Test 6 - player has no valid move and must pick up the pile (i.e. meaning if they have cards in hand or guard or hidden guard if there is no valid move they must pick up the pile") {
          var card=Cards("3H")
          card.add_point(3)
          PlayerOrder.current.add_hand(card)
          var expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(4H),Cards(3C))" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Bryana's Hand: List(Cards(3H))" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Angel's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n"
        
          Menu.showGameArea should be(expectedResult)
          Pile.clean_pile
          card=Cards("AS")
          card.add_point(13)
          Pile.add_pile(card)
          Pile.get_top_card.get_point should be (13)
          card=Cards("KS")
          card.add_point(12)
          Pile.add_pile(card)
          Pile.get_top_card.get_point should be (12)
          Menu.do_move // Bryana's turn 
          expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(4H),Cards(3C))" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Bryana's Hand: List(Cards(3H),Cards(KS),Cards(AS))" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Brayden's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n" +
          "Angel's Hand: List()" + "\n" +
          "Guard: List(), " + "Hidden Guard: List()" + "\n"

          Menu.showGameArea should be(expectedResult)
          Pile.size_of_pile should be(0)
        }


      }
      describe("Does a whole turn"){
       it("Cheks a whole Turn") { // this is essentially a whole "round", where each player does a move
        // Step 1 - initializeGame

        Menu.init_game
        for i <- 0 to 3 do{
          PlayerOrder.current.remove_card(PlayerOrder.current.get_hand_count)
          PlayerOrder.current.remove_guard(PlayerOrder.current.get_guard_count)
          PlayerOrder.current.remove_hidden_guard(PlayerOrder.current.get_hidden_Guard_count)
          PlayerOrder.advance
        }// This clears out all of the players hand
        Deck.clear_deck //We are going to clear deck because do move should already test that players have picked up cards if there are still cards in the Deck 
        var expectedResult = "Table: \n" +
        "Bryan's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Brayden's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n" +
        "Angel's Hand: List()" + "\n" +
        "Guard: List(), " + "Hidden Guard: List()" + "\n"
        Menu.showGameArea should be(expectedResult)

        //Now we add cards for the Player to play with for a turn
        var card=Cards("3H")
        card.add_point(3)
        var card2=Cards("3K")
        card2.add_point(3)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.advance
        card=Cards("4H")
        card.add_point(4)
        card2=Cards("4K")
        card2.add_point(4)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.advance
        card=Cards("5H")
        card.add_point(5)
        card2=Cards("5K")
        card2.add_point(5)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.advance
        card=Cards("6H")
        card.add_point(6)
        card2=Cards("7K")
        card2.add_point(7)
        PlayerOrder.current.add_hand(card)
        PlayerOrder.current.add_guard(card2)
        PlayerOrder.advance
        Pile.pickup_pile
        card=Cards("2H")
        card.add_point(2)
        Pile.add_pile(card)
        expectedResult = "Table: \n" +
        "Bryan's Hand: List(Cards(3H))" + "\n" +
        "Guard: List(Cards(3K)), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List(Cards(4H))" + "\n" +
        "Guard: List(Cards(4K)), " + "Hidden Guard: List()" + "\n" +
        "Brayden's Hand: List(Cards(5H))" + "\n" +
        "Guard: List(Cards(5K)), " + "Hidden Guard: List()" + "\n" +
        "Angel's Hand: List(Cards(6H))" + "\n" +
        "Guard: List(Cards(7K)), " + "Hidden Guard: List()" + "\n"

        Menu.showGameArea should be(expectedResult)
        
        Menu.do_turn

        expectedResult= "Table: \n" +
        "Bryan's Hand: List()" + "\n" +
        "Guard: List(Cards(3K)), " + "Hidden Guard: List()" + "\n" +
        "Bryana's Hand: List()" + "\n" +
        "Guard: List(Cards(4K)), " + "Hidden Guard: List()" + "\n" +
        "Brayden's Hand: List()" + "\n" +
        "Guard: List(Cards(5K)), " + "Hidden Guard: List()" + "\n" +
        "Angel's Hand: List()" + "\n" +
        "Guard: List(Cards(7K)), " + "Hidden Guard: List()" + "\n"
        
        Menu.showGameArea should be(expectedResult)
        Pile.size_of_pile should be(6)
      }
    }
      describe("Plays the whole game"){
      it("Checks Whole Game") {
        // Step 1 - initializeGame

        Menu.init_game

        var expectedGameResult = "Table: \n" +
        "Bryan's Hand: List(Cards(KC),Cards(4H),Cards(8H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"

        Menu.showGameArea should be(expectedGameResult)

        // Step 2 - play the game

        var falseExpected = "False"
        var expected = 0
        Menu.do_game
        var deck_size = Deck.get_size_of_deck
        deck_size should be(expected)
        val winner = "Brayden"
        Menu.check_winner should be(winner)
      
      }
    } 
    describe("Tests Setting Strategies and Display them"){
      it("Sets the strategey"){
        // Here we are going to make sure that we can change the strategy for the player 
        PlayerOrder.current.get_mov_strat.show should be ("First Valid")
        PlayerOrder.current.get_guard_strat.show should be ("First 3 Cards")
        Menu.init_game
        Menu.Set("Bryan",2)// This function will work the following way if the second parameter is 1 or 2 the players move strategy will be set to the according strategy and if the 2nd parameter is the 3 or 4 the players Guard strategy will be switched accordingly.
        Menu.Set("Bryan",4)
        PlayerOrder.current.get_mov_strat.show should be ("Highest Option")
        PlayerOrder.current.get_guard_strat.show should be ("Strong Guard")
        Menu.Set("Bryana",2)
        Menu.Set("Bryana",4)
        PlayerOrder.advance
        PlayerOrder.current.get_mov_strat.show should be ("Highest Option")
        PlayerOrder.current.get_guard_strat.show should be ("Strong Guard")

        
      }
      it("Displays the strategies"){

        var result= "Bryan: Highest Option, Strong Guard"+"\n"+
        "Bryana: Highest Option, Strong Guard"+"\n"+
        "Brayden: First Valid, First 3 Cards"+"\n"+
        "Angel: First Valid, First 3 Cards"+"\n"
        
        Table.show_strategey should be(result)

        //Now we are going to change one of the players 
        Menu.Set("Brayden",2)
        Menu.Set("Brayden",4)
        
        result="Bryan: Highest Option, Strong Guard"+"\n"+
        "Bryana: Highest Option, Strong Guard"+"\n"+
        "Brayden: Highest Option, Strong Guard"+"\n"+
        "Angel: First Valid, First 3 Cards"+"\n"

        Table.show_strategey should be(result)
      }
    }
    describe("Checks Strategies"){
      it("Tests Strategy 2 (Play Highest card)"){
        Menu.Set("Bryan",3)// This makes Bryan have strategy 3 AKA Default Guard strat 
        Menu.Set("Brayden",3)
        Menu.Set("Bryana",3)
        Menu.Set("Angel",3)
        Menu.init_game
        
          val expectedResult2 = "Table: \n" +
          "Bryan's Hand: List(Cards(KC),Cards(4H),Cards(8H))" + "\n" +
          "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
          "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
          "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
          "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
          "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
          "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
          "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        Menu.showGameArea should be(expectedResult2)
        Pile.size_of_pile should be(1)
        var card = Cards("3H")
        card.add_point(3)
        Pile.add_pile(card)
        card = Cards("5H")
        card.add_point(5)
        PlayerOrder.current.add_hand(card) // We are adding this to make sure that the player has a valid card card in the 
        //first slot but we want to play the top card which is the KC
        //Now that we set up the scenario ther pile is showing 3H since we are testing the Highest card first
        //the card that should be played is the KC
        Menu.do_move
        var expectedResult = "Table: \n" +
        "Bryan's Hand: List(Cards(8H),Cards(5H),Cards(4H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        Menu.showGameArea should be(expectedResult)
        Pile.get_top_card.get_point should be (13) //Payed the KC
        card = Cards("3H")
        card.add_point(3)
        Pile.add_pile(card)
        card = Cards("6H")
        card.add_point(6)
        PlayerOrder.current.add_hand(card)
        //Now we do it again just to verify since we know our do move works we just want to make sure it works again.
        Menu.do_move
        expectedResult = "Table: \n" +
        "Bryan's Hand: List(Cards(8H),Cards(5H),Cards(4H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(9H),Cards(6H),Cards(5H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        Menu.showGameArea should be(expectedResult)
        // Bryana played the AC
        Pile.get_top_card.get_point should be (14)
      }

      it("Tests Strategy 3 (Default Guard Strategy: First 3 Cards)"){
        Menu.Set("Bryan",3)// This makes Bryan have strategy 3 AKA Default Guard strat 
        Menu.Set("Brayden",3)
        Menu.Set("Bryana",3)
        Menu.Set("Angel",3)
        Menu.init_game
        val expectedResult = "Table: \n" +
        "Bryan's Hand: List(Cards(KC),Cards(4H),Cards(8H))" + "\n" +
        "Guard: List(Cards(AD),Cards(5C),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
        "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
        "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
        "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
        "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
        "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
        "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        Menu.showGameArea should be(expectedResult)
        // Here we see that Bryan's guard changed to have the highest value cards 
      }
      it("Tests Strategy 4 (Guard Strategy: Strong Guard)"){
        Menu.Set("Bryan", 4) // This makes Bryan have strategy 4 AKA Strong guard
        Menu.init_game

        // testing an updated expectedResult to see if it fits with the newly sorted hand features
        val expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(8H),Cards(5C),Cards(4H))" + "\n" +
          "Guard: List(Cards(AD),Cards(KC),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
          "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
          "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
          "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
          "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
          "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
          "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        
        print("Player Order: " + Menu.showPlayerOrder + "\n")

        /* original "expectedResult" for this section: (my change was to have the cards sorted in the hand and the guard from left to right, high to lo, after pulling the highest three cards)

        val expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(5C),Cards(4H),Cards(8H))" + "\n" +
          "Guard: List(Cards(AD),Cards(KC),Cards(9C)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
          "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
          "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
          "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
          "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
          "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
          "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        */
        
        Menu.showGameArea should be(expectedResult)
        // Here we see that Bryan's guard changed to have the low value cards from his hand
      }

      it("Tests Strategy 5 NEW (Guard Strategy: Weak Guard)"){
        Menu.Set("Bryan", 5) // This makes Bryan have strategy 5 AKA Weak guard
        Menu.init_game

        // testing an updated expectedResult to see if it fits with the newly sorted hand features
        val expectedResult = "Table: \n" +
          "Bryan's Hand: List(Cards(9C),Cards(KC),Cards(AD))" + "\n" +
          "Guard: List(Cards(4H),Cards(5C),Cards(8H)), " + "Hidden Guard: List(Cards(2D),Cards(6D),Cards(10D))" + "\n" +
          "Bryana's Hand: List(Cards(AC),Cards(5H),Cards(9H))" + "\n" +
          "Guard: List(Cards(2C),Cards(6C),Cards(10C)), " + "Hidden Guard: List(Cards(3D),Cards(7D),Cards(JD))" + "\n" +
          "Brayden's Hand: List(Cards(2H),Cards(6H),Cards(10H))" + "\n" +
          "Guard: List(Cards(3C),Cards(7C),Cards(JC)), " + "Hidden Guard: List(Cards(4D),Cards(8D),Cards(QD))" + "\n" +
          "Angel's Hand: List(Cards(3H),Cards(7H),Cards(JH))" + "\n" +
          "Guard: List(Cards(4C),Cards(8C),Cards(QC)), " + "Hidden Guard: List(Cards(5D),Cards(9D),Cards(KD))" + "\n"
        Menu.showGameArea should be(expectedResult)// Makaing sure that the player does pick the lowest card 
        Menu.Set("Bryana",4)
        Menu.Set("Brayden",2)
        Menu.Set("Brayden",4)
        
        var result="Bryan: Highest Option, Weak Guard"+"\n"+
        "Bryana: Highest Option, Strong Guard"+"\n"+
        "Brayden: Highest Option, Strong Guard"+"\n"+
        "Angel: First Valid, First 3 Cards"+"\n"

        Table.show_strategey should be(result) // Make sure that weak guard can be shown in the strategy screen

      
      }
      it ("Test that Deck can be shuffled (Beware test can fail due to random luck, if so run it again)"){
        var top_card= Deck.get_head
        top_card.get_point should be(13)
        Deck.shufle_deck
        top_card= Deck.get_head
        top_card.get_point should not be(13)
        
      }
    }
  }
}
