/*
 * Project Name: CS2365 Project 2 - UNO Deck Workout
 * October 2020
 * Description: This project creates a deck comprised of UNO cards and displays
 *              it in sets 7-card hands that get dealt one at a time until the
 *              deck is empty. The user is allowed to decide how many decks
 *              (between 1 and 3)are used, if the special cards should be
 *              allowed or not, and it each deck should be shuffled separate or
 *              together. Once the deck has been created and shuffled, hands of
 *              7 cards are draw. They are then organized by color and number,
 *              and the action cards are applied. After all this, the contents
 *              of the hand and the number of reps per workout are saved to an
 *              HTML file. This process is repeated until the deck is empty.
 *              Once the deck is empty, the total reps per workout are saved to
 *              the file.
 */
package unodeckproject_updated;
import Deck.Card;
import Deck.Deck;
import Deck.Hand;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author David Hoefs
 * @author Javier Vasquez
 */
public class UnoDeckProject_Updated {

    /**
     * Main method gets user input, creates and shuffles deck, and creates the
     * HTML file.
     * 
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter a file name/path for your output (include extension)");
        String fName = reader.readLine();
        createHTML(fName);
        
        //Get the number of decks to use
        int decks = 0;
        while(decks < 1 || decks > 3) {
            System.out.println("How many decks do you want to use? [1-3] ");
            String d = reader.readLine();
            decks = Integer.parseInt(d);
        }
        
        //Shuffle decks together or separate?
        String together = "";
        if(decks > 1) {
            while(!"n".equals(together) && !"y".equals(together)) {
                System.out.println("Do you want to shuffle the decks together? [Y/N] ");
                together = reader.readLine().toLowerCase();
            }
        } else
            together = "y";
        
        //Include action cards?
        String include_specials = "";
        int specials;
        while(!"n".equals(include_specials) && !"y".equals(include_specials)) {
            System.out.println("Do you want to include action cards? [Y/N] ");
            include_specials = reader.readLine().toLowerCase();
        }
        if ("y".equals(include_specials))
            specials = 32;
        else
            specials = 0;
        
        //Create main deck
        Deck mainDeck;
        
        //Populate main deck after shuffling cards
        if ("y".equals(together))
            mainDeck = shuffleTogether(decks, specials, include_specials);
        else
            mainDeck = shuffleSeparate(decks, specials, include_specials);
        
        // get user input to assign workout to card color 
        System.out.println("Enter workout for Blue Card: ");
        String blueWork = reader.readLine();
        System.out.println("Enter workout for Red Card: ");
        String redWork = reader.readLine();
        System.out.println("Enter workout for Green Card: ");
        String greenWork = reader.readLine();
        System.out.println("Enter workout for Yellow Card: ");
        String yellowWork = reader.readLine();
        
        int bCount = 0, rCount = 0, gCount = 0, yCount = 0, burpCount = 0;
        int bSkip = 0, rSkip = 0, gSkip = 0, ySkip = 0;
        
        //create writer for HTML file
        FileWriter writer = new FileWriter(fName);
        writer.write("<!DOCTYPE html>\n<html>\n<body>\n");
        int round = 1;
        
        
        //main loop for emptying the deck
        while(mainDeck.index > 0) {
            //System.out.println("Round " + round);
            writer.write("<p>Round " + round + "</p>\n");
            
            // draw 7 cards for player hand
            Card[] newHand;
            if(mainDeck.index > 6)
                newHand = new Card[7];
            else
                newHand = new Card[mainDeck.index];
            for(int i =0;i<newHand.length;i++){
                newHand[i] = mainDeck.drawCard();
            }
            // create hand
            Hand stats = new Hand(newHand);
            //System.out.println("PLAYER HAND BEFORE SORTING AND APPLYING ACTIONS: ");
            writer.write("<p>PLAYER HAND BEFORE SORTING AND APPLYING ACTIONS:</p>\n");
            for(int i = 0; i<stats.playerHand.length; i++){
                if(stats.playerHand[i].number == 10){
                    //System.out.println(stats.playerHand[i].color + " " + stats.playerHand[i].special);
                    writer.write("<p style=\"color:" + stats.playerHand[i].color + ";\">" + stats.playerHand[i].color + " " + stats.playerHand[i].special + "</p>\n");
                }
                else{
                    //System.out.println(stats.playerHand[i].color + " " + stats.playerHand[i].number);
                    writer.write("<p style=\"color:" + stats.playerHand[i].color + ";\">" + stats.playerHand[i].color + " " + stats.playerHand[i].number + "</p>\n");
                }
            }
            
            // sort hand, get color count,assign workout to color 
            stats.sortHand(mainDeck);
            // show the sorted player hand
            //System.out.println("\nPLAYER HAND AFTER SORTING AND APPLYING ACTIONS: ");
            writer.write("\n<p>PLAYER HAND AFTER SORTING AND APPLYING ACTIONS:</p>\n");
            for(int i = 0; i<stats.playerHand.length; i++){
                if(stats.playerHand[i].number == 10){
                    //System.out.println("Color: " + stats.playerHand[i].color.toUpperCase() + " Special: " + stats.playerHand[i].special.toUpperCase());
                    writer.write("<p style=\"color:" + stats.playerHand[i].color + ";\">Color: " + stats.playerHand[i].color.toUpperCase() + " Special: " + stats.playerHand[i].special.toUpperCase() + "</p>\n");
                }else{
                    //System.out.println("Color: " + stats.playerHand[i].color.toUpperCase() + " Number: " + stats.playerHand[i].number);
                    writer.write("<p style=\"color:" + stats.playerHand[i].color + ";\">Color: " + stats.playerHand[i].color.toUpperCase() + " Number: " + stats.playerHand[i].number + "</p>\n");
                }
            }
            
            //stats.reverseAction("blue",mainDeck);
            String workout = stats.configureWorkout(blueWork, greenWork, redWork, yellowWork);
            //System.out.println(workout);
            writer.write("<p>" + workout + "</p>\n");
            
            bCount = bCount + stats.blueCount;
            rCount = rCount + stats.redCount;
            gCount = gCount + stats.greenCount;
            yCount = yCount + stats.yellowCount;
            burpCount = burpCount + stats.burpeeCount;
            /*
            System.out.println("TOTAL WORKOUT SO FAR:\n" + 
                    blueWork + ": " + bCount + "\n" +
                    redWork + ": " + rCount + "\n" +
                    greenWork + ": " + gCount + "\n" +
                    yellowWork + ": " + yCount + "\n" +
                    "Burpees: " + burpCount);
            */
            writer.write("<p>TOTAL WORKOUT SO FAR:\n" + 
                    blueWork + ": " + bCount + " | " +
                    redWork + ": " + rCount + " | " +
                    greenWork + ": " + gCount + " | " +
                    yellowWork + ": " + yCount + " | " +
                    "Burpees: " + burpCount + "</p>\n");
            
