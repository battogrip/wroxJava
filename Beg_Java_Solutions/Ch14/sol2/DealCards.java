// Chapter 14 Exercise 2

import java.util.Vector;
import java.util.LinkedList;
import java.util.Random;
import java.util.ListIterator;

public class DealCards {
  public static void main(String[] args) {
    String[] suits = {"C", "D", "H", "S"};
    String[] cardValues = { "1","2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    int cardsInDeck = 52;
    Vector<String> deck = new Vector<String>(cardsInDeck);
    LinkedList<String> shuffledDeck = new LinkedList<String>();
    Random chooser = new Random();             // Card chooser

    // Load the deck
    for(String suit : suits) {
      for(String cardValue : cardValues) {
        deck.add(cardValue+suit);
      }
  }

    // Select cards at random from the deck to transfer to shuffled deck
    int selection = 0;                        // Selected card index
    for(int i = 0 ; i<cardsInDeck ; i++) {
      selection = chooser.nextInt(deck.size());
      shuffledDeck.add(deck.remove(selection));
    }
    
    // Deal the cards from the shuffled deck into four hands
    StringBuffer[] hands = { new StringBuffer("Hand 1:"), new StringBuffer("Hand 2:"),
                             new StringBuffer("Hand 3:"), new StringBuffer("Hand 4:")};
    ListIterator cards = shuffledDeck.listIterator();
   
    while(cards.hasNext()) { 
      for(StringBuffer hand : hands) {
        hand.append(' ').append((String)(cards.next()));
      }
    }

    // Display the hands
    for(StringBuffer hand : hands) {
      System.out.println(hand.toString());
    }
  }
}
