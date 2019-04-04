import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Assig5_p2_ex {
    static int NUM_CARDS_PER_HAND = 7;
    static int NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
    static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

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

        // ADD LABELS TO PANELS here

        // and two random cards in the play region here
        // (simulating a computer/hum ply)

        // show everything to the user
        myCardTable.setVisible(true);
    }


}

class CardTable {
    CardTable(String s, int a, int b) {
    }

    void setSize(int a, int b) {
    }

    void setLocationRelativeTo(Object _) {
    }

    void setDefaultCloseOperation(int _) {
    }

    void setVisible(boolean _) {
    }
}
