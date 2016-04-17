package mvc;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import warehouse.WarehouseModel;

public class View extends World {
	public Scene S ;
	public StackPane View;
	
	public Group Field = new Group();
	public Group ControlPanel = new Group();
	public Model Environment;
	public Group Human,Forklift,Supplier;
	private int adjuster = (int) (BlockW * 0.2);
	public Font font = new Font("Arial",adjuster);
	
	public View(Model M){
		super();
		Environment = M;
		HEIGHT = M.height*BlockH;
		WIDTH = M.width*BlockW;
		draw();
	}

	/* Starts the visualisation*/
	public void draw(){
		drawBorders();
		drawLines();
		drawAgents();
		super.setScene(getView());
	}
	
	public void drawAgents(){
		for(Agent a : Environment.AgList){
			switch(a.getType()){
			case "obstacle":	
				Rectangle ob = new Rectangle();
				ob.setHeight(BlockW);
				ob.setWidth(BlockH);
				ob.setX((a.x - 1)*BlockH );
				ob.setY((Environment.height - a.y)*BlockH);
				ob.setFill(Color.RED);
				Field.getChildren().addAll(ob);
				break;
			case "human":
				Human = new Group();
				Ellipse human = new Ellipse();
				human.setRadiusX(BlockW/2);
				human.setRadiusY(BlockH/2);
				human.setCenterX(a.x * BlockW + BlockW/2 );
				human.setCenterY((Environment.height - a.y) * BlockH + BlockH/2 );
				human.setFill(Color.GREY);
				Text T = new Text(a.getType());
				T.setX(human.getCenterX() - adjuster);
				T.setY(human.getCenterY() );
				T.setFont(font);
				Human.getChildren().addAll(human,T);
				Field.getChildren().addAll(Human);
				break;
			case "forklift":
				Forklift = new Group();
				Ellipse forklift = new Ellipse();
				forklift.setRadiusX(BlockW/2);
				forklift.setRadiusY(BlockH/2);
				forklift.setCenterX(a.x * BlockW + BlockW/2 );
				forklift.setCenterY((Environment.height - a.y) * BlockH + BlockH/2 );
				forklift.setFill(Color.YELLOW);
				Text t = new Text(a.getType());
				t.setX(forklift.getCenterX() - adjuster);
				t.setY(forklift.getCenterY() );
				t.setFont(font);
				Forklift.getChildren().addAll(forklift,t);
				Field.getChildren().addAll(Forklift);
				break;
			case "supplier":
				Supplier = new Group();
				Rectangle supplier= new Rectangle();
				supplier.setHeight(BlockH);
				supplier.setWidth(BlockW);
				supplier.setX(a.x * BlockW);
				supplier.setY((Environment.height - a.y ) * BlockH);
				supplier.setFill(Color.GREEN);
				Text supplierText = new Text(a.getType());
				supplierText.setX(supplier.getX() + adjuster);
				supplierText.setY(supplier.getY() + 2*adjuster );
				supplierText.setFont(font);
				Supplier.getChildren().addAll(supplier,supplierText);
				Field.getChildren().addAll(Supplier);
				break;
			default:
				Ellipse ag = new Ellipse();
				ag.setRadiusX(BlockW/2);
				ag.setRadiusY(BlockH/2);
				ag.setCenterX(a.x * BlockW - BlockW/2 );
				ag.setCenterY((Environment.height - a.y) * BlockH + BlockH/2 );
				ag.setFill(Color.PINK);
				Text k = new Text(a.getType());
				k.setX(ag.getCenterX() - adjuster);
				k.setY(ag.getCenterY() );
				k.setFont(font);
				Field.getChildren().addAll(ag,k);
				break;
			}
	}
	}
	public void drawBorders(){

		Line up    = new Line(0,0,WIDTH,   0  );
		Line down  = new Line(0,HEIGHT,WIDTH,  HEIGHT  );
		Line left  = new Line(0,0,0,HEIGHT);
		Line right = new Line(WIDTH,0,WIDTH,   HEIGHT);
		Field.getChildren().addAll(up,down,left,right);

		
	}
	
	public StackPane getView(){
		Human.setManaged(false);
		if(View==null){
			View = new StackPane();
			View.getChildren().add(Field);
//			Field.setTranslateX(PADDER/2);
//			Field.setTranslateY(PADDER/2);
//			View.getChildren().add(ControlPanel);
			
			}

		return View;
	}
	
	
	/* Draws the grid lines*/
	public void drawLines(){
		for(int i = 1 ; i < Environment.height ; i++){
			Line l = new Line(0,i*BlockH ,WIDTH,   i * BlockH  );
			Field.getChildren().add(l);
		}

		for(int i = 1 ; i < Environment.width ; i++){
			Line l = new Line(i*BlockW ,0,   i*BlockW , HEIGHT);

			Field.getChildren().add(l);
		}
		
	}
	
	public void addTestButton(){
		if(ControlPanel == null)
			ControlPanel = new Group();
		Button button = new Button("Test");
		
		ControlPanel.getChildren().add(button);
	}
	
	
}
