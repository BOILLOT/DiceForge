package launcher;

import java.util.ArrayList;
import java.util.List;

import client.Client;
import client.NamePlayers;
import game.Server;
import game.Display;

/**
 * The Class Main.
 */
public class Main {
	
	/** The server of the game. */
	private static Server s;

	/**
	 * The main method.
	 *  Initialize the server and handle the 4 Clients in Thread
	 *
	 * @param args the arguments, -p N launches N games
	 */
	public static void main(String[] args) {
		int gameNbr = 1;
		
		if(args.length != 0) {
			int current = 0;
			for(String s : args) {
				if(s.equals("-p")) {
					gameNbr = Integer.parseInt(args[current+1]);
				}
				current++;
			}
		}
		
		if(gameNbr == 1) {
			Display.getInstance(true);
		}
		else {
			Display.getInstance(false);
		}
		
		s = new Server(gameNbr);
		
		Thread t = new Thread(s);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start(); //execute run() de Server
		
		synchronized(s) {
			try {
				s.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Thread> list = new ArrayList<Thread>();
		
		for(int i=0; i < 4; i++) {
			Client c = new Client();
			NamePlayers np = NamePlayers.values()[i];
			Thread t2 = new Thread(c);
			t2.setPriority(Thread.MAX_PRIORITY);
			list.add(t2);
			t2.start();
			
			
			synchronized(c) {
				try {
					c.wait();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		try {
			for(Thread client : list) {
				client.join();
			}
			t.join();
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		System.exit(0);
	}
}
