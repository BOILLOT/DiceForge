package interfaces;

import java.util.UUID;

import event.Protocol;

/**
 * The Interface IServer is used to transmit from lower abstraction 
 */

public interface IServer {
	
	/**
	 * Send to client.
	 *
	 * @param id the id of the player
	 * @param p the protocol
	 * @param o the object to send
	 */
	public void sendToClient(UUID id, Protocol p, Object o);
	
	/**
	 * Gets the game number.
	 *
	 * @return the game number
	 */
	public int getGameNbr();

}
