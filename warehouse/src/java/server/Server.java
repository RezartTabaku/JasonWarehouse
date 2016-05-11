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
		MoveManagerHuman.humanAni.getChildren().clear();
		MoveManagerHuman.finishedTraversal = false;
		MoveManagerHuman.humanAtDest = false;
		MoveManagerHuman.humanAni.setNode(V.Human);
		MoveManagerHuman.humanAni.setOnFinished(e->MoveManagerHuman.finishedTraversal = true);
		MoveManagerHuman.traverse(coo[0],coo[1] , M.AgHuman.x, M.AgHuman.y,'l') ;
		MoveManagerHuman.humanAni.play();

		while(!MoveManagerHuman.finishedTraversal);
		
//		
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y ,'d') ;
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y,'r') ;
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y,'u');
//		System.out.println("Finished traversing");

		
		V.setDest(-1,-1);
		return MoveManagerHuman.humanAtDest;
	}
	
	@Override
	public void sendButton() throws RemoteException {
		
		V.addTestButton();
		
	}
	@Override
	public boolean forkGetItem(String item) throws RemoteException {
		
		MoveManagerFork.carryItem(item);
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
		
		// Test Items
		M.ItemCollection.add("test");
		V = new View(M);
		
		return M;
		
	}

	@Override
	public boolean moveFork(String loc) throws RemoteException {
		int[] coo = CommandParser.locationParser(loc);
		System.out.println("Here");
		V.setDest(7,7);
		MoveManagerFork.finishedTraversal = false;
		MoveManagerFork.forkAtDest = false;
		MoveManagerFork.forkAni.setNode(V.Forklift);
		MoveManagerFork.forkAni.setOnFinished(e->{
			MoveManagerFork.finishedTraversal = true;
			System.out.println("Finished fork animation");
		});
		MoveManagerFork.traverse(coo[0], coo[1] , M.AgFork.x, M.AgFork.y,'l') ;
		MoveManagerFork.forkAni.play();

		while(!MoveManagerFork.finishedTraversal);
		
//		
//		if(!MoveManagerFork.forkAtDest) MoveManagerFork.traverse(coo[0], coo[1] , M.AgFork.x, M.AgFork.y ,'d') ;
//		if(!MoveManagerFork.forkAtDest) MoveManagerFork.traverse(coo[0], coo[1] , M.AgFork.x, M.AgFork.y,'r') ;
//		if(!MoveManagerFork.forkAtDest) MoveManagerFork.traverse(coo[0], coo[1] , M.AgFork.x, M.AgFork.y,'u');
//		System.out.println("Finished traversing");

		
		V.setDest(-1,-1);
		return MoveManagerFork.forkAtDest;
	}

	@Override
	public boolean humanGetItem(String item) throws RemoteException {
		MoveManagerHuman.carryItem(item);
		System.out.println("Obtained item");
		return true;
	}
	
}
