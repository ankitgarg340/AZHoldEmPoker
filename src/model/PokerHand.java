//Ankit Garg

package model;

import java.util.*;

/*
 * When sorted, a list of PokerHand objects will be in ascending order.
 */

public class PokerHand implements Comparable<PokerHand> {
	Card[] hand;

	public PokerHand(Card c1, Card c2, Card c3, Card c4, Card c5) {
		Card[] pokerHand = new Card[5];
		pokerHand[0] = c1;
		pokerHand[1] = c2;
		pokerHand[2] = c3;
		pokerHand[3] = c4;
		pokerHand[4] = c5;

		hand = pokerHand;
	}

	public String printHand() {
		String returnString = "";
		for (int i = 0; i < 5; i++) {
			returnString += hand[i].toString();
		}
		return returnString + " -- " + findHandName();
	}

	@Override
	public int compareTo(PokerHand o) {
		int scoreThis = this.generateScore();
		int scoreThat = o.generateScore();

		if (scoreThis > scoreThat) {
			return 1;
		} else if (scoreThis < scoreThat) {
			return -1;
		} else {
			if (scoreThis == 100 || scoreThis == 60) {
				return this.compareStraight(o);
			} else if (scoreThis == 90) {
				return this.compareFourOfKind(o);
			} else if (scoreThis == 80) {
				return this.compareFullHouse(o);
			} else if (scoreThis == 70) {
				return this.compareFlush(o);
			} else if (scoreThis == 50) {
				return this.compareThreeOfKind(o);
			} else if (scoreThis == 40) {
				return this.compareTwoPair(o);
			} else if (scoreThis == 30) {
				return this.comparePair(o);
			} else {
				return this.compareHighCard(o);
			}
		}
	}

	public String findHandName() {
		int score = this.generateScore();
		if (score == 100) {
			ArrayList<Integer> ranks = this.ranksList();
			ranks.sort(Comparator.naturalOrder());
			if (ranks.get(4) == 14) {
				return "Royal Flush";
			} else {
				return "Straight Flush";
			}
		} else if (score == 90) {
			return "Four of a Kind";
		} else if (score == 80) {
			return "Full House";
		} else if (score == 70) {
			return "Flush";
		} else if (score == 60) {
			return "Straight";
		} else if (score == 50) {
			return "Three of a Kind";
		} else if (score == 40) {
			return "Two Pair";
		} else if (score == 30) {
			return "Pair";
		} else {
			return "High Card";
		}
	}

	private int generateScore() {
		if (this.isFlush() && this.isStraight()) {
			return 100;
		} else if (this.isFourOfKind()) {
			return 90;
		} else if (this.isFullHouse()) {
			return 80;
		} else if (this.isFlush()) {
			return 70;
		} else if (this.isStraight()) {
			return 60;
		} else if (this.isThreeOfKind()) {
			return 50;
		} else if (this.isTwoPair()) {
			return 40;
		} else if (this.isPair()) {
			return 30;
		} else {
			return 20;
		}
	}

	private boolean isFlush() {
		Suit toCheck = hand[0].getSuit();

		int i = 1;
		while (i < 5) {
			if (hand[i].getSuit() != toCheck) {
				return false;
			}
			i++;
		}
		return true;
	}

	private int compareFlush(PokerHand o) {
		ArrayList<Integer> ranksThis = this.ranksList();
		ArrayList<Integer> ranksOther = o.ranksList();

		ranksThis.sort(Comparator.naturalOrder());
		ranksOther.sort(Comparator.naturalOrder());

		int i = 4;
		while (i >= 0) {
			if (ranksThis.get(i) > ranksOther.get(i)) {
				return 1;
			} else if (ranksThis.get(i) < ranksOther.get(i)) {
				return -1;
			} else {
				i--;
			}
		}
		return 0;
	}

	private int compareStraight(PokerHand o) {
		ArrayList<Integer> ranksThis = this.ranksList();
		ArrayList<Integer> ranksOther = o.ranksList();

		ranksThis.sort(Comparator.naturalOrder());
		ranksOther.sort(Comparator.naturalOrder());

		if (ranksThis.get(4) > ranksOther.get(4)) {
			return 1;
		} else if (ranksThis.get(4) < ranksOther.get(4)) {
			return -1;
		} else {
			return 0;
		}

	}

	private boolean isStraight() {
		ArrayList<Integer> ranks = this.ranksList();
		ranks.sort(Comparator.naturalOrder());

		int i = 3;
		while (i >= 0) {
			if (ranks.get(i + 1) - ranks.get(i) == 1) {
				i--;
			} else {
				return false;
			}
		}
		return true;
	}

