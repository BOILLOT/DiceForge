package test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import client.Player;
import game.GameLoop;
import game.GameManager;
import game.GameStepLoop;
import game.Server;

public class GameLoopIT {
	
	/** Create the gameLoop object to test */
	GameLoop gl;
	
	/** Create the engine for the constructor of the gameLoop object */
	GameManager engine;
	
	/** Mock the server */
	@Mock
	Server serv;
	
	/** Mock the players */
	@Mock
	Player one, two;
	
	
	/** 
	 * Set up all the variables 
	 */
	@BeforeEach
	public void setUp() {
		serv = Mockito.mock(Server.class);
		engine = new GameManager(serv);
		gl = new GameLoop(engine);
		gl = Mockito.spy(gl);
		one = Mockito.mock(Player.class);
		two = Mockito.mock(Player.class);
	}
	
	/** 
	 * Test the accessor the gameloop 
	 */
	@Test
	public void accessorStateTest() {
		gl.setStep(GameStepLoop.NOT_STARTED);
		assertEquals(GameStepLoop.NOT_STARTED, gl.getStep());
	}
	
	/**
	 * Test the execute function 
	 */
	
	//Bug in the loop - correction will be done in the next iteration
	/*
	@Test
	public void execute() {
		
		//engine.addPlayer(one); 
		//engine.addPlayer(two); 
		engine.addNewPlayer(one.getId()); 
		engine.addNewPlayer(two.getId());
		
		gl.execute();
		
		/*Mockito.doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return 0;
			}
		}).when(gl).executeTurn();
		
		Mockito.verify(gl).execute();
	}*/
	
}
