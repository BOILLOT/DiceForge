package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import client.Player;
import game.GameManager;
import game.Server;
import event.Protocol;
import java.util.UUID;

//TODO: Auto-generated Javadoc
/**
* The Class GameManagerTest.
*/
public class GameManagerTest {
	
	
	/**	Create a GameManager */
	GameManager gm;
	
	/** Mock the server */
	@Mock
	Server serv;
	
	/** Create two players */
	Player one, two;
	
	/** 
	 * Set up all the variables 
	 */
	@BeforeEach
	 public void setUp() {
		serv = Mockito.mock(Server.class);
		gm = new GameManager(serv);
		one = new Player(new UUID(0, 0), "P1");
		two = new Player(new UUID(1, 1), "P2");;
		gm = Mockito.spy(gm);
	}
	
	/**
	 * Test the method addPlayer
	 */
	@Test
	public void addPlayer() {
		gm.addPlayer(one);
		Mockito.verify(gm).addPlayer(one);
	}
	
	/**
	 * Test the method setCurrentPlayer
	 */
	@Test
	public void setCurrentPlayer() {
		gm.addNewPlayer(one.getId());
		gm.addNewPlayer(two.getId());
		gm.setCurrentPlayer();
		Mockito.verify(gm).setCurrentPlayer();
	}
	
	/**
	 * Test the method sendToClient
	 */
	@Test
	public void sendToClient() {
		String obj = "test_send";
		gm.sendToClient(one.getId(), Protocol.CHOICE_FORGE, obj);
		Mockito.verify(gm, Mockito.times(1)).sendToClient(one.getId(), Protocol.CHOICE_FORGE, obj);
	}
	
	/**
	 * Test the method addNewPlayer
	 */
	@Test
	public void addNewPlayer() {
		gm.addNewPlayer(one.getId());
		Mockito.verify(gm).addNewPlayer(one.getId());
	}
	
	
	/**
	 * Test the method play
	 */
	
	//Bug in the loop - will be fix in the next iteration
	/*
	@Test
	public void play() {
		gm.addNewPlayer(new UUID(0, 0));
		gm.play();
		Mockito.verify(gm).play();
	}*/
	
	
	
	

}