	private boolean isFourOfKind() {
		int[] ranks = this.ranksArray();
		int i = 0;
		while (i < 15) {
			if (ranks[i] == 4) {
				return true;
			}
			i++;
		}
		return false;
	}

	private int compareFourOfKind(PokerHand o) {
		int[] ranksThis = this.ranksArray();
		int[] ranksOther = o.ranksArray();

		int rankOfFourThis = 0;
		int rankOfFourOther = 0;

		int rankOfOddCardThis = 0;
		int rankOfOddCardOther = 0;

		int i = 0;
		while (i < 15) {
			if (ranksThis[i] == 4) {
				rankOfFourThis = i;
			} else if (ranksThis[i] == 1) {
				rankOfOddCardThis = i;
			}

			if (ranksOther[i] == 4) {
				rankOfFourOther = i;
			} else if (ranksOther[i] == 1) {
				rankOfOddCardOther = i;
			}
			i++;
		}

		if (rankOfFourThis > rankOfFourOther) {
			return 1;
		} else if (rankOfFourThis < rankOfFourOther) {
			return -1;
		} else {
			if (rankOfOddCardThis > rankOfOddCardOther) {
				return 1;
			} else if (rankOfOddCardThis < rankOfOddCardOther) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	private boolean isFullHouse() {
		int[] ranks = this.ranksArray();

		int i2 = 0;
		boolean hasPair = false;
		boolean hasThreeKind = false;
		while (i2 < 15) {
			if (ranks[i2] == 2) {
				hasPair = true;
			}
			if (ranks[i2] == 3) {
				hasThreeKind = true;
			}
			i2++;
		}

		return (hasPair && hasThreeKind);
	}

	private int compareFullHouse(PokerHand o) {
		int[] ranksThis = this.ranksArray();
		int[] ranksOther = o.ranksArray();

		int rankOfThreeThis = 0;
		int rankOfThreeOther = 0;

		int rankOfPairThis = 0;
		int rankOfPairOther = 0;
		int i = 0;
		while (i < 15) {
			if (ranksThis[i] == 3) {
				rankOfThreeThis = i;
			} else if (ranksThis[i] == 2) {
				rankOfPairThis = i;
			}

			if (ranksOther[i] == 3) {
				rankOfThreeOther = i;
			} else if (ranksOther[i] == 2) {
				rankOfPairOther = i;
			}

			i++;
		}

		if (rankOfThreeThis > rankOfThreeOther) {
			return 1;
		} else if (rankOfThreeThis < rankOfThreeOther) {
			return -1;
		} else {
			if (rankOfPairThis > rankOfPairOther) {
				return 1;
			} else if (rankOfPairThis < rankOfPairOther) {
				return -1;
			}
			return 0;
		}
	}

	private boolean isThreeOfKind() {
		int[] ranks = this.ranksArray();

		int i2 = 0;
		while (i2 < 15) {
			if (ranks[i2] == 3) {
				return true;
			}
			i2++;
		}
		return false;
	}

	private int compareThreeOfKind(PokerHand o) {
		int[] ranksThis = this.ranksArray();
		int[] ranksOther = o.ranksArray();

		int rankOfThreeThis = 0;
		int rankOfThreeOther = 0;

		ArrayList<Integer> rankOfNonThreeThis = new ArrayList<Integer>();
		ArrayList<Integer> rankOfNonThreeOther = new ArrayList<Integer>();

		int i = 0;
		while (i < 15) {
			if (ranksThis[i] == 3) {
				rankOfThreeThis = i;
			} else if (ranksThis[i] == 1) {
				rankOfNonThreeThis.add(i);
			}

			if (ranksOther[i] == 3) {
				rankOfThreeOther = i;
			} else if (ranksOther[i] == 1) {
				rankOfNonThreeOther.add(i);
			}

			i++;
		}

		if (rankOfThreeThis > rankOfThreeOther) {
			return 1;
		} else if (rankOfThreeThis < rankOfThreeOther) {
			return -1;
		} else {
			rankOfNonThreeThis.sort(Comparator.naturalOrder());
			rankOfNonThreeOther.sort(Comparator.naturalOrder());

			int i2 = 1;
			while (i2 >= 0) {
				if (rankOfNonThreeThis.get(i2) > rankOfNonThreeOther.get(i2)) {
					return 1;
				} else if (rankOfNonThreeThis.get(i2) < rankOfNonThreeOther.get(i2)) {
					return -1;
				} else {
					i2--;
				}
			}
			return 0;

		}

	}

	private boolean isTwoPair() {
		int[] ranks = this.ranksArray();

		int i = 0;
		int pairCounter = 0;
		while (i < 15) {
			if (ranks[i] == 2) {
				pairCounter++;
				i++;
			} else {
				i++;
			}
		}
		return (pairCounter == 2);
	}

	private int compareTwoPair(PokerHand o) {
		int[] ranksThis = this.ranksArray();
		int[] ranksOther = o.ranksArray();

		int rankOfLowPairThis = 0;
		int rankOfHighPairThis = 0;
		int rankOfOddCardThis = 0;

		int rankOfLowPairOther = 0;
		int rankOfHighPairOther = 0;
		int rankOfOddCardOther = 0;

		int i = 0;
		while (i < 15) {
			if (ranksThis[i] == 1) {
				rankOfOddCardThis = i;
			}
			if (ranksOther[i] == 1) {
				rankOfOddCardOther = i;
			}

			if (rankOfLowPairThis == 0 && ranksThis[i] == 2) {
				rankOfLowPairThis = i;
			} else if (ranksThis[i] == 2) {
				rankOfHighPairThis = i;
			}

			if (rankOfLowPairOther == 0 && ranksOther[i] == 2) {
				rankOfLowPairOther = i;
			} else if (ranksOther[i] == 2) {
				rankOfHighPairOther = i;
			}

			i++;
		}

		if (rankOfHighPairThis > rankOfHighPairOther) {
			return 1;
		} else if (rankOfHighPairThis < rankOfHighPairOther) {
			return -1;
		} else {
			if (rankOfLowPairThis > rankOfLowPairOther) {
				return 1;
			} else if (rankOfLowPairThis < rankOfLowPairOther) {
				return -1;
			} else {
				if (rankOfOddCardThis > rankOfOddCardOther) {
					return 1;
				} else if (rankOfOddCardThis < rankOfOddCardOther) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	private boolean isPair() {
		int[] ranks = this.ranksArray();

		int i = 0;
		while (i < 15) {
			if (ranks[i] == 2) {
				return true;
			}
			i++;
		}
		return false;
	}

	private int comparePair(PokerHand o) {
		int[] ranksThis = this.ranksArray();
		int[] ranksOther = o.ranksArray();

		ArrayList<Integer> ranksNonPairThis = new ArrayList<Integer>();
		ArrayList<Integer> ranksNonPairOther = new ArrayList<Integer>();

		int rankOfPairThis = 0;
		int rankOfPairOther = 0;

		int i = 0;
		while (i < 15) {
			if (ranksThis[i] == 2) {
				rankOfPairThis = i;
			} else if (ranksThis[i] == 1) {
				ranksNonPairThis.add(i);
			}

			if (ranksOther[i] == 2) {
				rankOfPairOther = i;
			} else if (ranksOther[i] == 1) {
				ranksNonPairOther.add(i);
			}

			i++;
		}

		if (rankOfPairThis > rankOfPairOther) {
			return 1;
		} else if (rankOfPairThis < rankOfPairOther) {
			return -1;
		} else {
			ranksNonPairThis.sort(Comparator.naturalOrder());
			ranksNonPairOther.sort(Comparator.naturalOrder());

			int i2 = 2;
			while (i2 >= 0) {
				if (ranksNonPairThis.get(i2) > ranksNonPairOther.get(i2)) {
					return 1;
				} else if (ranksNonPairThis.get(i2) < ranksNonPairOther.get(i2)) {
					return -1;
				} else {
					i2--;
				}
			}
			return 0;
		}
	}

	private int compareHighCard(PokerHand o) {
		ArrayList<Integer> ranksThis = this.ranksList();
		ArrayList<Integer> ranksOther = o.ranksList();

		ranksThis.sort(Comparator.naturalOrder());
		ranksOther.sort(Comparator.naturalOrder());

		int i = 4;
		while (i >= 0) {
			if (ranksThis.get(i) > ranksOther.get(i)) {
				return 1;
			} else if (ranksThis.get(i) < ranksOther.get(i)) {
				return -1;
			}
			i--;
		}
		return 0;

	}

	private int[] ranksArray() {
		int[] ranks = new int[15];
		int i = 0;
		while (i < 5) {
			int cardValue = hand[i].getValue();
			ranks[cardValue]++;
			i++;
		}
		return ranks;
	}

	private ArrayList<Integer> ranksList() {
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		int i = 0;
		while (i < 5) {
			ranks.add(hand[i].getValue());
			i++;
		}
		return ranks;
	}

}
