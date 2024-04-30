//Ankit Garg

package model;

public class Card implements Comparable<Card> {

	Rank rank;
	Suit suit;

	public Card(Rank cardRank, Suit cardSuit) {
		rank = cardRank;
		suit = cardSuit;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue() {
		return rank.getValue();
	}

	public String toString() {
		Integer cardRank = this.getValue();
		Suit cardSuit = this.getSuit();

		char suit = 'a';

		if (cardSuit == Suit.CLUBS) {
			suit = '\u2663';
		} else if (cardSuit == Suit.SPADES) {
			suit = '\u2660';
		} else if (cardSuit == Suit.DIAMONDS) {
			suit = '\u2666';
		} else if (cardSuit == Suit.HEARTS) {
			suit = '\u2665';
		}

		return cardRank.toString() + suit;
	}

	@Override
	public int compareTo(Card other) {
		if (this.rank.getValue() > other.rank.getValue()) {
			return 1;
		} else if (this.rank.getValue() < other.rank.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}

	public boolean equals(Card other) {
		if (this.compareTo(other) == 0) {
			return true;
		} else {
			return false;
		}
	}
}