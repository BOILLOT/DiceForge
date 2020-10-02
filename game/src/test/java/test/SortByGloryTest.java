package test;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import client.Player;
import game.PlayerManager;
import game.SortByGlory;
import resources.TypeResource;
import game.GameManager;

public class SortByGloryTest {
	PlayerManager p1;
	PlayerManager p2;
	Player pl1;
	Player pl2;
	SortByGlory s;
	GameManager g;
	
	
	@BeforeEach
	public void setUp() {
		s = new SortByGlory();
		pl1 = new Player(new UUID(0,0), "Tony");
		pl2 = new Player(new UUID(0,0), "Andre");
		p1 = new PlayerManager(pl1, g);
		p2 = new PlayerManager(pl2, g);
		p1.getInventory().addRessourcesByType(TypeResource.GLORY, 10);
		p2.getInventory().addRessourcesByType(TypeResource.GLORY, 6);
	}
	
	
	@Test
	public void compareTest() {
		assertEquals(-4, s.compare(p1, p2));
	}
}
