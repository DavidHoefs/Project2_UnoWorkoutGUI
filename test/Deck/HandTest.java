/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Hoefs
 */
public class HandTest {
    private Deck testDeck;
    private Hand testHand;
    private Hand reverseTestHand;
    private Hand drawTwoTestHand;
    private Hand skipTestHand;
    private Hand wildFourTestHand;
    public HandTest() {
        
    }
    
    @Before
    public void setUp() {
        testDeck = Deck.shuffleTogether(1, 32, "y");
        Card[] testCards = new Card[7];
        for(int i = 0;i<7;i++){
            testCards[i] = testDeck.drawCard();
        }
        testHand = new Hand(testCards);
        
        
        Card[] redCards = new Card[6];
        for(int i = 0;i<6;i++){
            if(i == 2){
                redCards[i] = new Card("red","Reverse",10);
            }else{
                
            redCards[i] = new Card("red","none",3);
            }
                    
        }
        reverseTestHand = new Hand(redCards);
        
        Card[] dTwo = new Card[6];
        for(int i = 0;i<6;i++){
            if(i == 2){
                dTwo[i] = new Card("red","Draw 2",10);
            }else{
                
            dTwo[i] = new Card("red","none",3);
            }
                    
        }
        drawTwoTestHand = new Hand(dTwo);
        
        Card[] skip = new Card[6];
        for(int i = 0;i<6;i++){
            if(i == 2){
                skip[i] = new Card("red","Skip",10);
            }else{
                
            skip[i] = new Card("red","none",3);
            }
                    
        }
        skipTestHand = new Hand(skip);
        
        Card[] four = new Card[6];
        for(int i = 0;i<6;i++){
            if(i == 2){
                four[i] = new Card("none","Wild Draw 4",10);
            }else{
                
            four[i] = testDeck.drawCard();
            }
                    
        }
        wildFourTestHand = new Hand(four);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of colorToInt method, of class Hand.
     */
    @Test
    public void testColorToInt() {
        System.out.println("colorToInt");
        
       
        String color = "red";
        Hand instance = testHand;
        int expResult = 1;
        int result = instance.colorToInt(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of reverseAction method, of class Hand.
     */
    @Test
    public void testReverseAction() {
        System.out.println("reverseAction");
        String color = "red";
        Deck deck = testDeck;
        
        Hand instance = reverseTestHand;
        instance.reverseAction(color, deck);
        assertEquals(instance.playerHand.length,0);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
    
    @Test
    public void testDrawTwoAction(){
        System.out.println("drawTwoAction");
        String color = "red";
        Deck deck = testDeck;
        Hand instance = drawTwoTestHand;
        instance.getColorCount();
        int expected = instance.redCount * 2;
        instance.drawTwoAction("red");
        int result = instance.redCount;
        assertEquals(result,expected);
        
    }
    
    @Test
    public void testSkipAction(){
        System.out.println("skipAction");
        String color = "red";
        Deck deck = testDeck;
        Hand instance = skipTestHand;
        instance.getColorCount();
        int expected = 0;
        instance.skipAction(color);
        int after = instance.redCount;
        assertEquals(expected,after);
    }
    
    @Test
    public void testWildFourAction(){
        System.out.println("wildDrawFourAction");
        Deck deck = testDeck;
        Hand instance = wildFourTestHand;
        instance.getColorCount();
        int redExpected = instance.redCount * 4;
        int blueExpected = instance.blueCount * 4;
        int greenExpected = instance.greenCount * 4;
        int yellowExpected = instance.yellowCount * 4;
        instance.wildAction4();
        int redResult = instance.redCount;
        int blueResult = instance.blueCount;
        int greenResult = instance.greenCount;
        int yellowResult = instance.yellowCount;
        assertEquals(redExpected,redResult);
        assertEquals(blueExpected,blueResult);
        assertEquals(greenExpected,greenResult);
        assertEquals(yellowExpected,yellowResult);
        
    }


}
