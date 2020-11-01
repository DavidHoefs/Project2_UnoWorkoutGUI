package Deck;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Deck.Card;
import Deck.Deck;



/**
 *
 * @author David Hoefs
 * @author Javier Vasquez
 */
public class Hand {
    // array of Card objects - represents the players hand of 7 cards

    /**
     * This represents the 7 cards which are currently in the players hand
     */
    public  Card[] playerHand;
    
    

    /**
     * This represents the count of BLUE cards in the player hand
     */
    public int blueCount = 0,

    /**
     * This represents the count of RED cards in the player hand
     */
    redCount = 0,

    /**
     * This represents the count of YELLOW cards in the player hand
     */
    yellowCount = 0,

    /**
     * This represents the count of GREEN cards in the player hand
     */
    greenCount= 0,

    /**
     * This represents the count of SPECIAL cards in the player hand
     */
    specialCount = 0,

    /**
     * This represents the count of BURPEES added to the workout
     */
    burpeeCount = 0;
    // String to assign workout to color

    /**
     * Holds the String value of the user entered BLUE workout
     */
    public String blueWorkout,

    /**
     * Holds the String value of the user entered GREEN workout
     */
    greenWorkout,

    /**
     * Holds the String value of the user entered YELLOW workout
     */
    yellowWorkout,

    /**
     * Holds the String value of the user entered RED workout
     */
    redWorkout;
    
    // Constructor which initializes the player hand

    /**
     * 
     * @param inputHand
     */
    public Hand(Card[] inputHand ){
        this.playerHand = inputHand;
    }
    
    /**
     * This method is used while sorting the player hand by color
     * @param color - the color of the card in the players hand
     * @return an int value which represents the color of the card
     */
    public int colorToInt(String color){
        int output = 0;
        if(color == "red"){
            return 1;
        }else if(color == "blue"){
            return 2;
        }else if(color == "green"){
            return 3;
        }else if(color == "yellow"){
            return 4;
        }else if(color == "black"){
            return 5;
        }else{
            return 6;
        }
    }
    // bubble sort color

    /**
     * This method sorts the player hand first by color and then by number
     * Uses a modified implementation of Bubble Sort 
     * First Pass sorts by card color
     * Second pass sorts by card number
     * @param mainDeck the deck of cards 
     * @return a String value which represents the player hand before sorting
     */
    public String sortHand(Deck mainDeck){
        

      
        String cardsBeforeSort = getColorCount();

        
        // sort by color
        int n = playerHand.length;
        for(int i = 0;i< n-1;i++){
            for(int j = 0;j<n-i-1;j++){
                if(colorToInt(playerHand[j+1].color) >= colorToInt(playerHand[j].color)){
                    Card temp = playerHand[j];
                    playerHand[j] = playerHand[j+1];
                    playerHand[j+1] = temp;
                }
            }
        }
        // sort by number by color
        for(int i = 0;i< n-1;i++){
            for(int j = 0;j<n-i-1;j++){
                if(colorToInt(playerHand[j].color) == colorToInt(playerHand[j+1].color) && playerHand[j].number > playerHand[j+1].number){
                    Card temp = playerHand[j];
                    playerHand[j] = playerHand[j+1];
                    playerHand[j+1] = temp;
                }
            }
        }
        findActionCards(mainDeck);
            
        
        return cardsBeforeSort;
    }
    
    /**
     * This method finds the action cards within the player hand and executes the corresponding methods
     * @param deck the deck of cards for the workout
     */
    private void findActionCards(Deck deck){
        int actionCardCount = 0;
        int index = 0;
        int countWild4 = 0;
        int countWild = 0;
        String[] skip = new String[6];
        String[] reverse = new String[6];
        String[] draw = new String[6];
        int drawCount = 0;
        int reverseCount = 0;
        int skipCount = 0;
        while( index < playerHand.length){
            if(playerHand[index].special == "Reverse" ){
                reverse[reverseCount] = playerHand[index].color;
                reverseCount++;
                //reverseAction(playerHand[index].color,deck);
                index++;
                actionCardCount++;
            }
            else if(playerHand[index].special == "Skip" ){
                skip[skipCount] = playerHand[index].color;
                skipCount++;
               // skipAction(playerHand[index].color);
                index++;
                actionCardCount++;
            }
            else if(playerHand[index].special == "Draw 2" ){
                 draw[drawCount] = playerHand[index].color;
                 drawCount++;
                //drawTwoAction(playerHand[index].color);
                index++;
                actionCardCount++;
            }
             else if(playerHand[index].special == "Wild Draw 4" ){
                //wildAction4();
                countWild4++;
               // burpeeCount += 4;
                index++;
                actionCardCount++;
            } else if(playerHand[index].special == "Wild" ){
               // burpeeCount += 4;
               countWild++;
                index++;
                actionCardCount++;
            }else{
               index++; 
            }
        }
        for(int i = 0;i<skipCount;i++){
            skipAction(skip[i]);
        }
        for(int i = 0;i<reverseCount;i++){
            this.reverseAction(reverse[i],deck);
        }
        for(int i = 0;i<drawCount;i++){
            drawTwoAction(draw[i]);
        }
        
        
        for(int i = 0;i<countWild;i++){
            this.burpeeCount+=4;
        }
        for(int i = 0;i<countWild4;i++){
             blueCount *= 4;
            greenCount *= 4;
            yellowCount *= 4;
            redCount *= 4;
            burpeeCount += 4;
        }
        
        
    }
    
