package server;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MoveManager {
	public static boolean humanAtDest = false;
	public static boolean forkliftAtDest = false;
	public static boolean finishedTraversal = false;

	static SequentialTransition h = new SequentialTransition();

	private static void translateLeft(Node N) {

		N.setLayoutX(N.getLayoutX() - Server.V.BlockW);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void translateRight(Node N) {
		N.setLayoutX(N.getLayoutX() + Server.V.BlockW);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void translateUp(Node N) {
		N.setLayoutY(N.getLayoutY() - Server.V.BlockH);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void translateDown(Node N) {
		N.setLayoutY(N.getLayoutY() + Server.V.BlockH);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
		h.getChildren().add(a);

	}

	public synchronized static void traverse(int Destx, int Desty, int x, int y, char dir) {

		// h.setByX(0);
		// h.setByY(0);
		/** Out of bounds condition */
		if (x <= 0 || x > Server.M.width || y > Server.M.height || y <= 0)
			return;

		/** Occupied or already visited location */
		if ((Server.M.AgMap[x][y] != null && (Server.M.AgMap[x][y].getID() == -1 || Server.M.AgMap[x][y].getID() > 1))
				|| Server.M.visited[x][y])
			return;
		
		addMove(Server.V.Human, dir);
		/** Arrived at destination */
		if (Desty == y && Destx == x) {
			System.out.println("Arrived");
			MoveManager.humanAtDest = true;
			return;
		}
		Server.M.visited[x][y] = true;
		
		switch (dir) {
		case 'u':		

			traverse(Destx, Desty, x, y + 1, 'u');

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;


			if (!MoveManager.humanAtDest)
				addMove(Server.V.Human, 'd');
			break;

		case 'd':
			System.out.println(x + " " + y);

			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;


			if (!MoveManager.humanAtDest)
				addMove(Server.V.Human, 'u');

			break;

		case 'l':
			System.out.println(x + " " + y);

			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x - 1, y, 'l');

			} else
				return;

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x, y + 1, 'u');

			} else
				return;

			if (!MoveManager.humanAtDest)
				addMove(Server.V.Human, 'r');

			break;

		case 'r':
			System.out.println(x + " " + y);

			traverse(Destx, Desty, x, y - 1, 'd');

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x, y + 1, 'u');

			} else
				return;

			if (!MoveManager.humanAtDest) {
				traverse(Destx, Desty, x + 1, y, 'r');

			} else
				return;

			if (!MoveManager.humanAtDest)
				addMove(Server.V.Human, 'l');
			break;

		default:
			System.exit(1);
			break;
		}
		return;
	}
}
