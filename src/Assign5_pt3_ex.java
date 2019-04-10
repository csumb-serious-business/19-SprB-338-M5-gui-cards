import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Assign5_pt3_ex {
	static int NUM_CARDS_PER_HAND = 7;
	static int NUM_PLAYERS = 2;
	static JLabel[] botLabels = new JLabel[NUM_CARDS_PER_HAND];
	static JButton[] userLabels = new JButton[NUM_CARDS_PER_HAND];
	static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
	static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

	static private CardGameFramework highCardGame;
	static private CardTable myCardTable;
	static private int playerWinCount = 0;
	static private int computerWinCount = 0;
	static private int tieCount = 0;
	
	static Card[] unusedCardsPerPack;

	private static Boolean RemoveAll() {
		try {
			myCardTable.pnlComputerHand.removeAll();
			myCardTable.pnlHumanHand.removeAll();
			myCardTable.pnlPlayArea.removeAll();
		} catch (Exception s) {
			return false;
		}
		return true;
	}

	private static Boolean ValidateAll() {
		try {
			myCardTable.pnlPlayArea.validate();
			myCardTable.pnlComputerHand.validate();
			myCardTable.pnlHumanHand.validate();
			myCardTable.validate();
		} catch (Exception exp) {
			return false;
		}
		return true;
	}

	private static boolean AddLabelsForPlayers() {

		userLabels = buttonsList(highCardGame.getHand(1), true);
		botLabels = labelsList(highCardGame.getHand(0), false);

		for (JButton var : userLabels) {
			myCardTable.pnlHumanHand.add(var);
		}

		for (JLabel var : botLabels) {
			myCardTable.pnlComputerHand.add(var);
		}

		return true;

	}

	public static int compare(Card playerHuman, Card playerComp) {
		final char[] ranks = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X' };

		int humanValue;
		int compValue;

		for (humanValue = 0; humanValue < ranks.length && ranks[humanValue] != playerHuman.getValue(); humanValue++)
			;
		for (compValue = 0; compValue < ranks.length && ranks[compValue] != playerComp.getValue(); compValue++)
			;

		if (humanValue < compValue)
			return 0;
		else if (humanValue > compValue)
			return 1;
		else
			return -1;
	}

	// Heavy Hitting is done by this function
	private static void display(Card playerChoice, Card computerChoice) {
		int indexOf = 0;
		String playerPrompt = "Click on a card below to choose";
		String computerPrompt = "Computer says 'Please choose the higest value' to beat me ";
		String tieCountPrompt = "";
		
		if (RemoveAll()) // Can We Clear the Label
		{
			Hand playHand = new Hand();
			if (playerChoice != null && computerChoice != null) {
				playHand.takeCard(computerChoice);
				playHand.takeCard(playerChoice);
				playedCardLabels = labelsList(playHand, true);

				if (compare(playerChoice, computerChoice) == 1)
					playerWinCount++;

				else if (compare(playerChoice, computerChoice) == 0)
					computerWinCount++;
				else if( compare(playerChoice, computerChoice) == -1)
					tieCount++;
					

				playerPrompt = "[Status]User Wins: " + playerWinCount;
				computerPrompt = "[Status]Computer Wins: " + computerWinCount;
				tieCountPrompt =" [Ties]: " + tieCount;
			}

			AddLabelsForPlayers();
			if (playedCardLabels[0] != null || playedCardLabels[1] != null)
			// At the start of this we dont need any
			{
			for (indexOf = 0; indexOf < NUM_PLAYERS; indexOf++)
					myCardTable.pnlPlayArea.add(playedCardLabels[indexOf]);
			}

			myCardTable.pnlPlayArea.add(new JLabel(computerPrompt, 0));
			myCardTable.pnlPlayArea.add(new JLabel(playerPrompt +"\n" +tieCountPrompt , 0));
	

			ValidateAll();
			// If something goes wrong we know because this will return flase
		}
	}

	// Human Plays first each turn
	public static void main(String[] args) {
		int numPacksPerDeck = 1;
		int numJokersPerPack = 0;
		int numUnusedCardsPerPack = 0;
		unusedCardsPerPack = null;

		highCardGame = new CardGameFramework(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
				unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND);

		// You deal() from it (one statement).
		highCardGame.deal();
		highCardGame.sortHands();

		// establish main frame in which program will run
		myCardTable = new CardTable("HIGH Table - Phase 3", NUM_CARDS_PER_HAND, NUM_PLAYERS);
		myCardTable.setSize(800, 600);
		myCardTable.setLocationRelativeTo(null);
		myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myCardTable.setVisible(true);
		GUICard.loadCardIcons();

		display(null, null); // Start off with nothing selected
		myCardTable.setVisible(true);

	}

	static JLabel[] labelsList(Hand myHand, boolean displayCards) {
		JLabel[] handLabels = new JLabel[myHand.getNumCards()];
		Card handCard = myHand.inspectCard(1);
		int i = 1;
		// Inspect to get the validity, hence the while loop will exit if
		// recieved flag
		while (!handCard.getErrorFlag()) {
			if (displayCards) {
				handLabels[i - 1] = new JLabel(GUICard.getIcon(handCard));
			} else
				handLabels[i - 1] = new JLabel(GUICard.getBackCardIcon());
			handCard = myHand.inspectCard(++i);
		}

		return handLabels;
	}

	static JButton[] buttonsList(Hand currentHand, boolean displayCards) {
		JButton[] handButtons = new JButton[currentHand.getNumCards()];

		Card handCard = currentHand.inspectCard(1);
		// Inspect to get the validity, hence the while loop will exit if
		// recieved flag
		int i = 1;

		while (!handCard.getErrorFlag()) {
			if (displayCards) {
				handButtons[i - 1] = new JButton(GUICard.getIcon(handCard));
				handButtons[i - 1].addActionListener(new HumanHandListener());
				handButtons[i - 1].setActionCommand("" + (i - 1) + "");
			} else
				handButtons[i - 1] = new JButton(GUICard.getBackCardIcon());
			handCard = currentHand.inspectCard(++i);
		}

		return handButtons;
	}

	// We assign the listener to ever card we display out, so the human hand
	private static class HumanHandListener implements ActionListener {
		public void actionPerformed(ActionEvent cardClicked) {
			int humanIndex = Integer.valueOf(cardClicked.getActionCommand());

			Card humanCard = highCardGame.getHand(1).playCard(humanIndex); // These
																			// cards
																			// are
																			// in
																			// order
																			// by
																			// value
			Card botCard = highCardGame.getHand(0).playCard(humanIndex);// These
																		// cards
																		// are
																		// in
																		// order
																		// by
																		// value

			// If the bot has a card higher, should we do something?
			display(humanCard, botCard);
			// Do a Refresh
			refresh();
		}

		private Boolean refresh() {
			try {
				myCardTable.repaint();

			} catch (Exception e) {
				return false;
			}
			return true;
		}
	}

}

