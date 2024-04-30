//Ankit Garg
package model;

public enum Rank {

	DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6),

	SEVEN(7), EIGHT(8), NINE(9), TEN(10),

	JACK(11), QUEEN(12), KING(13), ACE(14);

	private int value;

	// Enums can also have constructors

	Rank(int value) {

		this.value = value;

	}

	public int getValue() {

		// Return the value of any enum, 2 through 14 (Ace)

		return value;

	}

}