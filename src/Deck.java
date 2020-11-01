/*
 * This class creates a new deck that can vary in size based on the ammount of
 * decks chosen by the user and if the user allows the use of special cards.
 * This class functions like a stack but with the added bonus of being able to
 * insert one card at a time from the bottom as well as the top.
 */
//package Deck;
import java.util.Random;

/**
 * @author David Hoefs
 * @author Javier Vasquez
 */
public class Deck {
    private Card[] deck;

    /**
     * index keeps track of how many cards are in the deck.
     */
    public int index;

    /**
     * maxCards is calculated at the beginning and determines the total number
     * of cards the deck can hold.
     */
    final public int maxCards;
    
    /**
     * Class constructor that calculates the max number of cards and creates a
     * new deck.
     * @param decks
     * @param specials
     */
    public Deck(int decks, int specials) {
        this.index = 0;
        this.maxCards = (76 + specials) * decks;
        this.deck = new Card[maxCards];
    }
    
    /**
     * Private method that returns the top card of the deck based on the index
     * of the class.
     * 
     * @return the top card of the deck
     */
    private Card topCard() {
        if (index == 0) {
            System.out.print("Error: Decks is empty, cannot look at top card.");
            return null;
        }
        return deck[index - 1];
    }
    
    /**
     * Public method that pops the top card out of the stack and returns it.
     * Returns an error if the stack is empty.
     * Decrements the class's index.
     * 
     * @return the top card of the deck
     */
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
    
    /**
     * Public method pushes to the top of the stack.
     * Returns nothing.
     * Increments the class's index.
     * 
     * @param color specifies the color of the card.
     * @param special specifies the special action of the card.
     * @param number specifies the number of the card.
     */
    public void addCard(String color, String special, int number) {
        if (index == maxCards) {
            System.out.print("Error: Deck is full, cannot add card");
            return;
        }
        
        this.deck[index] = new Card(color, special, number);
        this.index++;
    }
    
    /**
     * Public method pushes to the bottom of the stack and moves all other cards
     * up once.
     * Returns nothing.
     * Increments the class's index.
     * 
     * @param card specifies the color, special action, and number of the card.
     */
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
    
    /**
     * Public method shuffles deck by getting 2 random indexes from the deck and
     * swapping the cards stored at those indexes.
     */
    public void shuffleDeck() {
       Random rand = new Random();
       for (int i = 0; i < this.deck.length; i++) {
           int newRandIndex = rand.nextInt(this.deck.length);
           Card temp = this.deck[newRandIndex];
           this.deck[newRandIndex] = this.deck[i];
           this.deck[i] = temp;
       }
    }
    
    /**
     * Public method returns the number of cards currently in the deck. 
     * Used to print the number of cards left as they are being drawn from the
     * deck.
     * 
     * @return the total number of cards in the deck.
     */
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
