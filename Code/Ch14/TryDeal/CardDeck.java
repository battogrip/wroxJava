import java.util.Stack;
import java.util.Collections;

public class CardDeck {
  // Create a deck of 52 cards
  public CardDeck() {
    for(Suit suit : Suit.values())
      for(Rank rank : Rank.values())
        deck.push(new Card(rank, suit));
  }

  // Deal a hand
  public Hand dealHand(int numCards) {
    Hand hand = new Hand();
    for(int i = 0; i<numCards; i++) {
      hand.add((Card)deck.pop());
    }
    return hand;
  }
  
  // Shuffle the deck
  public void shuffle() {
    Collections.shuffle(deck);
  }

  private Stack<Card> deck = new Stack<Card>();
}
