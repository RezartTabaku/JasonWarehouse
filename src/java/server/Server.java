package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import mvc.Agent;
import mvc.Model;
import mvc.View;

public class Server extends UnicastRemoteObject implements FXServer{
	public static Model M;
	public static View V;
	public boolean result = false;

	public static boolean a = false;
	protected Server() throws RemoteException {
		super();

	}

	public boolean showStage() {
		System.out.println("Creating view..");
		a = true;
		return true;
	}
	
	@Override
	public boolean handshake() throws RemoteException {
		try{
			a = true;
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean moveHuman(String location) throws RemoteException {
		int[] coo = CommandParser.locationParser(location);
		System.out.println("Here");
		V.setDest(coo[0],coo[1]);
		MoveManager.h.setNode(V.Human);
		MoveManager.h.setOnFinished(e->MoveManager.finishedTraversal = true);
		MoveManager.traverse(coo[0],coo[1] , M.AgHuman.x, M.AgHuman.y,'l') ;
		MoveManager.h.play();

		while(!MoveManager.finishedTraversal);
		
//		
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y ,'d') ;
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y,'r') ;
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y,'u');
//		System.out.println("Finished traversing");

		M.resetVisited();
		V.setDest(-1,-1);
		return MoveManager.humanAtDest;
	}
	
	@Override
	public void sendButton() throws RemoteException {
		
		V.addTestButton();
		
	}
	@Override
	public boolean getItem(String string) throws RemoteException {
		System.out.println("Obtained item");
		//TODO Implement item to be carried
		return true;
	}
	@Override
	public Model setModel(int width, int height) throws RemoteException {
		M = new Model(width,height);
		M.addAgent(new Agent(1,"human",4,4));
		M.addAgent(new Agent(2,"forklift",5,4));
		M.addAgent(new Agent(3,"supplier",4,1));
		
		// Test obstacles
		M.addObstacle(7,7);
		M.addObstacleArea(14, 3, 14, 14);
		
		V = new View(M);
		
		return M;
		
	}
	
}
