// Class defining a hand of cards
import java.util.Vector;
import java.util.Collections;
import java.util.List;

public class Hand {
  // Add a card to the hand
  public void add(Card card) {
    hand.add(card);
  }

  public String toString() {
    StringBuilder str = new StringBuilder();
    for(Card card : hand) {
      str.append(" "+ card);
    }
    return str.toString();
  }

  public Hand sort() {
    Collections.sort(hand);
    return this;
  }
      
  private Vector<Card> hand = new Vector<Card>();  // Stores a hand of cards
}
