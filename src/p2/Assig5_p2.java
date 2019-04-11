package p2;

import javax.swing.*;
import java.awt.*;
import java.util.StringJoiner;

public class Assig5_p2 {
    private static final Deck randDeck = new Deck();
    static int NUM_CARDS_PER_HAND = 7; ////
    static int NUM_PLAYERS = 2;  ////
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND]; ////
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND]; ////
    static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS]; ////
    static JLabel[] playLabelText = new JLabel[NUM_PLAYERS]; ////

    ///
    static Card generateRandomCard() {
        int randCard = (int) (Math.random() * 56);
        return randDeck.inspectCard(randCard);
    }

    public static void main(String[] args) {

        // establish main frame in which program will run
        CardTable myCardTable = new CardTable("CardTable",
                NUM_CARDS_PER_HAND, NUM_PLAYERS);
        myCardTable.setSize(800, 600);
        myCardTable.setLocationRelativeTo(null);
        myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // show everything to the user
        myCardTable.setVisible(true);

        // --- CREATE LABELS --------------------------------------------------
        for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
            computerLabels[i] = new JLabel(GUICard.getBackCardIcon());
        }

        for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
            Card card = generateRandomCard();
            humanLabels[i] = new JLabel(GUICard.getIcon(card));
        }

        for (int i = 0; i < NUM_PLAYERS; i++) {
            Card card = generateRandomCard();
            playedCardLabels[i] = new JLabel(GUICard.getIcon(card));
        }

        // and two random cards in the play region here
        // (simulating a computer/hum play)
        playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
        playLabelText[1] = new JLabel("You", JLabel.CENTER);

        // --- ADD LABELS TO PANELS -------------------------------------------
        for (JLabel j : computerLabels) {
            myCardTable.pnlComputerHand.add(j);
        }

        for (JLabel j : humanLabels) {
            myCardTable.pnlHumanHand.add(j);
        }

        for (JLabel j : playedCardLabels) {
            myCardTable.pnlPlayArea.add(j);
        }

        for (JLabel j : playLabelText) {
            myCardTable.pnlPlayArea.add(j);
        }


        // show everything to the user
        myCardTable.setVisible(true);
    }
}

class CardTable extends JFrame {
    static int MAX_CARDS_PER_HAND = 56; ///
    static int MAX_PLAYERS = 2; ///
    public JPanel pnlComputerHand; ///
    public JPanel pnlHumanHand; ///
    public JPanel pnlPlayArea; ///
    private int numCardsPerHand; ///
    private int numPlayers; ///

    /**
     * Arranges panels for the card table
     *
     * @param title           the name of the game played on this table
     * @param numCardsPerHand the max number of per player hand
     * @param numPlayers      the number of players for this game
     */
    ///
    public CardTable(String title, int numCardsPerHand, int numPlayers) {
        super();

        if (numPlayers > MAX_PLAYERS) {
            numPlayers = MAX_PLAYERS;
        }

        if (numCardsPerHand > MAX_CARDS_PER_HAND) {
            numCardsPerHand = MAX_CARDS_PER_HAND;
        }

        this.numPlayers = numPlayers;
        this.numCardsPerHand = numCardsPerHand;

        setSize(1150, 650);
        setTitle(title);

        setLayout(new BorderLayout());

        pnlComputerHand = new JPanel();
        pnlComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
        add(pnlComputerHand, BorderLayout.NORTH);

        pnlPlayArea = new JPanel();
        pnlPlayArea.setLayout(new GridLayout(2, numPlayers));
        add(pnlPlayArea, BorderLayout.CENTER);

        pnlHumanHand = new JPanel();
        pnlHumanHand.setLayout(new GridLayout(1, numCardsPerHand));
        add(pnlHumanHand, BorderLayout.SOUTH);
    }

    ///
    public int getNumPlayers() {
        return numPlayers;
    }

    ///
    public int getNumCardsPerHand() {
        return numCardsPerHand;
    }

}

class GUICard {
    static final String imagesDir = "images/";
    static boolean iconsLoaded = false; ///
    private static Icon[][] iconCards = new ImageIcon[14][4]; /// 14 = A through K plus joker
    private static Icon iconBack; ///