            bSkip = bSkip + stats.blueSkip;
            rSkip = rSkip + stats.redSkip;
            gSkip = gSkip + stats.greenSkip;
            ySkip = ySkip + stats.yellowSkip;
            /*
            System.out.println("TOTAL WORKOUT SKIPPED:\n" + 
                    blueWork + ": " + stats.blueSkip + " | " + 
                    redWork + ": " + stats.redSkip + " | " +
                    greenWork + ": " + stats.greenSkip + " | " +
                    yellowWork + ": " + stats.yellowSkip);
            */
            writer.write("<p>TOTAL WORKOUT SKIPPED:\n" + 
                    blueWork + ": " + bSkip + " | " + 
                    redWork + ": " + rSkip + " | " +
                    greenWork + ": " + gSkip + " | " +
                    yellowWork + ": " + ySkip + "</p>\n");
            
           // System.out.println("Cards Remaining in Deck: " + mainDeck.index);
            writer.write("<p>Cards Remaining in Deck: " + mainDeck.index + "</p>\n");
            //System.out.println("--------------------------------------------");
            writer.write("<p>--------------------------------------------</p>\n");
            round++;
        }
        writer.write("</body>\n</html>");
        writer.close();
    }
    
    /**
     * Public method creates an HTML file.
     * 
     * @param fname file name/path for the HTML file.
     */
    public static void createHTML(String fname){
        try{
            File file = new File(fname);
            if(file.createNewFile())
                System.out.println("File created: " + file.getAbsolutePath());
            else{
                System.out.println("A file with that name already exists, using that one instead.");
                System.out.println("File path: " + file.getAbsolutePath());
            }
        }catch(IOException e){
            System.out.println("Error creating file");
            e.printStackTrace();
        }
    }
    
    /**
     * Public method that initializes a deck by adding cards in order of color
     * and number.
     * 
     * @param deck the deck to be created
     * @param include_specials used as a condition to include or not include
     *                         action cards
     * @param index used to keep track of which deck is being initialized if
     *              there is more than one deck
     * @return the deck after being filled with cards
     */
    public static Deck deckInitializer(Deck deck, String include_specials, int index) {
        String[] colors = {"blue", "yellow", "red", "green", "black"};
        String[] specials = {"Skip", "Draw 2", "Reverse", "Wild", "Wild Draw 4", "none"};
        int[] number = {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10};
        //Hard coded to initialize deck
        for(int i = 0 + index; i < 108 + index; i++) {
            if (i >= 0 + index && i < 19 + index)//blue nums
                deck.addCard(colors[0], specials[5], number[i - index]);
            else if ("y".contains(include_specials) && i >= 19 + index && i < 22 + index){//blue specials
                deck.addCard(colors[0], specials[i - index - 19], number[19]);
                deck.addCard(colors[0], specials[i - index - 19], number[19]);
            } else if (i >= 25 + index && i < 44 + index)//yellow nums
                deck.addCard(colors[1], specials[5], number[i - index - 25]);
            else if ("y".contains(include_specials) && i >= 44 + index && i < 47 + index) {//yellow specials
                deck.addCard(colors[1], specials[i - index - 44], number[19]);
                deck.addCard(colors[1], specials[i - index - 44], number[19]);
            } else if (i >= 50 + index && i < 69 + index)//red nums
                deck.addCard(colors[2], specials[5], number[i - index - 50]);
            else if ("y".contains(include_specials) && i >= 69 + index && i < 72 + index) {//red specials
                deck.addCard(colors[2], specials[i - index - 69], number[19]);
                deck.addCard(colors[2], specials[i - index - 69], number[19]);
            } else if (i >= 75 + index && i < 94 + index)//green nums
                deck.addCard(colors[3], specials[5], number[i - index - 75]);
            else if ("y".contains(include_specials) && i >= 94 + index && i < 97 + index) {//green specials
                deck.addCard(colors[3], specials[i - index - 94], number[19]);
                deck.addCard(colors[3], specials[i - index - 94], number[19]);
            } else if("y".contains(include_specials) && i >= 100 + index && i < 104 + index){//wild and +4
                deck.addCard(colors[4], specials[3], number[19]);
                deck.addCard(colors[4], specials[4], number[19]);
            }
        }
        return deck;
    }
    
    /**
     * Public method initializes one or more decks under a single variable name
     * before calling the shuffle method from the deck class.
     * 
     * @param decks the number of deck to be shuffled
     * @param specials the the amount of action cards to be used in the deck
     * @param include_specials the condition that tells the program if the user
     *                         elected to use action cards
     * @return the populated deck
     */
    public static Deck shuffleTogether(int decks, int specials, String include_specials) {
        Deck deck = new Deck(decks, specials);
        for(int i = 0; i < decks; i++){
            int index = i * 108;
            deck = deckInitializer(deck, include_specials, index);
        }
        deck.shuffleDeck();
        return deck;
    }
    
    /**
     * Public method that initializes multiple decks and shuffles 
     * them separately before adding them to a single, main deck.
     * 
     * @param decks the number of deck to be shuffled
     * @param specials the the amount of action cards to be used in the deck
     * @param include_specials the condition that tells the program if the user
     *                         elected to use action cards
     * @return the populated deck
     */
    public static Deck shuffleSeparate(int decks, int specials, String include_specials) {
        Deck bigDeck = new Deck(decks, specials);
        for(int i = 0; i < decks; i++) {
            Deck smolDeck = new Deck(1, specials);
            smolDeck = deckInitializer(smolDeck, include_specials, 0);//create mini deck
            smolDeck.shuffleDeck();//shuffle it
            
            int index = smolDeck.maxCards;
            for (int j = 0; j < index; j++) {
                Card card = smolDeck.drawCard();
                bigDeck.addCard(card.color, card.special, card.number);//add mini deck to big deck
            }
        }
        return bigDeck;
    }
}
