package interfaces;

import java.util.UUID;
import event.Protocol;
import resources.Statistic;

/**
 * The Interface IGameManager is used to link PlayerManager to GameManager.
 */

public interface IGameManager {
	
	/**
	 * Gets the player stats.
	 *
	 * @param idPlayer the id player
	 * @return the player stats
	 */
	public Statistic getPlayerStats(UUID idPlayer);
	
	/**
	 * Send to client.
	 *
	 * @param id the id of a player
	 * @param p the protocol chosen
	 * @param o the object to send
	 */
	public void sendToClient(UUID id, Protocol p, Object o);

}
