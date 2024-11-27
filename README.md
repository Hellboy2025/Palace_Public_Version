# Running the game.
Open a terminal and execute sbt ~run.

## Equipment:

The Palace game is played on a table that displays the player's hand, their three hidden cards, and their three revealed cards. In the center, a deck of cards with a counter over them will display the number of cards left in the deck.

## Game Play:

At the beginning of the game, each player is dealt three cards face down, which will be arranged in a line. These are called your hidden guard cards. Then, each player will get another six cards and pick 3 to put down face up right above the three face-down cards. These will be called your guard. Then, the game begins when a card is taken from the top of the deck and placed face up in the center of the table; we will call this the pile. The player, clockwise to the dealer, will have to place a card with a higher number than the one on the pile or place a number 2 card that will reset the pile. Then, it’s the next player's turn. If there are still cards in the deck, the player will pick up the number of cards needed to have three cards if the player has less than three cards in his hand. 

The card values go from 3-9, then Queen, King, and Ace. With each turn, the player must have a higher level card than the one on the top of the pile, or they will have to take the whole pile to their hand. 

Finally, once a player has no cards in his hand and no more cards are on the deck, he will use the guard cards first. Once you have expended your guard cards, you will gain access to a single hidden guard card, which the player will pick randomly. If, during this process, you have to pick up the pile, you must expend all the cards in your hand before you can go on to use your hidden guard cards.

Note that the Simulation watcher will have the opportunity to skip a player as long as the player has a valuable card to play.

## Objective
A player wins the game once he has expended all the cards in his hand, hidden guard cards, and guard cards. 

## Strategies
The strategies we have selected for Palace mainly reside on which card to play according to their rank. As players conduct a turn, they evaluate the cards they have and select which one to play (if they can play one at all). Once a player "decides" (i.e. once a player is assigned a strategy that tells them which card to play) which card to play, it means that they have a valid card. If they do not play a card but rather pick up the pile, this means that regardless of which strategy they were assigned, they did not possess a valid card to play. If a player is told they have a valid card to play, they will play it. If they do not (and they still have cards remaining), they will pick up the pile, enabling the player after them to play any card they so desire. The goal of this game is to be the first player to run out of cards after all the cards on the deck are gone as well. Palace is over once a winner is annouched. This winner will be the first player to run out of cards in their Hand, Guard, and HiddenGuard collectively.

###  First Balid (Basic Strategy for Guard)
Our first strategy, BASIC, is rooted in the player whose turn is currently happening and being told (a.k.a. "making the decision") to play the first valid card from left to right. What this means is that if a player still has a Hand, then they must play the first valid card from left to right in their hand. If the player does not have a hand, then they must play the first valid card from their Guard (* so long as the deck is empty). A player cannot access their Guard until the deck is empty, and a player cannot access their HiddenGuard until they no longer have a Guard. If a player does not have a Hand nor a Guard, then they would play the first valid card from their HiddenGuard that they can access.

### Lowest First
Our second strategy, LOWEST FIRST, is rooted in the player whose turn is currently happening being told (a.k.a. "making the decision") to play their lowest valid card. What this means is that if a player still has a Hand, then they must play the lowest valid card in that hand. If the player does not have a hand, then they must play the lowest valid card from their Guard (* so long as the deck is empty). A player still cannot access their Guard until the deck is empty, and a player cannot access their HiddenGuard until they no longer have a Guard. If a player does not have a Hand nor a Guard, then they would play the lowest valid card from their HiddenGuard that they can access. This is a rank based strategy, suit is not considered.

### Strong Guard (Basic Strategy for Guard)
When the game is initialized, the Player will pick the higher cards in their hand and use them as their guard. This means that the player will have a strong guard to play with after he finishes the cards on this hand.

### Weak Guard
When the game is initialized, the PLayer will pick the lowest cards in their hand and use them in their guard. This means that the player will have a weak guard to play with after he finishes the cards in his hand.

## Open Design, Fail-Safe Defaults, and Economy of Mechanism Principles

### Open Design
This implementation of Palace is well documented within ScalaDoc, detailing each function, as well as well documented within the code's own naming conventions. Each variable name has been carefully selected and designed in such a way as to promote a) ease of understanding, b) readability, and c) transference. For example, our showPlayerOrder, advancePlayerOrder, and shuffle methods all tell the reader exactly what they will be doing.

### Fail-Safe Defaults
While we may have missed this step in our first sprint (milestone), we soon realized just how important it is to be careful with who you give access to. This is why we very quickly changed our code to utilize the following fail-safe defaults, best exhibited by the Player Class: distinguishing between vals and vars, classifying variables as private, and using "get" functions rather than accessing the variables themselves. This limited the scope in which personnel and files could access the most important pieces of data. Yes, this is just a game, but these are the principles that, at a foundational level, help protect a myriad of personal and private information. Within this project, we were also very restrictive with what files we imported across the project to try and regulate "who" (who being which file) should have access to what.

### Economy of Mechanism
We were able to adhere to the economy of mechanism in many ways, the most prominent of which being organization. The purpose of the economy of mechanism is to keep the code simple. We have simplified it by breaking it up into digestible, bite-size chunks separated by file. We have also kept a detailed commit history, which also helps as one can go back and review, seeing the layers built in tandem.

### Observer Tests for Playing from Hand
In order to ensure quality control and proper regulation of this game, we have included a GUI testing document as a step-by-step guide in evaluating the proper response of the program's DO MOVE function. While this current version of Palace (as of 92305APR24) has not implemented all the strategies we are projecting to be tested in our guide, thus making the actual testing results ambiguous for now, it does include action and expected result for each step with the projection being done as if those strategies have been completely coded. One can find this file as "Assignment 7 - GUI Test on DO MOVE (PIC)" (a jpg).