class CardGameFramework {
	private static final int MAX_PLAYERS = 50;

	private int numPlayers;
	private int numPacks; // # standard 52-card packs per deck
							// ignoring jokers or unused cards
	private int numJokersPerPack; // if 2 per pack & 3 packs per deck, get 6
	private int numUnusedCardsPerPack; // # cards removed from each pack
	private int numCardsPerHand; // # cards to deal each player
	private Deck deck; // holds the initial full deck and gets
						// smaller (usually) during play
	private Hand[] hand; // one Hand for each player
	private Card[] unusedCardsPerPack; // an array holding the cards not used
										// in the game. e.g. pinochle does not
										// use cards 2-8 of any suit

	public CardGameFramework(int numPacks, int numJokersPerPack, int numUnusedCardsPerPack, Card[] unusedCardsPerPack,
			int numPlayers, int numCardsPerHand) {
		int k;

		// filter bad values
		if (numPacks < 1 || numPacks > 6)
			numPacks = 1;
		if (numJokersPerPack < 0 || numJokersPerPack > 4)
			numJokersPerPack = 0;
		if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) // > 1 card
			numUnusedCardsPerPack = 0;
		if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
			numPlayers = 4;
		// one of many ways to assure at least one full deal to all players
		if (numCardsPerHand < 1 || numCardsPerHand > numPacks * (52 - numUnusedCardsPerPack) / numPlayers)
			numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

		// allocate
		this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
		this.hand = new Hand[numPlayers];
		for (k = 0; k < numPlayers; k++)
			this.hand[k] = new Hand();
		deck = new Deck(numPacks);

		// assign to members
		this.numPacks = numPacks;
		this.numJokersPerPack = numJokersPerPack;
		this.numUnusedCardsPerPack = numUnusedCardsPerPack;
		this.numPlayers = numPlayers;
		this.numCardsPerHand = numCardsPerHand;
		for (k = 0; k < numUnusedCardsPerPack; k++)
			this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

		// prepare deck and shuffle
		newGame();
	}

	// constructor overload/default for game like bridge
	public CardGameFramework() {
		this(1, 0, 0, null, 4, 13);
	}

	public Hand getHand(int k) {
		// hands start from 0 like arrays

		// on error return automatic empty hand
		if (k < 0 || k >= numPlayers)
			return new Hand();

		return hand[k];
	}

	public Card getCardFromDeck() {
		return deck.dealCard();
	}

	public int getNumCardsRemainingInDeck() {
		return deck.getNumCards();
	}

	public void newGame() {
		int k, j;

		// clear the hands
		for (k = 0; k < numPlayers; k++)
			hand[k].resetHand();

		// restock the deck
		deck.init(numPacks);

		// remove unused cards
		for (k = 0; k < numUnusedCardsPerPack; k++)
			deck.removeCard(unusedCardsPerPack[k]);

		// add jokers
		for (k = 0; k < numPacks; k++)
			for (j = 0; j < numJokersPerPack; j++)
				deck.addCard(new Card('X', Card.Suit.values()[j]));

		// shuffle the cards
		deck.shuffle();
	}

	public boolean deal() {
		// returns false if not enough cards, but deals what it can
		int k, j;
		boolean enoughCards;

		// clear all hands
		for (j = 0; j < numPlayers; j++)
			hand[j].resetHand();

		enoughCards = true;
		for (k = 0; k < numCardsPerHand && enoughCards; k++) {
			for (j = 0; j < numPlayers; j++)
				if (deck.getNumCards() > 0)
					hand[j].takeCard(deck.dealCard());
				else {
					enoughCards = false;
					break;
				}
		}

		return enoughCards;
	}

	void sortHands() {
		int k;

		for (k = 0; k < numPlayers; k++)
			hand[k].sort();
	}

	Card playCard(int playerIndex, int cardIndex) {
		// returns bad card if either argument is bad
		if (playerIndex < 0 || playerIndex > numPlayers - 1 || cardIndex < 0 || cardIndex > numCardsPerHand - 1) {
			// Creates a card that does not work
			return new Card('M', Card.Suit.spades);
		}

		// return the card played
		return hand[playerIndex].playCard(cardIndex);

	}

	boolean takeCard(int playerIndex) {
		// returns false if either argument is bad
		if (playerIndex < 0 || playerIndex > numPlayers - 1)
			return false;

		// Are there enough Cards?
		if (deck.getNumCards() <= 0)
			return false;

		return hand[playerIndex].takeCard(deck.dealCard());
	}

}