    /**
     * This method implements the reverse action card
     * @param color the color of the reverse card in the player hand
     * @param deck the deck being used for the workout
     */
    public void reverseAction(String color,Deck deck){
        int [] indexArr = new int[7];
        int indexCounter = 0;
        // searches the player hand for any card with matching color
        for(int i = 0;i<playerHand.length;i++){
            // if reverse card, store index where that card is located
            if(playerHand[i].special == "Reverse"){
                indexArr[indexCounter] = i;
                indexCounter++;
            }
            // if the card is the same color of the reverse card
            // add to the bottom of the deck
            // store index where that card was held
            else if(playerHand[i].color == color){
                deck.addCardBottom(playerHand[i]);
                indexArr[indexCounter] = i;
                indexCounter++;
            }
        }
        // create new Card[] that is the size after the reverse cards are removed
        Card[] tmpArr = new Card[playerHand.length - indexCounter];
        indexCounter = 0;
        // only keep cards which were not the same color as the reverse card
        for(int i = 0,k=0;i<playerHand.length;i++){
            if(indexArr[indexCounter] == i){
                indexCounter++;
                continue;
            }
            tmpArr[k++] = playerHand[i];
        }
        playerHand = tmpArr;
        
        // clear out the color count for the color of the reverse card
        if(color == "blue"){
            blueCount = 0;
        }else if(color == "red"){
            redCount = 0;
        }else if(color == "green"){
            greenCount = 0;
        }else if (color == "yellow"){
            yellowCount = 0;
        }
    }
    
    
    /**
     * This method implements the skip action cards action
     * @param color the color of the skip card in player hand
     * 
     */
    public void skipAction(String color){
        int [] indexArr = new int[6];
        int indexCounter = 0;
        // find cards that are same color as skip card
        for(int i = 0;i<playerHand.length;i++){
            if(playerHand[i].color == color){
                indexArr[indexCounter] = i;
                indexCounter++;
            }
        }
        
        // Only keep cards which were not the color of the skip card
        Card[] tempArr = new Card[playerHand.length - (indexCounter)];
        indexCounter = 0;
        for(int i=0,k=0;i <playerHand.length;i++){
            if(indexArr[indexCounter] == i){
                indexCounter++;
                continue;
            }
            tempArr[k++] = playerHand[i];
        }
        playerHand = tempArr;
        // clear out count for color of skip card
        if(color == "blue"){
            blueCount = 0;
        }else if(color == "red"){
            redCount = 0;
        }else if(color == "green"){
            greenCount = 0;
        }else if (color == "yellow"){
            yellowCount = 0;
        }
    }
    
    /**
     * This method implements the draw two action 
     * @param color the color of the draw two card in player hand
     * Multiplies the count of the input color by 2
     */
    public void drawTwoAction(String color){
            if(color == "blue"){
                blueCount *= 2;
            }
            else if(color == "red"){
                redCount *= 2;
            }
            else if(color == "yellow"){
                yellowCount *= 2;
            }
            else if(color == "green"){
                greenCount *=2;
            }
    }
    /**
     * This method implements the wild draw 4 action card
     * Multiplies the total count of each color by 4
     */
    public void wildAction4(){
        blueCount *= 4;
        greenCount *= 4;
        yellowCount *= 4;
        redCount *= 4;
        burpeeCount += 4;
    }
    
    // returns the count of each color card

    /**
     * This method retrieves the color count for each color in the players hand
     * @return a String which holds the color count for each color in the players hand
     */
    public String getColorCount(){
        String output = "";
        for(int i = 0;i<playerHand.length;i++){
            if(playerHand[i].color.toString() == "blue" && playerHand[i].special == "none"){
                blueCount += playerHand[i].number;  
            }
            else if(playerHand[i].color.toString() == "green"&& playerHand[i].special == "none"){
                greenCount+= playerHand[i].number;
            }
            else if(playerHand[i].color.toString() == "red"&& playerHand[i].special == "none"){
                redCount+= playerHand[i].number;
            }
            else if(playerHand[i].color.toString() == "yellow"&& playerHand[i].special == "none"){
                yellowCount+= playerHand[i].number;
            }else if(playerHand[i].color == "black" && playerHand[i].special != "none"){
                specialCount++;
            }
            
            output += ("Card " + (i+1) + " :: " + playerHand[i].color +" ");
            
            if(playerHand[i].special != "none"){
                output += playerHand[i].special + "\n";
            }else{
               output += playerHand[i].number + "\n";
            }
        }
        output += "\n";
        return output;       
    }
    // assigns workout to colors

    /**
     * This method returns the workout for a round after the player hand has been 
     * sorted and the action cards have been applied 
     * @param blueWorkout the user input blue card workout
     * @param greenWorkout the user input green card workout
     * @param redWorkout the user input red card workout
     * @param yellowWorkout the user input yellow card workout
     * @return
     */
    public String configureWorkout(String blueWorkout,String greenWorkout,String redWorkout,String yellowWorkout){
        String output = "";
         this.blueWorkout = blueWorkout;
         this.greenWorkout = greenWorkout;
         this.redWorkout = redWorkout;
         this.yellowWorkout = yellowWorkout;
         output += ("\nWORKOUT:\n");
         output += (blueWorkout + ": " + blueCount + " | ");
         output +=(redWorkout + ": " + redCount+ " | ");
         output +=(yellowWorkout + ": " + yellowCount+ " | ");
         output += (greenWorkout + ": " + greenCount+ " | ");
         output += (" BURPEES:" + burpeeCount + "\n");
         /*
         for(int i = 0;i<playerHand.length;i++){
            if(playerHand[i].special == "Wild" || playerHand[i].special == "Wild Draw 4"){
                output += (" BURPEES:" + burpeeCount + "\n");
            }
        }*/
        return output;
    }
}