    ///
    static void loadCardIcons() {
        if (iconsLoaded) {
            return;
        }
        for (Card.FaceValue value : Card.FaceValue.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                int vID = value.ordinal();
                int sID = suit.ordinal();

                iconCards[vID][sID] = new ImageIcon(
                        imagesDir + value.toString() + suit.toString() + ".gif");
            }
        }
        iconBack = new ImageIcon(imagesDir + "BK.gif");//set the icon back
        iconsLoaded = true;
    }


    ///
    static public Icon getIcon(Card card) {
        loadCardIcons();
        return iconCards[Card.valueAsInt(card)][Card.suitAsInt(card)];
    }

    ///
    static public Icon getBackCardIcon() {
        loadCardIcons();
        return iconBack;
    }


}

/**
 * Represents a single playing card with a suit and value
 */
class Card {
    /// superseded by FaceValue enum
    public static char[] valuRanks = {
            'A',
            '2', '3', '4', '5', '6', '7', '8', '9',
            'T',
            'J', // Jack
            'Q', // Queen
            'K', // King
            'X'  // Joker
    };

    private FaceValue value;
    private Suit suit;
    private boolean errorFlag;


    /**
     * Create a default card, an Ace of Spades
     */
    public Card() {
        this(FaceValue.A, Suit.spades);
    }

    /**
     * Create a card with a given value and suit
     *
     * @param value the card's value
     * @param suit  the card's suit
     */
    public Card(FaceValue value, Suit suit) {
        set(value, suit);

    }

    /**
     * Evaluates whether a given value/suit combination results in a valid card
     *
     * @param value the value to check
     * @param suit  the suit to check
     * @return true if the combination of value/suit is valid for a card
     */
    private static boolean isValid(char value, Suit suit) {
        try {
            FaceValue.valueOf(value);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return suit != null;
    }

    private static boolean isValid(FaceValue value, Suit suit) {
        return value != null && suit != null;
    }


    /**
     * Returns an int given a card value
     * turns "A", "2", "3", ... "Q", "K", "X" into 0 - 13
     *
     * @param card the card to check
     * @return cardValue
     */
    static int valueAsInt(Card card) {
        return card.value.ordinal();
    }

    ///
    static void arraySort(Card[] cards, int arraySize) {
        for (int i = 0; i < arraySize - 1; i++) {
            for (int j = 0; j < arraySize - i - 1; j++) {
                Card card1 = cards[j];
                Card card2 = cards[j + 1];
                if (card1.getValue() == card2.getValue()) {
                    if (suitAsInt(card1) > suitAsInt(card2)) {
                        cards[j + 1] = card1;
                        cards[j] = card2;
                    }
                }

                for (FaceValue c : FaceValue.values()) {
                    if (c == card1.getValue()) {
                        break;
                    }
                    if (c == card2.getValue()) {
                        cards[j + 1] = card1;
                        cards[j] = card2;
                    }
                }
            }
        }
    }

    /**
     * Returns a int given a card suit
     *
     * @param card the card with a suit to check
     * @return cardSuit if j is valid or `false` in case of error message
     */
    static int suitAsInt(Card card) {
        return card.suit.ordinal();
    }

    /**
     * Assigns new values for this card's value and suit
     *
     * @param value the new value for this card
     * @param suit  the new suit for this card
     * @return true if the set operation was successful
     */
    public boolean set(FaceValue value, Suit suit) {
        if (isValid(value, suit)) {
            this.value = value;
            this.suit = suit;
            this.errorFlag = false;
        } else {
            this.errorFlag = true;

        }
        return this.errorFlag;
    }

    /**
     * @return the Card's suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * @return the Card's errorFlag
     */
    public boolean getErrorFlag() {
        return errorFlag;
    }

    /**
     * @return the Card's value
     */
    public FaceValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card) obj;
            return this.equals(other);
        }
        return false;
    }

    /**
     * Checks whether this card is equivalent to another card (has the same suit and value)
     *
     * @param card the other card to evaluate against
     * @return true if the two cards are equivalent
     */
    public boolean equals(Card card) {
        // errorFlag (invalid) cards can be checked also (not sure if that is OK)
        // they are technically evaluable, but not useful for the app
        return card.getSuit() == this.getSuit() &&
                card.getValue() == this.getValue() &&
                card.getErrorFlag() == this.getErrorFlag();
    }

    @Override
    public String toString() {
        if (this.getErrorFlag()) {
            return "\uFFFD\uFFFD"; // �� -- invalid card
        }

        return "" + this.getValue() + this.getSuit().toUnicode();
    }

    enum Suit {
        clubs, diamonds, hearts, spades;

        @Override
        public String toString() {
            return this.name().substring(0, 1).toUpperCase();
        }

        /**
         * @return the corresponding Unicode character for a given suit
         */
        public char toUnicode() {
            switch (this) {
                case clubs:
                    return '\u2663'; // ♣
                case diamonds:
                    return '\u2666'; // ♦
                case hearts:
                    return '\u2665'; // ♥
                case spades:
                    return '\u2660'; // ♠
                default:
                    return '\uFFFD'; // � -- should never happen
            }
        }
    }

    enum FaceValue {
        A,  // Ace
        _2, // Numeric
        _3,
        _4,
        _5,
        _6,
        _7,
        _8,
        _9,
        T, // 10
        J, // Jack
        Q, // Queen
        K, // King
        X; // Joker

        /**
         * todo Overload
         *
         * @param c
         * @return
         * @throws IllegalArgumentException
         */
        public static FaceValue valueOf(char c) throws IllegalArgumentException {
            if (Character.isDigit(c)) {
                return FaceValue.valueOf("_" + c);
            }
            return FaceValue.valueOf("" + c);
        }

        @Override
        public String toString() {
            return this.name().substring(this.name().length() - 1);
        }

    }
}

