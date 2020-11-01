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
    public int index;
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
}
