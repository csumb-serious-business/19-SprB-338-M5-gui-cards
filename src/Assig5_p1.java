import javax.swing.*;
import java.awt.*;


public class Assig5_p1 {

    static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
    static final int VALUES = 13;
    static final int SUITS = 4;
    static final String imagesDir = "images/";
    static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

    /**
     * populates the icon field with its image icons
     */
    static void loadCardIcons() {
        String value;
        String suit;
        int index = 0;

        // build the filenames ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
        // in a SHORT loop.  For each file name, read it in and use it to
        // instantiate each of the 57 Icons in the icon[] array.
        for (int i = 0; i < SUITS; i++) {//for all suits
            for (int j = 0; j <= VALUES; j++) {
                value = turnIntIntoCardValue(j);
                suit = turnIntIntoCardSuit(i);
                icon[index] = new ImageIcon(imagesDir + value + suit + ".gif");
                index++;
            }
        }

        // add an item for the card back
        icon[index] = new ImageIcon(imagesDir + "BK.gif");
    }

    /**
     * Returns a card value given an int
     * turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
     *
     * @param value the int to check
     * @return cardValue if value is valid or `false` in case of error message
     */
    static String turnIntIntoCardValue(int value) {
        if (value >= 0 && value < FaceValues.values().length) {
            // Since face values are numeric, trim leading _ from name
            String valueName = FaceValues.values()[value].name();
            return valueName.substring(valueName.length() - 1);
        }
        return "�"; // Invalid face value
    }

    /**
     * Returns a card suit given an int
     * turns 0 - 3 into "C", "D", "H", "S"
     *
     * @param value the int to convert
     * @return cardSuit if value is valid or `false` in case of error message
     */
    static String turnIntIntoCardSuit(int value) {
        if (value >= 0 && value < Suits.values().length) {
            return Suits.values()[value].name();
        }
        return "�"; // Invalid suit
    }

    // a simple main to throw all the JLabels out there for the world to see
    public static void main(String[] args) {
        // prepare the image icon array
        loadCardIcons();

        // establish main frame in which program will run
        JFrame frmMyWindow = new JFrame("Card Room");
        frmMyWindow.setSize(1150, 650);
        frmMyWindow.setLocationRelativeTo(null);
        frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up layout which will control placement of buttons, etc.
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
        frmMyWindow.setLayout(layout);

        // prepare the image label array
        JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
        for (int k = 0; k < NUM_CARD_IMAGES; k++)
            labels[k] = new JLabel(icon[k]);

        // place your 3 controls into frame
        for (int k = 0; k < NUM_CARD_IMAGES; k++)
            frmMyWindow.add(labels[k]);

        // show everything to the user
        frmMyWindow.setVisible(true);
    }

    private enum Suits {
        C, // Clubs
        D, // Diamonds
        H, // Hearts
        S  // Spades
    }

    private enum FaceValues {
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
        X  // Joker
    }
}
