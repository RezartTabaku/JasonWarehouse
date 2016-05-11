package server;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MoveManagerHuman {
	public static boolean humanAtDest = false;
	public static boolean forkliftAtDest = false;
	public static boolean finishedTraversal = false;
	public static boolean[][] visited ;
	static SequentialTransition humanAni = new SequentialTransition();
	private static boolean carrying;
	


	private static void addMove(Node N, char dir) {

		Animation a = new TranslateTransition();
		((TranslateTransition) a).setNode(N);
		((TranslateTransition) a).setDuration(Duration.millis(2000));
		switch (dir) {
			case 'u':
				((TranslateTransition) a).setByY(0 - Server.V.BlockH);
				break;
			case 'd':
				((TranslateTransition) a).setByY(Server.V.BlockH);
				break;
			case 'l':
				((TranslateTransition) a).setByX(0 - Server.V.BlockW);
				break;
			case 'r':
				((TranslateTransition) a).setByX(Server.V.BlockW);
				break;
			default:
				System.exit(1);
			}
		humanAni.getChildren().add(a);

	}

	public synchronized static void traverse(int Destx, int Desty, int x, int y, char dir) {
		System.out.println(x +" "+ y);
		/** Out of bounds condition */
		if (x <= 0 || x > Server.M.width || y > Server.M.height || y <= 0)
			return;

		/** Occupied or already visited location */
		if ((Server.M.AgMap[x][y] != null && (Server.M.AgMap[x][y].getID() == -1 || Server.M.AgMap[x][y].getID() > 1))
				|| visited[x][y])
			return;
		
		addMove(Server.V.Human, dir);
		
		/** Arrived at destination; update location */
		if (Desty == y && Destx == x) {
			System.out.println("Human Arrived");
			resetVisited();
			updateLocation(x,y);
			MoveManagerHuman.humanAtDest = true;
			return;
		}
		
		visited[x][y] = true;
		
		switch (dir) {
		case 'u':		

			traverse(Destx, Desty, x, y + 1, 'u');

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;


			if (!MoveManagerHuman.humanAtDest)
				addMove(Server.V.Human, 'd');
			break;

		case 'd':

			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;


			if (!MoveManagerHuman.humanAtDest)
				addMove(Server.V.Human, 'u');

			break;

		case 'l':

			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x, y + 1, 'u');

			} else
				return;

			if (!MoveManagerHuman.humanAtDest)
				addMove(Server.V.Human, 'r');

			break;

		case 'r':

			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x, y + 1, 'u');

			} else
				return;

			if (!MoveManagerHuman.humanAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManagerHuman.humanAtDest)
				addMove(Server.V.Human, 'l');
			break;

		default:
			System.exit(1);
			break;
		}
		return;
	}
	
	public static void resetVisited(){
		for(int i = 1; i <= Server.M.height; i++){
			for(int j = 1; j <= Server.M.width; j++){
				MoveManagerHuman.visited[i][j] = false;
			}
		}
	}
	
	public static void carryItem(String item) {
		carrying = true;
		try {
			Thread.sleep(5000);
		} catch (Exception e) {

			e.printStackTrace();
		}
		TranslateTransition carry = new TranslateTransition();
		carry.setNode(Server.V.ItemCollection.get(0));
		carry.setDuration(Duration.millis(5000));
		carry.setByY(-Server.V.BlockH);
		carry.play();
		carry.setOnFinished(e->{
				Platform.runLater(new Runnable(){
				public void run() {
					Server.V.Field.getChildren().remove(Server.V.ItemCollection.get(0));
					Server.V.Human.getChildren().add(Server.V.ItemCollection.get(0));
					Server.V.ItemCollection.get(0).setTranslateY(-3*Server.V.BlockH);
					Server.V.ItemCollection.get(0).setTranslateX(Server.V.BlockH);				
	            }
				});
			});
		
		
	}
	
	public static void updateLocation(int x , int y){
		Server.M.AgMap[Server.M.AgHuman.x][Server.M.AgHuman.y] = null;
		Server.M.AgHuman.x = x;
		Server.M.AgHuman.y = y;
		Server.M.AgMap[Server.M.AgHuman.x][Server.M.AgHuman.y] = Server.M.AgHuman;
	}
}
