import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.StringJoiner;

public class Assig5_p2_ex {
    static int NUM_CARDS_PER_HAND = 7;
    static int NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
    static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

    static Card generateRandomCard(){
        Deck deck = new Deck();
        int randCard = (int)(Math.random() * 56);
        return deck.inspectCard(randCard);
    }

    public static void main(String[] args) {
        int k;
        Icon tempIcon;

        // establish main frame in which program will run
        CardTable myCardTable = new CardTable("CardTable",
                NUM_CARDS_PER_HAND, NUM_PLAYERS);
        myCardTable.setSize(800, 600);
        myCardTable.setLocationRelativeTo(null);
        myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // show everything to the user
        myCardTable.setVisible(true);

        // CREATE LABELS here
        for(int i = 0; i < NUM_CARDS_PER_HAND; i++){
            JLabel j = new JLabel(GUICard.getBackCardIcon());
            computerLabels[i] = j;
        }

        for(int i = 0; i < NUM_CARDS_PER_HAND; i++){
            Card card = generateRandomCard();
            JLabel j = new JLabel(GUICard.getIcon(card));
            humanLabels[i] = j;
        }

        for(JLabel j : playedCardLabels){
            Card card = generateRandomCard();
            j = new JLabel(GUICard.getIcon(card));
        }

        playedCardLabels[0] = new JLabel(GUICard.getIcon(generateRandomCard()));
        playedCardLabels[1] = new JLabel(GUICard.getIcon(generateRandomCard()));
        playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
        playLabelText[1] = new JLabel("You", JLabel.CENTER);

        // ADD LABELS TO PANELS here
        for(JLabel j : computerLabels) {
            myCardTable.pnlComputerHand.add(j);
        }

        for(JLabel j : humanLabels){
            myCardTable.pnlHumanHand.add(j);
        }

        for(JLabel j : playedCardLabels){
            myCardTable.pnlPlayArea.add(j);
        }

        for(JLabel j : playLabelText){
            myCardTable.pnlPlayArea.add(j);
        }

        // and two random cards in the play region here
        // (simulating a computer/hum ply)

        // show everything to the user
        myCardTable.setVisible(true);
    }


}

class CardTable extends JFrame {
    static int MAX_CARDS_PER_HAND = 56;
    static int MAX_PLAYERS = 2;
    private int numCardsPerHand;
    private int numPlayers;
    public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

    public CardTable(String title, int numCardsPerHand, int numPlayers) { //TODO: constructor
        super();

        if(numPlayers > MAX_PLAYERS) this.numPlayers = 2;
        else this.numPlayers = numPlayers;
        if(numCardsPerHand > MAX_CARDS_PER_HAND) this.numCardsPerHand = MAX_CARDS_PER_HAND;
        else this.numCardsPerHand = numCardsPerHand;

        setSize(1150, 650);
        setTitle(title);

        this.setLayout(new BorderLayout());
        pnlComputerHand = new JPanel();
        add(pnlComputerHand, BorderLayout.NORTH);
        pnlPlayArea = new JPanel();
        add(pnlPlayArea, BorderLayout.CENTER);
        pnlHumanHand = new JPanel();
        add(pnlHumanHand, BorderLayout.SOUTH);

        pnlComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
        pnlPlayArea.setLayout(new GridLayout(2, numPlayers));
        pnlHumanHand.setLayout(new GridLayout(1, numCardsPerHand));
        
    }

    public int getNumPlayers(){
        return numPlayers;
    }

    public int getNumCardsPerHand(){
        return numCardsPerHand;
    }

}

class GUICard {
    private static Icon[][] iconCards = new ImageIcon[14][4]; //14 = A through K plus joker
    private static Icon iconBack;
    static boolean iconsLoaded = false;

    static void loadCardIcons() {
        if(iconsLoaded) return;

        String value;
        String suit;

        for(int i=0; i< Assig5_p1_ex.SUIT; i++) {//for all suits
            for(int j=0; j<= Assig5_p1_ex.VALUE; j++) {//for all values
                value = Assig5_p1_ex.turnIntIntoCardValue(j);
                suit = Assig5_p1_ex.turnIntIntoCardSuit(i);
                iconCards[j][i]=new ImageIcon("images/"+value+suit+".gif");
            }
        }
        iconBack = new ImageIcon("images/BK.gif");//set the icon back
        iconsLoaded = true;
    }


    // turns "A", "2", "3", ... "Q", "K", "X" into 0 - 13
    static int valueAsInt(Card card) {
        /**
         * Returns an int given a card value
         *
         * @param value the value to check
         * @return cardValue
         */
        int cardValue = 0;
        char[] imageCardValue= {'A','2','3','4','5','6','7','8','9','T'
                ,'J','Q','K','X'};
        for(char v : imageCardValue){
            if(card.getValue() == v) return cardValue;
            cardValue++;
        }

        return cardValue;
    }

