package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * State Pattern implementation for Player's states
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public abstract class PlayerState implements Serializable{
	Player player;
	
	/**
	 * PlayerState class constructor
	 * @param player context
	 */
	PlayerState(Player player){
		this.player = player;
	}
	/**
	 * Returns the player's bounds for his current state
	 * @return the player's bounds for his current state
	 */
	public abstract Rectangle getBounds();
	
	public int getPointsOnDeath(){
		return -100;
	}
	
	public int getPointsOnArriving() {
		return 100;
	}
	
	/**
	 * Returns player's current state 
	 * @return player's current state 
	 */
	public abstract String getState();
}
