package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * POOgger's player implementation
 * @version 2.6
 * @author Angie Medina - Jose Perez
 * */
public class Player extends Playable implements Pushable{
	
	private final int delta = 48;
	private int state;
	private int minReachY;
	private int lastMove;
	
	private boolean beingCarried;
	private Carrier carrier;
	private Animator animator;
	
	/**
	 * Player class constructor
	 * @param initialLives Player's initialLives
	 * @param maxX Player's POOgger width
	 * @param maxY Player's POOgger height
	 * @param dimensions Player's size
	 */
	public Player(int initialLives,int initX, int initY, int[] size) {
		this.width = size[0];
		this.height = size[1]; 
		this.isVisible = true;
		this.lives = initialLives;
		this.initX = initX;
		this.initY = initY;
		animator = new Animator();
		resetPlayer();
	}
	
	public void move() {
		int delay = isFast?25:50;
		int dx = 0, dy = 0;
		switch (""+orientation) {
			case "W" :
				dy-=delta/3;
				break;
			case "S" :
				dy+=delta/3;
				break;
			case "D" :
				dx+=delta/3;
				break;
			case "A" :
				dx-=delta/3;
				break;
		}
		
		if (("" + orientation).equals("W")) {
			lastMove = (lastMove + 1) % 3;
			System.out.println("1");
			if (lastMove == 0 && getY() < minReachY) {
				System.out.println("2");
				minReachY = getY();
				increasePoints(10);
			}
		}
		super.move(dx, dy);
		updateSprite();
		if(!animator.isRunning()) {
			animator.animate(delay,3,new Runnable() {public void run() {move();}});
		}
		isInAir();
	}
	
	private void updateSprite() {
		state = (state+1)%3;
		sprite =  "Frog"+(state+1)+orientation;	
	}
	
	/**
	 * Changes, if possible, the player orientation
	 * @param orientation new orientation
	 * */
	public void setOrientation(char orientation) {
		if(!animator.isRunning()) {
			this.orientation = orientation;
			move();
			isFlying = false;
			if(beingCarried) {
				stopBeignCarried();
			}
		}
	}
	
	private void stopBeignCarried() {
		carrier.stopCarrying(this);
		carrier = null;
		setVisible(true);
		Timer doWhenAnimationStop = new Timer(1,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!animator.isRunning()) {
					beingCarried = false;
					((Timer) e.getSource()).stop();
				}
			}
		});
		doWhenAnimationStop.start();
	}
	
	/**
	 * Decrease the player lives and reset his position
	 * @param initx x inital position
	 * @param initx y inital position
	 * */
	public boolean decreasePlayerLives() {
		boolean revives = false;
		lives--;
		if(lives>0) {
			revives = true;
			resetPlayer();
		}
		return revives;
	}
	
	public void resetPlayer() {
		orientation = 'W';
		carrier = null;
		sprite =  "Frog"+(state+1)+orientation;
		animator.stop();
		state = 0;
		x = initX;
		y = initY;
		minReachY = y;
		lastMove = 0;
		isToxic = false;
		isArmored = false;
		isFast = false;
		canFly = false;
		isFlying = false;
		if(carrier!=null) stopBeignCarried(); 
	}
	
	/**
	 * Increase the player point by some amount
	 * @param value, the amount to add to the player's pointz
	 */
	public void increasePoints(int value) {
		points += value;
		System.out.println(points);
	} 
	
	/**
	 * Returns player's size
	 * @return player's size
	 * */
	public int[] getDimensions() {
		return new int[] {width,height};
	}
	
	/**
	 * Returns player's lives
	 * @return player's lives
	 * */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Returns the player's points
	 **/
	public int getPoints() {
		return points;
	}
	
	@Override
	public boolean isCarriable() {
		return !beingCarried;
	}
	
	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public void beingCarried(Carrier c) {
		beingCarried = true;
		//animator.stop();
		carrier = c;
	}
	
	@Override 
	public String getSprite() {
		String returnImage = isVisible?sprite:"";
		return returnImage;
	}
	
	@Override
	public boolean setPosition(int x, int y) {
		animator.stop();
		state = 0;
		this.x = x;
		this.y = y;
		return true;
		
	}

	@Override
	public void addPush(int push, String dir) {
		if(dir.equals("W") || dir.equals("S")) super.move(0, push);
		else super.move(push, 0);
	}
	
	@Override 
	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	@Override
	public char getDir() {
		return orientation;	
	}
	
	@Override
	public boolean isBeingCarried() {
		return beingCarried;
	}
	
	@Override
	public boolean isInAir() {
		isInAir = !(state==0) || isFlying;
		return isInAir;
	}
}
