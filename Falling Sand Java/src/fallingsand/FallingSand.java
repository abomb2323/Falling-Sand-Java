package fallingsand;

/*
 * Main file, the application startpoint
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

import fallingsand.ScreenCanvas;

import java.util.ArrayList;

import fallingsand.Element;

public class FallingSand extends Application{
	
	final int width = 980;
	final int height = 720;
	
	private Element[][] elemArray; //Coordinate value of where an element is
	
	private Element currElem; //Current selected element
	private ArrayList<Element> elements; //List of all elements in the game
	private Element[] particles; //List of all particles on screen.
	
	int keyTimer = 0; //Add a buffer to the key presses
	
	int penSize = 10;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage sandStage) throws Exception {
		sandStage.setTitle("Falling Sand Game"); //Title
		
		Pane root = new Pane(); //Root pane
		StackPane holder = new StackPane(); //Holder StackPane for the background color
		
		Scene sandbox = new Scene(root, width, height); //Main scene of the game
		
		ScreenCanvas canvas = new ScreenCanvas(width, height); //Init our canvas
		
		holder.getChildren().add(canvas); //Add the canvas to the holder
		root.getChildren().add(holder); //add the holder to the canvas
		
		holder.setStyle("-fx-background-color: black"); //bg color black
		sandStage.setScene(sandbox); //Set the scene to our sandbox
		
		elemArray = new Element[width][height];
		elements = new ArrayList<Element>();
		initElements();
		
		//Keyboard input
		ArrayList<String> input = new ArrayList<String>();
		
		sandbox.setOnKeyPressed(
				new EventHandler<KeyEvent>() {
					public void handle(KeyEvent e) {
						String code = e.getCode().toString();
						if(!input.contains(code)) {
							input.add(code);
						}
					}
				});
		
		sandbox.setOnKeyReleased(
				new EventHandler<KeyEvent>() {
					public void handle(KeyEvent e) {
						String code = e.getCode().toString();
						input.remove(code);
					}
				});
		//End Keyboard input
		
		//Mouse input
		sandbox.setOnMouseDragged(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						
						for(int x = (int) (e.getX()-penSize); x <= (int) (e.getX()+penSize); x++) {
							for(int y = (int) (e.getY()-penSize); y <= (int) (e.getY()+penSize); y++) {
								addElementToScreen(currElem, (int) (penSize + x), (int) (penSize + y));
							}
						}
						
						//addElementToScreen(currElem, (int) e.getX(), (int) e.getY());
					}
				});
		
		sandbox.setOnMouseClicked(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						for(int x = (int) (e.getX()-penSize); x <= (int) (e.getX()+penSize); x++) {
							for(int y = (int) (e.getY()-penSize); y <= (int) (e.getY()+penSize); y++) {
								addElementToScreen(currElem, (int) (penSize + x), (int) (penSize + y));
							}
						}
					}
				});
		
		sandbox.setOnMousePressed(
				new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						for(int x = (int) (e.getX()-penSize); x <= (int) (e.getX()+penSize); x++) {
							for(int y = (int) (e.getY()-penSize); y <= (int) (e.getY()+penSize); y++) {
								addElementToScreen(currElem, (int) (penSize + x), (int) (penSize + y));
							}
						}
					}
				});
		//End Mouse input
		//Game loop
		new AnimationTimer() {//game loop
			public void handle(long currentNanoTime) {//game loop
				GraphicsContext gc = canvas.getGC();
				
				gc.setFill(Color.BLACK); //Clear the
				gc.fillRect(0, 0, width, height); // Screen
				
				elementText(canvas.getGC());
				
				//Loop through and paint/update all particles.
				for(int x = 0; x < width; x++) {
					for(int y = 0; y < height; y++) {
						Element tempElem = elemArray[x][y];
						if(tempElem != null) {
							tempElem.move(elemArray);
							paintElement(tempElem, gc);
						}
					}
				}
				
				
				
				if(input.contains("RIGHT") && keyTimer > 20) { //Cycle current element to the right
					if((elements.indexOf(currElem)+1) < elements.size()) {
						currElem = elements.get(elements.indexOf(currElem)+1);
					} else {
						currElem = elements.get(0);
					}
					keyTimer = 0; //reset keyTimer 
				}
				if(input.contains("LEFT") && keyTimer > 20) { //Cycle current element to the left
					if((elements.indexOf(currElem)-1) >= 0) {
						currElem = elements.get(elements.indexOf(currElem)-1);
					} else {
						currElem = elements.get(elements.size()-1);
					}
					keyTimer = 0; //reset keyTimer
				}
				
				keyTimer++; //Cycles the keyTimer up 1 per every frame.
				float elapsedTime = (System.nanoTime() - currentNanoTime)/1000000000f;
				float frameRate = 1f/elapsedTime;
				System.out.println(frameRate);
			}
		}.start();
		
		sandStage.show();
	}
	
	/**
	 * Initialize the elements in the game
	 */
	public void initElements() {
		Element E_WALL = new Element("Wall", Color.GREY, 0, 0, 100);
		elements.add(E_WALL);
		currElem = E_WALL;
		Element E_SAND = new Element("Sand", Color.TAN, .8, 1, 20);
		elements.add(E_SAND);
		Element E_WATER = new Element("Water", Color.BLUE, 1, .6, 1);
		elements.add(E_WATER);
	}
	
	public void paintElement(Element element, GraphicsContext gc) {
		gc.setFill(element.getColor());
		gc.fillRect(element.getX(), element.getY(), 1, 1);
	}
	
	public void elementText(GraphicsContext gc) {
		gc.setFont(new Font(20));
		gc.setFill(Color.WHITE);
		gc.fillText(currElem.getName(), 10, 20);
		
		gc.setFill(currElem.getColor());
		gc.fillRect(10, 30, 40, 20);
	}
	
	public void addElementToScreen(Element elem, int x, int y) {
		if(elemArray[x][y] == null) {
			Element element = new Element(elem.getName(), elem.getColor(), elem.getGravity(), elem.getSpread(), elem.getDensity());
			elemArray[x][y] = element;
			element.setX(x);
			element.setY(y);
			element.setRealX(x);
			element.setRealY(y);
			element.setOldX(x);
			element.setOldY(y);
		}
	}
}