/**
 * Represents a hand of playing cards held by a single player
 * It can hold several cards
 */
class Hand {
    public static int MAX_CARDS = 50; // no 'monster arrays'
    private Card[] myCards; //---------/ also called myArray in assignment desc
    private int numCards; //-----------/ count of cards

    /**
     * Creates an empty hand
     */
    public Hand() {
        this.myCards = new Card[MAX_CARDS];
        this.numCards = 0;
    }

    /**
     * Adds a card to the hand, usually from another play area, like a deck.
     *
     * @param card the card to add
     * @return true if card successfully taken
     */
    public boolean takeCard(Card card) {
        //todo must use char?
        if (numCards < MAX_CARDS) {
            Card takenCard = new Card(card.getValue(), card.getSuit());
            myCards[numCards++] = takenCard; //copies card to myCards
            return true;//return true if success
        } else {
            return false;
        }
    }

    /**
     * Resets this hand to its initial (empty) state
     */
    public void resetHand() {
        numCards = 0;
        myCards = new Card[MAX_CARDS];
    }

    //   numCards accessor

    /**
     * @return the number of cards currently in this hand
     */
    public int getNumCards() {
        return numCards;
    }

    /**
     * Fetches the card in a given position in the hand without removing it.
     *
     * @param k the position to fetch the card from
     * @return the card from the given position in the hand
     * -OR- an invalid card if that position is invalid or unpopulated
     */
    Card inspectCard(int k) {
        //if(index is less than the accessible and greater than the index)
        if (0 <= k && k <= numCards) {
            return new Card(myCards[k].getValue(), myCards[k].getSuit());
        }

        return new Card(null, Card.Suit.spades); //creates a card that will not work so error flag returns true
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (numCards <= 0) {
            return joiner.add("empty").toString();
        }
        for (int i = 0; i < numCards; i++) {
            joiner.add(myCards[i].toString());
        }
        return joiner.toString();

    }

    public Card playCard(int cardIndex) {
        if (numCards == 0) //error
        {
            //Creates a card that does not work
            return new Card(null, Card.Suit.spades);
        }
        //Decreases numCards.
        Card card = myCards[cardIndex];

        numCards--;
        for (int i = cardIndex; i < numCards; i++) {
            myCards[i] = myCards[i + 1];
        }

        myCards[numCards] = null;

        return card;
    }

    ///
    void sort() {
        Card.arraySort(myCards, numCards);
    }
}

/**
 * Represents the source of playing cards in a game
 */
class Deck {
    private static final int MAX_PACKS = 6;
    private static final int CARDS_PER_PACK = 56;
    public static final int MAX_CARDS = MAX_PACKS * CARDS_PER_PACK;
    private static Card[] masterpack;

    private Card[] cards;
    private int numPacks;
    private int topCard;

