import javax.swing.*;
import java.awt.*;

public class Assig5_p1_ex {
   // static for the 57 icons and their corresponding labels
   static final int NUM_CARD_IMAGES = 57; //52 + 4 jokers+1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   static final int VALUE=13;
   static final int SUIT=4;

   static void loadCardIcons() {
      /**
       * Instantiates all cards and one card back into icon[index] array
       * for all suits and values
       */
      String value;
      String suit;
      int index=0;
      
      for(int i=0;i<SUIT;i++) {//for all suits
         for(int j=0;j<=VALUE;j++) {//for all values
            value=   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
            turnIntIntoCardValue(j);
            suit=turnIntIntoCardSuit(i);
            //instantiate 57 icons in icon[] array
            icon[index]=new ImageIcon("images/"+value+suit+".gif");
            index++;
         }
      }
      icon[index]=new ImageIcon("images/BK.gif");//add a back to the icon[index]
      index++;
   }

   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k) {
      /**
       * Returns a card value given an int
       *
       * @param k the int to check
       * @return cardValue if k is valid or `false` in case of error message
       */
      String cardValue;
      String[] imageCardValue= {"A","2","3","4","5","6","7","8","9","T"
              ,"J","Q","K","X"};
      if(k>=0 && k<=VALUE) {//assign imageCardValue[k] to cardValue
         cardValue=imageCardValue[k];
      }
      else {
         return "Invalid Card in turnIntIntoCardValue function";
      }
      return cardValue;
   }

   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j) {
      /**
       * Returns a card suit given an int
       *
       * @param j the int to check
       * @return cardSuit if j is valid or `false` in case of error message
       */
      String cardSuit;
      String[] cardSuitImage={"C","D","H","S"};
      if(j>=0 && j<=SUIT) {//assign cardSuitImage[j] to cardSuit
         cardSuit=cardSuitImage[j];
      }
      else {
         return "Card Suit is invalid in turnIntIntoCardSuit";
      }
      return cardSuit;
   }

   public static void main(String[] args) {
      int k;

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
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);

      // place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // show everything to the user
      frmMyWindow.setVisible(true);

   }
}
