/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Deck.Card;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Deck.Deck;
import Deck.Hand;
import static java.lang.Integer.parseInt;
import static javafx.application.Application.launch;
import javafx.event.Event;
/**
 *
 * @author David Hoefs
 */
public class UnoDeckWorkoutGui extends Application {
    
    private TextField tfNumberOfDecks = new TextField();
    private TextField tfIncludeActionCards = new TextField();
    private TextField tfShuffleCards = new TextField();
    private TextField tfBlueWorkout = new TextField();
    private TextField tfRedWorkout = new TextField();
    private TextField tfGreenWorkout = new TextField();
    private TextField tfYellowWorkout = new TextField();
    private Button btnCreateWorkout = new Button();
    private TextArea tfWorkoutOutput = new TextArea();
    private Button btnNextRound = new Button();
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(new Label("How many decks would you like to use? [1-3]"), 0, 0);
        grid.add(tfNumberOfDecks, 1, 0);
        grid.add(new Label("Action Cards?[Y/N] "), 0, 1);
        grid.add(tfIncludeActionCards, 1, 1);
        grid.add(new Label("Shuffle Cards Together? [Y/N]"), 0, 2);
        grid.add(tfShuffleCards, 1, 2);
        grid.add(new Label("Blue Workout: "), 0, 3);
        grid.add(tfBlueWorkout, 1, 3);
        grid.add(new Label("Red Workout: "), 0, 4);
        grid.add(tfRedWorkout, 1, 4);
        grid.add(new Label("Green Workout: "), 0, 5);
        grid.add(tfGreenWorkout, 1, 5);
        grid.add(new Label("Yellow Workout: "), 0, 6);
        grid.add(tfYellowWorkout, 1, 6);
        grid.add(btnCreateWorkout, 1, 7);
        grid.add(btnNextRound,2,7);
        grid.add(tfWorkoutOutput,1,9);
        
        // UI Properties
        grid.setAlignment(Pos.CENTER);
        btnCreateWorkout.setText("Create Workout");
        btnCreateWorkout.setOnAction( e -> createWorkout(true));
        btnNextRound.setAlignment(Pos.BOTTOM_LEFT);        
        btnNextRound.setText("Next Round");
        btnNextRound.setOnAction(e -> nextRound());
        

        // setup scene
        Scene scene = new Scene(grid, 300, 250);
        
        primaryStage.setTitle("Uno Workout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private Deck fullDeck;
    
    
    private void nextRound(){
        createWorkout(false);
    }
    private void createWorkout(boolean firstRound){
        Deck mainDeck;
        String blueWorkout = tfBlueWorkout.getText();
        String redWorkout = tfRedWorkout.getText();
        String greenWorkout = tfGreenWorkout.getText();
        String yellowWorkout = tfYellowWorkout.getText();
        if(firstRound){
            
            String numDecks = tfNumberOfDecks.getText();
            String includeActionCards = tfIncludeActionCards.getText();
            
            int specials = 32;
            if("y".equals(includeActionCards))
                specials = 32;
            else
                specials = 0;
            
            String together = tfShuffleCards.getText();
            
            // choose whether or not to shuffle action cards
            if(together == "y"){
                mainDeck = shuffleTogether(parseInt(numDecks), specials, includeActionCards);
            }else{
                mainDeck = shuffleSeparate(parseInt(numDecks), specials, includeActionCards);
            }
        }
        
        else{
            mainDeck = fullDeck;
        }
        // when we have used all of the cards
        if(mainDeck == null){
            String finishedOutput = "NO CARDS REMAINING!";
            tfWorkoutOutput.setText(finishedOutput);
            return;
        }
        //mainDeck = shuffleTogether(parseInt(numDecks), 1, "y");
       
        // create a player hand and draw 7 cards
        Card[] newHand;
        if(mainDeck.index > 6)
            newHand = new Card[7];
        else
            newHand = new Card[mainDeck.index];
        for(int i =0;i<newHand.length;i++){
            newHand[i] = mainDeck.drawCard();
        }
        
        String outputString = "";
        // create hand
        Hand playerHand = new Hand(newHand);
        // String returned from sortHand() method describes the players cards
        String cardsBeforeSort = "SORTED HAND:\n" + playerHand.sortHand(mainDeck);
        
        
        
        // the string returned from configureWorkout method describes the players hand 
        String workoutConfigured = playerHand.configureWorkout(blueWorkout, greenWorkout, redWorkout, yellowWorkout);
        
        
        
        outputString +=("PLAYER HAND AFTER ACTION CARDS: \n");
        for(int i = 0;i<playerHand.playerHand.length;i++){
            outputString += "Color: " + playerHand.playerHand[i].color.toUpperCase() + " Number: " + playerHand.playerHand[i].number + " Special: " + playerHand.playerHand[i].special.toUpperCase() + "\n";
            
        }
        // print the output to the TextArea
        
        fullDeck = mainDeck;
        int remainingCards = fullDeck.getCardCount();
        tfWorkoutOutput.setText(cardsBeforeSort + outputString + "\n" + workoutConfigured + "\nCards Remaining: " + remainingCards);
        
    }
    
    /**
     *
     * @param deck
     * @param include_specials
     * @param index
     * @return
     */
    public static Deck deckInitializer(Deck deck, String include_specials, int index) {
        String[] colors = {"blue", "yellow", "red", "green", "none"};
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
     *
     * @param decks
     * @param specials
     * @param include_specials
     * @return
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
     *
     * @param decks
     * @param specials
     * @param include_specials
     * @return
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