    /**
     * Creates a new deck using a given number of packs
     *
     * @param numPacks the number of packs within this deck
     */
    public Deck(int numPacks) {
        this.init(numPacks);
    }

    /**
     * Creates a new deck with a single pack
     */
    public Deck() {
        this.init();
    }

    /**
     * Populates the reusable master pack for decks
     * only if it is empty.
     */
    private static void allocateMasterPack() {
        if (Deck.masterpack != null) {
            return;
        }
        Deck.masterpack = new Card[CARDS_PER_PACK];
        int c = 0;

        for (Card.FaceValue value : Card.FaceValue.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                masterpack[c] = new Card(value, suit);
                c++;
            }
        }
    }

    /**
     * Refreshes this deck, discarding all current cards (if any)
     * and populating it with fresh packs.
     *
     * @param numPacks the number of packs to refresh with
     */
    public void init(int numPacks) {
        // init master pack if not yet populated
        allocateMasterPack();

        // enforce pack limit
        if (numPacks > MAX_PACKS) {
            numPacks = MAX_PACKS;
            System.out.printf("Maximum number of packs exceeded, set to maximum: %d%n", numPacks);
        }

        this.numPacks = numPacks;

        int numCards = numPacks * CARDS_PER_PACK;
        this.cards = new Card[numCards];


        // for the desired number of packs, copy the master pack into packs
        for (int i = 0; i < numPacks; i++) {
            System.arraycopy(Deck.masterpack, 0,
                    this.cards, i * CARDS_PER_PACK,
                    Deck.masterpack.length);
        }

        // set the position of the top card
        this.topCard = numCards - 1; // zero-indexed
    }

    /**
     * Refreshes this deck, discarding all current cards (if any)
     * and populating it with a fresh pack.
     */
    public void init() { //reinitializes an existing Deck object with one pack
        this.init(1);
    }

    /**
     * Removes the top card of the deck and returns it
     *
     * @return the top card from the deck
     */
    public Card dealCard() { //returns the top card of the deck and removes it
        Card dealtCard = cards[topCard];
        cards[topCard] = null;
        topCard--;
        return dealtCard;
    }

    /**
     * Fetches the top card from this deck without removing it
     *
     * @return the top card in this deck
     */
    public int getTopCard() { //returns the topCard integer
        return this.topCard;
    }

    /**
     * Fetches the card at a given position within the deck
     * -OR- an invalid card if that position is not populated
     * or the position is otherwise invalid
     * <p>
     * does not remove the card from the deck
     *
     * @param k the position of the card in the deck to inspect
     * @return the card at the given position, or and invalid card if not found
     */
    public Card inspectCard(int k) { //takes an integer and accesses the deck at that index and returns a card object
        if (k >= 0 && k <= topCard) {
            return cards[k];
        } else return new Card(null, Card.Suit.diamonds); //returns a card with errorFlag if index is out of range
    }

    /**
     * Exchanges the card in one position with the card in another position
     *
     * @param cardA the position of a card to swap
     * @param cardB the position of another card to swap
     */
    private void swap(int cardA, int cardB) { //helper function for shuffle, takes two ints and swaps the cards at those indexes
        if (cardA == cardB) {
            return;
        }

        Card tempCard = cards[cardA];
        cards[cardA] = cards[cardB];
        cards[cardB] = tempCard;
    }

    /**
     * Randomizes the order of the cards within this deck
     */
    public void shuffle() { //
        int numCards = this.topCard + 1;
        int shuffleSteps = numCards * 25;
        for (int i = 0; i < shuffleSteps; i++) {
            int cardA = (int) (Math.random() * numCards);
            int cardB = (int) (Math.random() * numCards);
            swap(cardA, cardB);
        }
    }

    ///
    int getNumCards() {
        return cards.length;
    }

    ///
    boolean addCard(Card card) {
        cards[topCard + 1] = card;
        topCard++;
        return cards[topCard] == card;
    }

    ///
    boolean removeCard(Card card) {
        boolean success = false;
        for (Card c : cards) {
            if (c == card) {
                c = cards[topCard];
                cards[topCard] = null;
                topCard--;
                success = true;
            }
        }
        return success;
    }

    ///
    void sort() {
        Card.arraySort(cards, topCard);
    }
}