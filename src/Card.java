//package Deck;

/*
 * This class creates a new card that stores a color, number, and special
 * action. Card is used in the Deck and Hand classes as well as the main client.
 */

/**
 * @author David Hoefs
 * @author Javier Vasquez
 */
public class Card {

    /**
     * color, for this project, can be blue, red, green, or yellow.
     */
    final public String color;

    /**
     * special, for this project, can be draw 2, reverse, skip, wild, wild 4.
     */
    final public String special;

    /**
     * number 0 - 10.
     */
    final public int number;
    
    /**
     * Class constructor that creates a card.
     * @param c the color value of the card
     * @param s the special category, if any, of the card
     * @param n the number, if any, of the card
     */
    public Card(String c, String s, int n){
        this.color = c;
        this.special = s;
        this.number = n;
    }
    
}
