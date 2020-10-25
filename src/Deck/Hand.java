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
 */
public class Hand {
    // array of Card objects - represents the players hand of 7 cards
    public  Card[] playerHand;
    
    // int counters to count the number of cards the player has
    public int blueCount = 0,redCount = 0,yellowCount = 0,greenCount= 0,specialCount = 0,burpeeCount = 0;
    // String to assign workout to color
    public String blueWorkout,greenWorkout,yellowWorkout,redWorkout;
    
    // Constructor which initializes the player hand
    public Hand(Card[] inputHand ){
        this.playerHand = inputHand;
    }
    
    // insertion sort for sorting hand
    
    public  String sortHand(Deck mainDeck){
       boolean sameColor = false;
        for(int i = 0;i<playerHand.length - 1;i++){
            for(int k = i+1;k>0;k--){
                final Card card = getNextCard(k);
                final Card card2 = getNextCard(k - 1);
                if(card.color == card2.color){
                    sameColor = true;
                }
                if(card.color != card2.color ){
                    swapCards(k,k-1);
                    sameColor = false;
                    
                    
                }
                else if(card.number < card2.number && sameColor){
                    swapCards(k,k-1);
                }else{
                    break;
                }
                
            }
        }
        String cardsBeforeSort = getColorCount();
        if(specialCount > 0){
            findActionCards(mainDeck);
        }
        return cardsBeforeSort;
    }
    
    private void findActionCards(Deck deck){
        int actionCardCount = 0;
        int index = 0;
        while(actionCardCount < specialCount && index < playerHand.length){
            if(playerHand[index].special == "Reverse"){
                reverseAction(playerHand[index].color,deck);
                index++;
                actionCardCount++;
            }
            else if(playerHand[index].special == "Skip"){
                skipAction(playerHand[index].color);
                index++;
                actionCardCount++;
            }
            else if(playerHand[index].special == "Draw 2"){
                drawTwoAction(playerHand[index].color);
                index++;
                actionCardCount++;
            }
            else if(playerHand[index].special == "Wild Draw 4"){
                wildAction4();
                index++;
                actionCardCount++;
            }else if(playerHand[index].special == "Wild"){
                burpeeCount++;
                 index++;
                actionCardCount++;
                
            }else{
               index++; 
            }
            
        }
    }
    
    // swap method for insertion sort
    private void swapCards(int index,int index2){
        Card tempCard = playerHand[index];
        Card tempCard2 = playerHand[index2];
        playerHand[index] = tempCard2;
        playerHand[index2] = tempCard;
    }
    
    // returns the card at the input index
    private  Card getNextCard(int index){
        
        return this.playerHand[index];
    }
    
    public void reverseAction(String color,Deck deck){
        int [] indexArr = new int[6];
        int indexCounter = 0;
        for(int i = 0;i<playerHand.length;i++){
            if(playerHand[i].color == color && playerHand[i].special != "reverse"){
                deck.addCardBottom(playerHand[i]);
                indexArr[indexCounter] = i;
                indexCounter++;
            }
            if(playerHand[i].special == "reverse"){
                indexArr[indexCounter] = i;
                indexCounter++;
            }
        }
        Card[] tmpArr = new Card[playerHand.length - indexCounter];
        indexCounter = 0;
        for(int i = 0,k=0;i<playerHand.length;i++){
            if(indexArr[indexCounter] == i){
                indexCounter++;
                continue;
            }
            tmpArr[k++] = playerHand[i];
        }
        playerHand = tmpArr;
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
    
    // removes the cards with the color that is passed in from the players hand
    private void skipAction(String color){
        int [] indexArr = new int[6];
        int indexCounter = 0;
        for(int i = 0;i<playerHand.length;i++){
            if(playerHand[i].color == color){
                indexArr[indexCounter] = i;
                indexCounter++;
            }
        }
        
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
    // multiplies the total number of the specified colored card
    private void drawTwoAction(String color){
       
            if(color == "blue"){
                blueCount *= 2;
            }
            else if(color == "red"){
                redCount *= 2;
                
            }
            else if(color == "yellow"){
                yellowCount *= 2;
            }
            else{
                greenCount *=2;
            }
            
           
        
    }
    
    private void wildAction4(){
        blueCount *= 4;
        greenCount *= 4;
        yellowCount *= 4;
        redCount *= 4;
        burpeeCount++;
    }
    // returns the count of each color card
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
            }else if(playerHand[i].special != "none"){
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
        
         
//        System.out.println("WORKOUT DETAILS: ");
//        System.out.println("Blue Count: " + blueCount + " " + this.blueWorkout);
//        System.out.println("Red Count: " + redCount+ " " + this.redWorkout);
//        System.out.println("Green Count: " + greenCount+ " " + this.greenWorkout);
//        System.out.println("Yellow Count: " + yellowCount+ " " + this.yellowWorkout);
//        System.out.println("Special Count: " + specialCount);
//        System.out.println("-------------------------------------------------------");
//        
            
    }
    // assigns workout to colors
    public String configureWorkout(String blueWorkout,String greenWorkout,String redWorkout,String yellowWorkout){
        String output = "";
         this.blueWorkout = blueWorkout;
         this.greenWorkout = greenWorkout;
         this.redWorkout = redWorkout;
         this.yellowWorkout = yellowWorkout;
         output += ("WORKOUT:\n");
         output += (blueWorkout + " " + blueCount + "\n");
         output +=(redWorkout + " " + redCount+ "\n");
         output +=(yellowWorkout + " " + yellowCount+ "\n");
         output += (greenWorkout + " " + greenCount+ "\n");
         for(int i = 0;i<playerHand.length;i++){
             if(playerHand[i].special == "Wild" || playerHand[i].special == "Wild Draw 4"){
                output += (burpeeCount * 4 + " BURPEES"+ "\n");
            }
         }
         
        return output;
    }
}
