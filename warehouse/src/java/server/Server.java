package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import mvc.Agent;
import mvc.Model;
import mvc.View;

public class Server extends UnicastRemoteObject implements FXServer{
	public static Model M;
	public static View V;
	public boolean result = false;
//	public Scene S;
	public static boolean a = false;
	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	public String sayHello() {
//		a = true;
		return "Hello World " + a ;
	}
	public boolean showStage() throws RemoteException {
		System.out.println("Here");
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
	public boolean moveHuman(int x, int y) throws RemoteException {
		
		MoveManager.h.setNode(V.Human);
		MoveManager.h.setOnFinished(e->MoveManager.finishedTraversal = true);
		MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y,'l') ;
		MoveManager.h.play();

		while(!MoveManager.finishedTraversal);
		
//		
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y ,'d') ;
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y,'r') ;
//		if(!MoveManager.humanAtDest) MoveManager.traverse(x,y , M.AgHuman.x, M.AgHuman.y,'u');
//		System.out.println("Finished traversing");

		M.resetVisited();
		return MoveManager.humanAtDest;
	}
	
	@Override
	public void sendButton() throws RemoteException {
		
		V.addTestButton();
		
	}
	@Override
	public boolean getItem(String string) throws RemoteException {
		
		return false;
	}
	@Override
	public boolean setModel(int width, int height) throws RemoteException {
		M = new Model(width,height);
		M.addAgent(new Agent(1,"human",4,4));
		M.addAgent(new Agent(2,"forklift",5,4));
		M.addAgent(new Agent(3,"supplier",4,1));
		M.addObstacle(7,7);
		M.addObstacleArea(14, 3, 14, 14);
		V = new View(M);
		return true;
		
	}
	
}
