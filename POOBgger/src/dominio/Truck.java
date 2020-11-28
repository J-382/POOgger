package dominio;

import java.util.ArrayList;

/**
 * POOgger's truck implementation
 * @version 2.2
 * @author Angie Medina - Jose Perez
 * */
public class Truck extends Carrier{
	
	private Animator animator;
	private int frame;
	private String orientation;
	
	/**
	 * Truck class constructor
	 * @param x Truck's x position
	 * @param y Truck's y position
	 * @param speed Truck's speed
	 * @param flipped if the Truck are flipped horizontally
	 * */
	public Truck(int x, int y, int speed,int[] size, String sprite, boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.maxCarryNumber = 1;
		this.carried = new ArrayList<Pushable>();
		this.isVisible = true;
		orientation = flipped?"F":"";
		frame = 0;
		animator = new Animator();
		sprite = "Truck"+(frame+1)+orientation;
	}
	
	/**
	 * Plays truck's move animation
	 * */
	private void updateSprite() {
		String state = carrying?"C":"";
		frame = (frame+1)%2;
		sprite = "Truck"+state+(frame+1)+orientation;
	}
	
	@Override
	public void stopCarrying(Pushable c) {
		sprite = "Truck"+(frame+1)+orientation;
		if(((Element) c).isPlayable()) ((Playable) c).makeToxic();
		System.out.println(((Playable) c).isToxic());
		super.stopCarrying(c);
	}
	
	@Override
	public void move() {
		super.move();
		if(!animator.isRunning()) {
			animator.animate(5,2,new Runnable() {public void run() {updateSprite();}});
		}
	}
	
	/*Usar la direccion del jugador para evitar las colisiones*/
	@Override
	public void startCarrying(Pushable c) {
		if(c.setPosition(x+48, y)) {
			c.setVisible(false);
			super.startCarrying(c);
		}
	}
	
	public boolean inCollision(Element e) {
		boolean isDead = true;
		if(e.isPlayable()) {
			((Playable) e).makeToxic(true);
		}
		if(e.getX()<x) {
			isDead = !((Playable) e).isArmored();
			if(!isDead) isVisible = false;
		}else isDead = super.inCollision(e);
		return isDead;
	}
	
}