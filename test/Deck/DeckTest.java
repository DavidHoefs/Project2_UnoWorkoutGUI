/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Deck;

import Deck.Deck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author David Hoefs
 */
public class DeckTest {
    private Card testCard;
    private Deck testDeck;
    
    
    public DeckTest() {
        
    }
    
    @Before
    public void setUp() {
        testDeck = Deck.shuffleTogether(1, 32, "y");
        testCard = new Card("red","none",3);
                
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of drawCard method, of class Deck.
     */
    @Test
    public void testDrawCard() {
        
        System.out.println("drawCard");
        Deck instance = testDeck;
        Card expResult = testDeck.drawCard();
        Card result = instance.drawCard();
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addCard method, of class Deck.
     */
    @Test
    public void testAddCard() {
        System.out.println("addCard");
        String color = "red";
        String special = "none";
        int number = 3;
       int beforeCount = testDeck.getCardCount();
        Deck instance = testDeck;
        instance.drawCard();
        instance.addCard(color, special, number);
        int after = instance.getCardCount();
        assertEquals(beforeCount,after);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addCardBottom method, of class Deck.
     */
    @Test
    public void testAddCardBottom() {
        Deck instance = testDeck;
        testDeck.drawCard();
        int beforeCardCount = instance.getCardCount();
        System.out.println("addCardBottom");
        Card card = testCard;
        
        instance.addCardBottom(card);
        int afterCardCount = instance.getCardCount();
        assertTrue(beforeCardCount < afterCardCount);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of getCardCount method, of class Deck.
     */
    @Test
    public void testGetCardCount() {
        System.out.println("getCardCount");
        
        Deck instance = testDeck;
        int expResult = 108;
        int result = instance.getCardCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
