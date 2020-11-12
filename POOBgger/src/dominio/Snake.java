package dominio;

public class Snake extends Element{
	
	private int speed;
	private int state;
	private String orientation;
	private Animator animator;
	
	public Snake(int xPos, int yPos, int speed, boolean flipped){
		sprite = "Snake1";
		x = xPos;
		y = yPos;
		this.speed = speed;
		orientation = "";
		state = 0;
		animator = new Animator();
		if(flipped) flip();
	}
	
	public void updateSprite(){
		state = (state+1)%4;
		if(state==2) y+=20;
		else if(state==3) y-=20;
		sprite = "Snake"+(state+1)+orientation;
	}
	
	public void move() {
		x += speed;
		if (x>600) flip();
		if(!animator.isRunning()) {
			animator.animate(200,2,new Runnable() {public void run() {updateSprite();}});
		}
		
	}
	
	public void flip() {
		speed *= -1;
		orientation = orientation.equals("F")?"":"F";
		sprite = "Snake"+(state+1)+orientation;
	}
	
	@Override
	public boolean inCollision(Element e) {
		return true;
	}
}
