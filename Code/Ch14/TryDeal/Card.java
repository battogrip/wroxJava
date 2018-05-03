
public class Card implements Comparable<Card> {
  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public String toString() {
    return rank + " of " + suit;
  }

  // Compare two cards
  public int compareTo(Card card) {
    if(suit.equals(card.suit)) {                      // First compare suits
      if(rank.equals(card.rank)) {                  // So check face values 
        return 0;                                     // They are equal
      }
      return rank.compareTo(card.rank)<0 ? -1 : 1;
    } else {                                          // Suits are the same
      return suit.compareTo(card.suit)<0 ? -1 : 1;    // Sequence is C<D<H<S
    }
  }

  private Suit suit;
  private Rank rank;
}
