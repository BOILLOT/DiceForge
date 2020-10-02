package test;

import client.Inventory;
import client.Player;
import game.GameManager;
import game.PlayerManager;
import interfaces.IGameManager;
import resources.TypeResource;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
 
/**
 * The Class PlayerManagerTest.
 */

public class PlayerManagerTest {

	/** Create a new player */
	Player p;
	
	/** Create a new inventory */
	Inventory i;
	
	/** Create a new PlayerManager */
	PlayerManager pm;
	
	/**Create a  GameManager */
	 GameManager g;
	 

	/**
	 * Setup the variables.
	 */
	@BeforeEach
	public void setUp() {

		p = new Player(new UUID(0, 0), "Test");
		pm = new PlayerManager(p, g);
	}
	
	
	/**
	 * Test the getter getRank()
	 */
	@Test
	public void getRankTest() {
		assertEquals(0, pm.getRank());
	}
	
	/**
	 * Test  setRank()
	 */
	@Test
	public void setRankTest() {
		pm.setRank(2);
		assertEquals(2, pm.getRank());
	}
	
	
	/**
	 * Test the getter getPlayer() 
	 */
	@Test
	public void getPlayerTest() {
		assertEquals(p, pm.getPlayer());
	}
	
	/**
	 * Test the getter getinventory() 
	 */
	@Test
	public void getInventoryTest() {
		i = new Inventory();
		i =	pm.getInventory();
		assertEquals(i, pm.getInventory());
		pm.getInventory().addRessourcesByType(TypeResource.GOLD, 6);
		assertEquals(6, pm.getInventory().getNbrGold());
		
	}
	

}
