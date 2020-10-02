package client;

import event.ChoiceExploitForge;
import event.TypeChoice;

// TODO: Auto-generated Javadoc
/**
 * The Class AIManager handle AI choices,
 * For now, no strategy, it's only random.
 */
public class AIRandom extends AI{
	
	/**
	 *  Determines randomly if the player buys a card or a face .
	 *
	 * @param ch represents the object that will be filled by the Client's choice
	 * *
	 */
	void ChoiceForge(ChoiceExploitForge ch) {
		
		int rand = getRandomBetweenMinMax(0, 2); 
		if( rand == 0 ) {
			handleForge(ch);
		}
		else if(rand == 1)
			handleExploit(ch);
		else
			ch.setType(TypeChoice.NOTHING);
		
	}
	
	/**
	 * This method randomly selects if a player buy something (0-1), 
	 * If it's the case, sets randomly which Face in the Forge will be bought (0 - Forge.size()), 
	 * Sets randomly which Dice will be modified (1-2), 
	 * Sets randomly which Face on a Dice will be settled (1-6).
	 *
	 * @param chFE the chFE
	 * @return true, if successful
	 */
	boolean handleForge(ChoiceExploitForge chFE) {
		boolean rslt = false;
        chFE.setType(TypeChoice.FORGE);
        
        if(chFE.getChF().getForge().getShop().size() == 0) {
            chFE.setType(TypeChoice.NOTHING);
            return rslt;
        }
        
        int randBasin = getRandomBetweenMinMax(0, chFE.getChF().getForge().getShop().size()-1);
        
        if(chFE.getChF().getForge().getShop().get(randBasin).getBasinFace().isEmpty()) {
            chFE.getChF().getForge().getShop().remove(randBasin);
            this.handleForge(chFE);
        }
        
        else {
            int randFace = getRandomBetweenMinMax(0, chFE.getChF().getForge().getShop().get(randBasin).getBasinFace().size() -1);
            
            if(cantBuyFace(chFE.getChF().getForge().getShop().get(randBasin).getBasinFace().get(randFace), chFE.getChF())) {
                chFE.getChF().getForge().getShop().get(randBasin).getBasinFace().remove(randFace);
                this.handleForge(chFE);
            }
            else {
                chFE.getChF().setRandDice(getRandomBetweenMinMax(1, 2));
                chFE.getChF().setRandFace(getRandomBetweenMinMax(0, 5));
                chFE.getChF().setIndexBassin(randBasin);
                chFE.getChF().setIndexFace(randFace);
                rslt = true;
            }
            
        }
        return rslt;
    }
	
	/**
	 * Determines which card the player choose, here we browse all cards available until one is buyable
	 * TODO.
	 *
	 * @param chFE a ChoiceExploitForge
	 * @return true, if successful
	 */
	boolean handleExploit(ChoiceExploitForge chFE) {
		boolean rslt = false;
		chFE.setType(TypeChoice.EXPLOIT);
		
		if(chFE.getChE().getIslands().size() == 0) {
			chFE.setType(TypeChoice.NOTHING);
			return rslt;
		}
		
		int randomIsland = getRandomBetweenMinMax(0, chFE.getChE().getIslands().size()-1);
		
		if(chFE.getChE().getIslands().get(randomIsland).getCardsAvailable().isEmpty()) {
			chFE.getChE().getIslands().remove(randomIsland);
			this.handleExploit(chFE);
		}
		else {
			int randomCard = getRandomBetweenMinMax(0, chFE.getChE().getIslands().get(randomIsland).getCardsAvailable().size()-1);
			
			if(cantBuyCard(chFE.getChE().getIslands().get(randomIsland).getCardsAvailable().get(randomCard), chFE.getChE())) {
				chFE.getChE().getIslands().get(randomIsland).getCardsAvailable().remove(randomCard);
				this.handleExploit(chFE);
			}
			else {
				chFE.getChE().setRandIsland(randomIsland);
				chFE.getChE().setRandCard(randomCard);
				rslt = true;
			}
		}
		return rslt;
	}	
	
}
