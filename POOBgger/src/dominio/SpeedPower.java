package dominio;

/**
 * Pogger's SpeedPower implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class SpeedPower extends Power{
	
	/**
	 * SpeedPower Class constructor
	 * @param x SpeedPower's x position
	 * @param y SpeedPower's y position
	 * @param width SpeedPower's width
	 * @param height SpeedPower's height
	 */
	public SpeedPower(int x, int y, int width, int height) {
		super(x,y,width,height);
		sprite = "Speed1";
		Animator animator = new Animator();
		animator.animate(100, 18, new Runnable() {public void run() {updateSprite();}},false);
	}
	
	/**
	 * Changes the SpeedPower's Sprite
	 */
	private void updateSprite() {
		state = (state+1)%16;
		sprite = "Speed"+(state+1);
	}
	
	/**
	 * Give SpeedPower to the given player
	 * @param Playable the desired player
	 */
	public void givePower(Playable player) {
		player.makeFast(true);		
	}
}
