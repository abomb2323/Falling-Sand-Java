package fallingsand;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class ScreenCanvas extends Canvas{
	
	private GraphicsContext gc;
	private int width, height;
	
	public ScreenCanvas(int width, int height) {
		super(width, height);
		
		this.width = width;
		this.height = height;
		
		gc = this.getGraphicsContext2D();
	}
	
	public GraphicsContext getGC() {
		return gc;
	}
}
