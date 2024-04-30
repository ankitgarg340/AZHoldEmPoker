//Ankit Garg
package model;

import java.util.ArrayList;
import java.util.Random;

public class Player {
	Card[] playerCards;
	double holdings;
	PokerHand bestHand;
	Card[] communityCards;
	String name;

	public Player(ArrayList<Card> deck, Card[] communityCardsArr, int playerNum) {
		playerCards = drawTwo(deck);
		communityCards = communityCardsArr;
		bestHand = findBestHand(findAllHands(communityCards));
		name = "Player " + playerNum;
		holdings = 100.00;
	}

	public Card[] drawTwo(ArrayList<Card> deck) {
		Card[] playerHand = new Card[2];
		Random rand = new Random();

		Card card1 = deck.remove(rand.nextInt(0, deck.size()));
		Card card2 = deck.remove(rand.nextInt(0, deck.size()));

		playerHand[0] = card1;
		playerHand[1] = card2;

		return playerHand;
	}

	public void resetPlayerCards(ArrayList<Card> deck, Card[] newCommCards) {
		playerCards = drawTwo(deck);
		bestHand = findBestHand(findAllHands(newCommCards));
	}

	public double returnHolding() {
		return holdings;
	}

	public PokerHand returnBestHand() {
		return bestHand;
	}

	public void collectAnte() {
		holdings -= 2.00;
	}

	public void addWinnings(double winning) {
		double holdingToRound = holdings;
		holdingToRound += winning;

		holdingToRound = Math.round(holdingToRound * 100);

		holdings = holdingToRound / 100;
	}

	public String getName() {
		return name;
	}

	public void printPlayer() {
		System.out.println(name);
		System.out.printf("Holdings: %.2f", holdings);
		System.out.println();
		System.out.println("Cards: " + playerCards[0].toString() + playerCards[1].toString());
		System.out.println("Highest Hand: " + bestHand.printHand());
	}

	public PokerHand findBestHand(PokerHand[] allHands) {
		int i = 0;
		while (i < allHands.length) {
			PokerHand toCheck = allHands[i];
			boolean isHighest = true;
			int i2 = 0;
			while (i2 < allHands.length && isHighest) {
				if (toCheck.compareTo(allHands[i2]) < 0) {
					isHighest = false;
				} else {
					if (i2 == 20) {
						return toCheck;
					}
					i2++;
				}
			}
			i++;
		}
		return allHands[0];
	}

	private PokerHand[] findAllHands(Card[] communityCards) {
		Card[] allCards = new Card[7];
		for (int i = 0; i < 5; i++) {
			allCards[i] = communityCards[i];
		}
		allCards[5] = playerCards[0];
		allCards[6] = playerCards[1];

		PokerHand[] allHands = new PokerHand[21];
		allHands[0] = genPokerHand(allCards, 0, 1, 2, 3, 4);
		allHands[1] = genPokerHand(allCards, 0, 1, 2, 3, 5);
		allHands[2] = genPokerHand(allCards, 0, 1, 2, 3, 6);
		allHands[3] = genPokerHand(allCards, 0, 1, 2, 4, 5);
		allHands[4] = genPokerHand(allCards, 0, 1, 2, 4, 6);
		allHands[5] = genPokerHand(allCards, 0, 1, 2, 5, 6);
		allHands[6] = genPokerHand(allCards, 0, 1, 3, 4, 5);
		allHands[7] = genPokerHand(allCards, 0, 1, 3, 4, 6);
		allHands[8] = genPokerHand(allCards, 0, 1, 3, 5, 6);
		allHands[9] = genPokerHand(allCards, 0, 1, 4, 5, 6);
		allHands[10] = genPokerHand(allCards, 0, 2, 3, 4, 5);
		allHands[11] = genPokerHand(allCards, 0, 2, 3, 4, 6);
		allHands[12] = genPokerHand(allCards, 0, 2, 3, 5, 6);
		allHands[13] = genPokerHand(allCards, 0, 2, 4, 5, 6);
		allHands[14] = genPokerHand(allCards, 0, 3, 4, 5, 6);
		allHands[15] = genPokerHand(allCards, 1, 2, 3, 4, 5);
		allHands[16] = genPokerHand(allCards, 1, 2, 3, 4, 6);
		allHands[17] = genPokerHand(allCards, 1, 2, 3, 5, 6);
		allHands[18] = genPokerHand(allCards, 1, 2, 4, 5, 6);
		allHands[19] = genPokerHand(allCards, 1, 3, 4, 5, 6);
		allHands[20] = genPokerHand(allCards, 2, 3, 4, 5, 6);

		return allHands;
	}

	private PokerHand genPokerHand(Card[] allCard, int i1, int i2, int i3, int i4, int i5) {
		return new PokerHand(allCard[i1], allCard[i2], allCard[i3], allCard[i4], allCard[i5]);
	}
}
