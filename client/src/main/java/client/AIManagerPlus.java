package client;

import card.Card;
import card.Island;
import event.ChoiceExploitForge;
import event.TypeChoice;
import forge.Basin;

/**
 * The Class AIManagerPlus describes a "intelligent" AI, that plays logically.
 */
public class AIManagerPlus extends AI{
	
	/**
	 * Choice forge if the first step in the event, first we toss a coin,
	 * depending on the result, the AI will try to buy a face or a card. 
	 * If it can't buy the face, it try to buy a card and vice-versa. 
	 *
	 * @param ch the choiceExploitForge object, containaing choiceForge with the forge and choiceExploit containing islands
	 */
	void ChoiceForge(ChoiceExploitForge ch) {
		int rand = getRandomBetweenMinMax(0, 1); 
		if( rand == 0 ) {
			if(!handleForge(ch)) {
				handleExploit(ch);
			}
		}
		else if(rand == 1)
			if(!handleExploit(ch)) {
				handleForge(ch);
			}
	}
	
	/**
	 * This function browse the basin to find the most expensive face the player can buy
	 * Sets randomly which Dice will be modified (1-2), 
	 * Sets randomly which Face on a Dice will be settled (1-6).
	 *
	 * @param chFE the ch FE
	 * @return true, if successful
	 */
	boolean handleForge(ChoiceExploitForge chFE) {
		boolean rslt = false;
        chFE.setType(TypeChoice.FORGE);
        
        if(chFE.getChF().getForge().getShop().size() == 0) {
            chFE.setType(TypeChoice.NOTHING);
            return rslt;
        }
        
        int lastBasin = chFE.getChF().getForge().getShop().size() - 1;
        
        if(chFE.getChF().getForge().getShop().get(lastBasin).getBasinFace().isEmpty()) {
            chFE.getChF().getForge().getShop().remove(lastBasin);
            this.handleForge(chFE);
        }
        
        else {
            int randFace = getRandomBetweenMinMax(0, chFE.getChF().getForge().getShop().get(lastBasin).getBasinFace().size() -1);
            
            if(cantBuyFace(chFE.getChF().getForge().getShop().get(lastBasin).getBasinFace().get(randFace), chFE.getChF())) {
                chFE.getChF().getForge().getShop().get(lastBasin).getBasinFace().remove(randFace);
                this.handleForge(chFE);
            }
            else {
                chFE.getChF().setRandDice(getRandomBetweenMinMax(1, 2));
                chFE.getChF().setRandFace(getRandomBetweenMinMax(0, 5));
                chFE.getChF().setIndexBassin(lastBasin);
                chFE.getChF().setIndexFace(randFace);
                rslt = true;
            }
            
        }
        return rslt;
    }
	
	/**
	 * Determines which card the player choose, here we browse all cards available until one is buyable
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
		
		int lastIsland = chFE.getChE().getIslands().size() -1;
		
		if(chFE.getChE().getIslands().get(lastIsland).getCardsAvailable().isEmpty()) {
			chFE.getChE().getIslands().remove(lastIsland);
			this.handleExploit(chFE);
		}
		else {
			int randomCard = getRandomBetweenMinMax(0, chFE.getChE().getIslands().get(lastIsland).getCardsAvailable().size()-1);
			
			if(cantBuyCard(chFE.getChE().getIslands().get(lastIsland).getCardsAvailable().get(randomCard), chFE.getChE())) {
				chFE.getChE().getIslands().get(lastIsland).getCardsAvailable().remove(randomCard);
				this.handleExploit(chFE);
			}
			else {
				chFE.getChE().setRandIsland(lastIsland);
				chFE.getChE().setRandCard(randomCard);
				rslt = true;
			}
		}
		return rslt;
	}		
}
