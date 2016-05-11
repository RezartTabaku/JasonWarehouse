package server;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MoveManagerFork {
	public static boolean forkAtDest = false;
	public static boolean finishedTraversal = false;
	public static boolean carrying = false;
	public static boolean[][] visited ;
	static SequentialTransition forkAni = new SequentialTransition();

	private static void addMove(Node N, char dir) {

		Animation a = new TranslateTransition();
		((TranslateTransition) a).setNode(N);
		((TranslateTransition) a).setDuration(Duration.millis(100));
		
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
		forkAni.getChildren().add(a);

	}

	public synchronized static void traverse(int Destx, int Desty, int x, int y, char dir) {
		/** Out of bounds condition */
		if (x <= 0 || x > Server.M.width || y > Server.M.height || y <= 0){
			return;
		}
			

		/** Occupied or already visited location */
		if ((Server.M.AgMap[x][y] != null && (Server.M.AgMap[x][y].getID() == -1 || Server.M.AgMap[x][y].getID() != 2))
				|| visited[x][y]){
			return;
		
		}
			
		addMove(Server.V.Forklift, dir);
		
		/** Arrived at destination */
		if (Desty == y && Destx == x) {
			System.out.println("Fork Arrived");
			resetVisited();
			Server.M.AgFork.x = x;
			Server.M.AgFork.y = y;
			MoveManagerFork.forkAtDest = true;
			return;
		}
		visited[x][y] = true;
		
		switch (dir) {
		case 'u':		

			traverse(Destx, Desty, x, y + 1, 'u');

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;


			if (!MoveManagerFork.forkAtDest)
				addMove(Server.V.Forklift, 'd');
			break;

		case 'd':


			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;


			if (!MoveManagerFork.forkAtDest)
				addMove(Server.V.Forklift, 'u');

			break;

		case 'l':


			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x, y + 1, 'u');

			} else
				return;

			if (!MoveManagerFork.forkAtDest)
				addMove(Server.V.Forklift, 'r');

			break;

		case 'r':
			
			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x, y + 1, 'u');

			} else
				return;

			if (!MoveManagerFork.forkAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManagerFork.forkAtDest)
				addMove(Server.V.Forklift, 'l');
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
				MoveManagerFork.visited[i][j] = false;
			}
		}
	}

	public static void carryItem(String item) {
		carrying = true;
		Platform.runLater(new Runnable(){
			public void run() {
				Server.V.Field.getChildren().remove(Server.V.ItemCollection.get(0));
				Server.V.Forklift.getChildren().add(Server.V.ItemCollection.get(0));
				
            }
			
		});
		
	}
}