    static public Icon getIcon(Card card){
        if(iconsLoaded == false) loadCardIcons();
        return iconCards[valueAsInt(card)][suitAsInt(card)];
    }

    static public Icon getBackCardIcon() {
        if(iconsLoaded == false) loadCardIcons();
        return iconBack;
    }

    // turns 0 - 3 into "C", "D", "H", "S"
    static int suitAsInt(Card card) {
        /**
         * Returns a int given a card suit
         *
         * @param suit the suit to check
         * @return cardSuit if j is valid or `false` in case of error message
         */
        int cardSuit = 0;
        Card.Suit[] cardSuitImage={Card.Suit.clubs, Card.Suit.diamonds, Card.Suit.hearts, Card.Suit.spades};
        for(Card.Suit s : cardSuitImage){
            if(card.getSuit() == s) return cardSuit;
            cardSuit++;
        }

        return cardSuit;
    }

}

/**
 * Represents a single playing card with a suit and value
 */
class Card {
    public static final String VALID_VALUE_CHARS = "A23456789TJQKX";
    public static char[] valuRanks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
    private char value;
    private Suit suit;
    private boolean errorFlag;
    enum Suit {clubs, diamonds, hearts, spades};


    /**
     * Create a default card, an Ace of Spades
     */
    public Card() {
        this('A', Suit.spades);
    }

    /**
     * Create a card with a given value and suit
     *
     * @param value the card's value
     * @param suit  the card's suit
     */
    public Card(char value, Suit suit) {
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
        value = Character.toUpperCase(value);
        // value in valid chars AND suit is not null
        return (VALID_VALUE_CHARS.indexOf(value) != -1 && suit != null);
    }

    /**
     * Assigns new values for this card's value and suit
     *
     * @param value the new value for this card
     * @param suit  the new suit for this card
     * @return true if the set operation was successful
     */
    public boolean set(char value, Suit suit) {
        if (isValid(value, suit)) {
            this.suit = suit;
            this.value = value;
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
    public char getValue() {
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

        return "" + this.getValue() + this.suitToUnicode();
    }

    /**
     * @return the corresponding Unicode character for a given suit
     */
    private char suitToUnicode() {
        // note no breaks between case statements
        // since values are returned directly
        switch (this.getSuit()) {
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

    static void arraySort(Card[] cards, int arraySize){
        for(int i = 0; i < arraySize - 1; i++){
            for(int j = 0; j < arraySize-i-1; j++){
                Card card1 = cards[j];
                Card card2 = cards[j+1];
                if(card1.getValue() == card2.getValue()){
                    if(GUICard.suitAsInt(card1) > GUICard.suitAsInt(card2)){
                        cards[j+1] = card1;
                        cards[j] = card2;
                    }
                }

                for(char c : valuRanks){
                    if(c == card1.getValue()) break;
                    if(c == card2.getValue()){
                        cards[j+1] = card1;
                        cards[j] = card2;
                    }
                }
            }
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
        if (numCards < MAX_CARDS) {
            char valueChar = card.getValue();
            Card.Suit suitVal = card.getSuit();
            Card takenCard = new Card(valueChar, suitVal);
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
    	  Card cardReturn;
    	  //if(index is less than the accessable and greater than the index)
    	  Card card1;
          if (k <= numCards && k >= 0)
          {
        	  card1 = new Card(myCards[k - 1].getValue(), myCards[k - 1].getSuit());
          }

          else
          {
            
             card1 = new Card('Z', Card.Suit.spades); //creates a card that will not work so error flag returns true
          }
          return card1;
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

    public Card playCard(int cardIndex)
    {
        if ( numCards == 0 ) //error
        {
            //Creates a card that does not work
            return new Card('M', Card.Suit.spades);
        }
        //Decreases numCards.
        Card card = myCards[cardIndex];

        numCards--;
        for(int i = cardIndex; i < numCards; i++)
        {
            myCards[i] = myCards[i+1];
        }

        myCards[numCards] = null;

        return card;
    }

    void sort(){
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

        for (char value : Card.VALID_VALUE_CHARS.toCharArray()) {
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
        } else return new Card('X', Card.Suit.diamonds); //returns a card with errorFlag if index is out of range
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

    int getNumCards(){
        return cards.length;
    }

    boolean addCard(Card card){
        cards[topCard +1] = card;
        topCard++;
        if(cards[topCard] == card) return true;
        else return false;
    }

    boolean removeCard(Card card){
        boolean success = false;
        for(Card c : cards){
            if(c == card){
                c = cards[topCard];
                cards[topCard] = null;
                topCard--;
                success = true;
            }
        }
        return success;
    }

    void sort(){
        Card.arraySort(cards, topCard);
    }
}