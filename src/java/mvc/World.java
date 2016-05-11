package mvc;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class World  {

	private Scene stage ;
	public double HEIGHT = 700;
	public double WIDTH  = 500;
	public int    PADDER = 50;				// Value to pad the screen
	
	public static int    BlockH  = 35;	   // Size of the grid cell
	public static int    BlockW  = 35;	   
	
	public World(){
		super();
	}
	
	public World(double height, double width) {
		super();
		
		HEIGHT = height;
		WIDTH = width;
		
	}
	public World(String title ,double hEIGHT, double width) {
		super();
		HEIGHT = hEIGHT;
		WIDTH = width;
	}
	
	public void setScene(Parent S){

		stage = new Scene(S,HEIGHT+PADDER,WIDTH+PADDER);
	}
	
	public Scene getScene(){
		return stage;
	}
	
	
	public int getBlockH() {
		return BlockH;
	}
	public void setBlockH(int bLOCK) {
		BlockH = bLOCK;
	}
	
	public int getBlockW() {
		return BlockW;
	}
	public void setBlockW(int bLOCK) {
		BlockW = bLOCK;
	}
}
