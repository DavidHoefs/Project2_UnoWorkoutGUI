/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Deck;
import java.util.Random;

/**
 *
 * @author Javier
 */
public class Deck {
    private Card[] deck;
    private int index;
    final public int maxCards;
    
    public Deck(int decks, int specials) {
        this.index = 0;
        this.maxCards = (76 + specials) * decks;
        this.deck = new Card[maxCards];
    }
    
    private Card topCard() {
        if (index == 0) {
            System.out.print("Error: Decks is empty, cannot look at top card.");
            return null;
        }
        return deck[index - 1];
    }
    
    public Card drawCard() {
        if (index == 0) {
            System.out.print("Error: Deck is empty, cannot draw card.");
            return null;
        }
        
        Card myCard = topCard();
        this.index--;
        this.deck[index] = null;
        return myCard;
    }
    
    public void addCard(String color, String special, int number) {
        if (index == maxCards) {
            System.out.print("Error: Deck is full, cannot add card");
            return;
        }
        
        this.deck[index] = new Card(color, special, number);
        this.index++;
    }
    
    public void addCardBottom(Card card) {
        if (index == maxCards) {
            System.out.print("Error: Deck is full, cannot add card");
            return;
        }
        Card moveUp;
        Card insert = card;
        
        for (int i = 0; i < index + 1; i++) {
            moveUp = this.deck[i];
            this.deck[i] = insert;
            insert = moveUp;
        }
        index++;
    }
    
    public void shuffleDeck() {
       Random rand = new Random();
       for (int i = 0; i < this.deck.length; i++) {
           int newRandIndex = rand.nextInt(this.deck.length);
           Card temp = this.deck[newRandIndex];
           this.deck[newRandIndex] = this.deck[i];
           this.deck[i] = temp;
       }
    }
    
    public int getCardCount(){
        int output = 0;
        for(int i = 0;i<deck.length;i++){
            if(deck[i] != null){
                output++;
            }
        }
        return output;
    }
}
