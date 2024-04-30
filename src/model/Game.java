//Ankit Garg
package model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Game {
	ArrayList<Card> deck;
	Card[] communityCards;
	Player[] playersArr;

	public Game() {
		deck = generateDeck();
		communityCards = generateCommunityCards(deck);
		playersArr = null;
	}

	public void start() {
		Scanner kb = new Scanner(System.in);

		System.out.println("How many players?");
		String players = kb.nextLine();
		System.out.println();

		Integer playerNum = Integer.parseInt(players);
		playersArr = new Player[playerNum];

		for (int i = 0; i < playerNum; i++) {
			playersArr[i] = new Player(deck, communityCards, i + 1);
			playersArr[i].collectAnte();
		}

		System.out.print("Community Cards: ");
		for (int i = 0; i < 5; i++) {
			System.out.print(communityCards[i].toString());
		}

		System.out.println();
		System.out.println();

		for (int i = 0; i < playersArr.length; i++) {
			playersArr[i].printPlayer();
			System.out.println();
		}

		ArrayList<Player> winningPlayers = findBestPlayer();

		giftWinners(winningPlayers);
		presentWinner(winningPlayers);

		System.out.println();
		System.out.println();

		String answer = "";
		while (!(answer.equals("y") || answer.equals("n"))) {
			System.out.println("Play another game? <y or n> ");
			answer = kb.nextLine();
			System.out.println();
		}

		if (answer.equals("y")) {
			this.restart();
		}

		kb.close();
	}

	public void restart() {
		deck = generateDeck();
		communityCards = generateCommunityCards(deck);

		for (int i = 0; i < playersArr.length; i++) {
			playersArr[i].resetPlayerCards(deck, communityCards);
			playersArr[i].collectAnte();
		}

		System.out.print("Community Cards: ");
		for (int i = 0; i < 5; i++) {
			System.out.print(communityCards[i].toString());
		}

		System.out.println();
		System.out.println();

		for (int i = 0; i < playersArr.length; i++) {
			playersArr[i].printPlayer();
			System.out.println();
		}
		ArrayList<Player> winningPlayers = findBestPlayer();

		giftWinners(winningPlayers);
		presentWinner(winningPlayers);

		System.out.println();

		Scanner kb = new Scanner(System.in);
		String answer = "";
		while (!(answer.equals("y") || answer.equals("n"))) {
			System.out.println("Play another game? <y or n> ");
			answer = kb.nextLine();
		}

		if (answer.equals("y")) {
			this.restart();
		} else {
			kb.close();
		}

	}

	private void presentWinner(ArrayList<Player> winners) {
		if (winners.size() == 1) {
			System.out.print("Winner: ");
			System.out.print(winners.get(0).getName());
			System.out.println();
			System.out.print("Holdings: $");
			System.out.printf("%.2f", winners.get(0).returnHolding());
			System.out.println();
			System.out.print("Winning Hand: ");
			System.out.print(winners.get(0).returnBestHand().printHand());
			System.out.println();
		} else {
			System.out.println("Winners: ");
			for (int i = 0; i < winners.size(); i++) {
				System.out.print(winners.get(i).getName());
				System.out.print(" (with hand " + winners.get(i).returnBestHand().printHand() + ")");
				System.out.println();
				System.out.printf("Holdings: $%.2f", winners.get(i).returnHolding());
				System.out.println();
				System.out.println();
			}
		}

	}

	private ArrayList<Card> generateDeck() {
		ArrayList<Card> cardDeck = new ArrayList<Card>();

		Suit toAdd = Suit.CLUBS;
		cardDeck.add(new Card(Rank.DEUCE, toAdd));
		cardDeck.add(new Card(Rank.THREE, toAdd));
		cardDeck.add(new Card(Rank.FOUR, toAdd));
		cardDeck.add(new Card(Rank.FIVE, toAdd));
		cardDeck.add(new Card(Rank.SIX, toAdd));
		cardDeck.add(new Card(Rank.SEVEN, toAdd));
		cardDeck.add(new Card(Rank.EIGHT, toAdd));
		cardDeck.add(new Card(Rank.NINE, toAdd));
		cardDeck.add(new Card(Rank.TEN, toAdd));
		cardDeck.add(new Card(Rank.JACK, toAdd));
		cardDeck.add(new Card(Rank.QUEEN, toAdd));
		cardDeck.add(new Card(Rank.KING, toAdd));
		cardDeck.add(new Card(Rank.ACE, toAdd));

		toAdd = Suit.DIAMONDS;
		cardDeck.add(new Card(Rank.DEUCE, toAdd));
		cardDeck.add(new Card(Rank.THREE, toAdd));
		cardDeck.add(new Card(Rank.FOUR, toAdd));
		cardDeck.add(new Card(Rank.FIVE, toAdd));
		cardDeck.add(new Card(Rank.SIX, toAdd));
		cardDeck.add(new Card(Rank.SEVEN, toAdd));
		cardDeck.add(new Card(Rank.EIGHT, toAdd));
		cardDeck.add(new Card(Rank.NINE, toAdd));
		cardDeck.add(new Card(Rank.TEN, toAdd));
		cardDeck.add(new Card(Rank.JACK, toAdd));
		cardDeck.add(new Card(Rank.QUEEN, toAdd));
		cardDeck.add(new Card(Rank.KING, toAdd));
		cardDeck.add(new Card(Rank.ACE, toAdd));

		toAdd = Suit.HEARTS;
		cardDeck.add(new Card(Rank.DEUCE, toAdd));
		cardDeck.add(new Card(Rank.THREE, toAdd));
		cardDeck.add(new Card(Rank.FOUR, toAdd));
		cardDeck.add(new Card(Rank.FIVE, toAdd));
		cardDeck.add(new Card(Rank.SIX, toAdd));
		cardDeck.add(new Card(Rank.SEVEN, toAdd));
		cardDeck.add(new Card(Rank.EIGHT, toAdd));
		cardDeck.add(new Card(Rank.NINE, toAdd));
		cardDeck.add(new Card(Rank.TEN, toAdd));
		cardDeck.add(new Card(Rank.JACK, toAdd));
		cardDeck.add(new Card(Rank.QUEEN, toAdd));
		cardDeck.add(new Card(Rank.KING, toAdd));
		cardDeck.add(new Card(Rank.ACE, toAdd));

		toAdd = Suit.SPADES;
		cardDeck.add(new Card(Rank.DEUCE, toAdd));
		cardDeck.add(new Card(Rank.THREE, toAdd));
		cardDeck.add(new Card(Rank.FOUR, toAdd));
		cardDeck.add(new Card(Rank.FIVE, toAdd));
		cardDeck.add(new Card(Rank.SIX, toAdd));
		cardDeck.add(new Card(Rank.SEVEN, toAdd));
		cardDeck.add(new Card(Rank.EIGHT, toAdd));
		cardDeck.add(new Card(Rank.NINE, toAdd));
		cardDeck.add(new Card(Rank.TEN, toAdd));
		cardDeck.add(new Card(Rank.JACK, toAdd));
		cardDeck.add(new Card(Rank.QUEEN, toAdd));
		cardDeck.add(new Card(Rank.KING, toAdd));
		cardDeck.add(new Card(Rank.ACE, toAdd));

		return cardDeck;
	}

	private Card[] generateCommunityCards(ArrayList<Card> deck) {
		Card[] communityCards = new Card[5];

		Random rand = new Random();

		Card card1 = deck.remove(rand.nextInt(0, deck.size()));
		Card card2 = deck.remove(rand.nextInt(0, deck.size()));
		Card card3 = deck.remove(rand.nextInt(0, deck.size()));
		Card card4 = deck.remove(rand.nextInt(0, deck.size()));
		Card card5 = deck.remove(rand.nextInt(0, deck.size()));

		communityCards[0] = card1;
		communityCards[1] = card2;
		communityCards[2] = card4;
		communityCards[3] = card3;
		communityCards[4] = card5;

		return communityCards;
	}

	private ArrayList<Player> findBestPlayer() {
		ArrayList<Player> winners = new ArrayList<Player>();

		for (int i = 0; i < playersArr.length; i++) {
			PokerHand toCheck = playersArr[i].returnBestHand();
			boolean isHighest = true;
			int i2 = 0;
			while (isHighest && i2 < playersArr.length) {
				if (toCheck.compareTo(playersArr[i2].returnBestHand()) < 0) {
					isHighest = false;
				} else {
					if (i2 == playersArr.length - 1) {
						winners.add(playersArr[i]);
					}
					i2++;
				}
			}
		}

		return winners;

	}

	private void giftWinners(ArrayList<Player> winningPlayers) {
		double pot = 2 * Double.valueOf(playersArr.length);
		double winnings = pot / Double.valueOf(winningPlayers.size());

		for (int i = 0; i < winningPlayers.size(); i++) {
			winningPlayers.get(i).addWinnings(winnings);
		}
	}

}
