package client;

import java.util.Random;

import card.Card;
import card.Cost;
import event.ChoiceExploit;
import event.ChoiceExploitForge;
import event.ChoiceFace;
import event.ChoiceForge;
import face.Face;

/**
 * The Class AI.
 */
public abstract class AI {
	/** Defines a random for the class. */
	Random r;
	
	/**
	 * Determines which card the player choose, here we browse all cards available until one is buyable
	 * 	 *
	 * @param chFE a ChoiceExploitForge
	 * @return true, if successful
	 */
	
	abstract boolean handleExploit(ChoiceExploitForge chFE);
	
	/**
	 * This method randomly selects if a player buy something (0-1), 
	 * If it's the case, sets randomly which Face in the Forge will be bought (0 - Forge.size()), 
	 * Sets randomly which Dice will be modified (1-2), 
	 * Sets randomly which Face on a Dice will be settled (1-6).
	 *
	 * @param chFE the ch FE
	 * @return true, if successful
	 */
	
	abstract boolean handleForge(ChoiceExploitForge chFE);
	
	/**
	 *  Determines randomly if the player buys a card or a face .
	 *
	 * @param ch represents the object that will be filled by the Client's choice
	 */
	
	abstract void ChoiceForge(ChoiceExploitForge ch); 
	
	/**
	 * This function indicates if a Card can be purchased.
	 *
	 * @param c a Card, Ch a ChoiceExploit
	 * @param ch the ch
	 * @return b a boolean, true if the Card can be purchased
	 */
	
	public boolean cantBuyCard(Card c, ChoiceExploit ch){
        boolean b = false;
        for(Cost co : c.getCost()) { 
        	if(co.getValue() > ch.getListResourcesPlayer().get(co.getType())) {
        		b=true;
        		// regarde si je peux acheter la carte que j'ai tirer aléatoirement
        	}
        }
        return b;
    }
	
	/**
	 * This function indicates if a Face can be purchased.
	 *
	 * @param f a Face
	 * @param ch a ChoiceForge (containing player's gold amount)
	 * @return b a boolean, true if the Face can be purchased
	 */
	public boolean cantBuyFace(Face f, ChoiceForge ch) {
		boolean b = false;
	    if(f.getPrice() > ch.getGoldAmount()) {
	    	b = true;
	    }
	    return b;
	}
	
	/**
	 * Determines which face is chosen when the choiceFace event response is asked.
	 *
	 * @param ch the ChoiceFace object containing the face with a choice
	 */
	public void choiceFace(ChoiceFace ch) {
		int size = ch.getFace().getListFace().size()-1;
		int randF = getRandomBetweenMinMax(0, size);
		
		ch.setChoiceFace(ch.getFace().getListFace().get(randF));
	}
	
	/**
	 * Gets the random between min max.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the random between min max
	 */
	public int getRandomBetweenMinMax(int min, int max){
		r = new Random();
        return this.r.nextInt(max - min + 1) + min;
    }
}
