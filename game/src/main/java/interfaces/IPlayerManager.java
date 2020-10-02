package interfaces;

import client.Inventory;

/**
 * The Interface IPlayerManager is used te send data from playerManager to the handlerChoiceFace
 */
public interface IPlayerManager {
	
	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	public Inventory getInventory();
}
