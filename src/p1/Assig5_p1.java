package p1;

import javax.swing.*;
import java.awt.*;


public class Assig5_p1 {

    static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
    static final String imagesDir = "images/";
    static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];

    /**
     * populates the icon field with its image icons
     */
    static void loadCardIcons() {
        int index = 0;

        for (Suit suit : Suit.values()) {
            for (FaceValue value : FaceValue.values()) {
                icon[index] = new ImageIcon(imagesDir + value.toString() + suit.toString() + ".gif");
                index++;
            }
        }

        // add an item for the card back
        icon[index] = new ImageIcon(imagesDir + "BK.gif");
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

    private enum Suit {
        C, // Clubs
        D, // Diamonds
        H, // Hearts
        S; // Spades

        @Override
        public String toString() {
            return this.name();
        }
    }

    private enum FaceValue {
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

        @Override
        public String toString() {
            return this.name().substring(this.name().length() - 1);
        }
    }
}
