package fallingsand;

import javafx.scene.paint.Color;
import java.util.Random;

public class Element {
	
	private String name;
	private Color color;
	private double gravity;
	private double spread;
	private double density;
	private int x,y;
	private int oldX, oldY;
	private double realX, realY;

	/**
	 * 
	 * @param name Name of the element.
	 * @param color Color of the element.
	 * @param gravity How fast the element falls (0 = stationary)
	 * @param spread How much the element fans out
	 * @param density How dense the material is
	 */
	public Element(String name, Color color, double gravity, double spread, double density) {
		this.name = name;
		this.color = color;
		this.gravity = gravity;
		this.spread = spread;
		this.density = density;
	}

	public void move(Element[][] elemArray) {

		oldX = x;
		oldY = y;
		
		realY += gravity;
		y = (int) realY;
		
		Random rand = new Random(System.currentTimeMillis()*this.hashCode());
		double chance = rand.nextDouble()*spread;
		
		if(chance > .5) {
			int headsTails = rand.nextInt(2);
			if(headsTails == 0) {
				realX += spread*2;
				x = (int) realX;
			} else {
				realX -= spread*2;
				x = (int) realX;
			}
		}
				
	}
	
	public int getOldX() {
		return oldX;
	}

	public void setOldX(int oldX) {
		this.oldX = oldX;
	}

	public int getOldY() {
		return oldY;
	}

	public void setOldY(int oldY) {
		this.oldY = oldY;
	}

	public double getRealX() {
		return realX;
	}

	public void setRealX(double realX) {
		this.realX = realX;
	}

	public double getRealY() {
		return realY;
	}

	public void setRealY(double realY) {
		this.realY = realY;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public double getGravity() {
		return gravity;
	}


	public void setGravity(double gravity) {
		this.gravity = gravity;
	}


	public double getSpread() {
		return spread;
	}


	public void setSpread(double spread) {
		this.spread = spread;
	}

	
	public double getDensity() {
		return density;
	}


	public void setDensity(double density) {
		this.density = density;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
