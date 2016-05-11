package warehouse;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mvc.*;
import server.*;


public class WareEnv  extends Environment implements Runnable{
	public static final Literal at_supplier = Literal.parseLiteral("staying_in(supplier)");
	public static final Literal at_rest = Literal.parseLiteral("staying_in(restArea)");
	public static final Literal getItem = Literal.parseLiteral("get(item)");
	public static final Literal go_supplier = Literal.parseLiteral("go(supplier)");
	
	
	FXServer server;
	Model M ;
	View V;
	public void init(String[] Rez){
		try {
				server = (FXServer) Naming.lookup("rmi://localhost:5099/hello");
				if(server.handshake()){
					M = server.setModel(Integer.parseInt(Rez[0]), Integer.parseInt(Rez[1]));
				}else System.exit(1);

		} 
		catch (Exception e) {
		
			e.printStackTrace();
		}

		try {
			Thread.sleep(3000);
			updatePercepts();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	private void updatePercepts() throws RemoteException {
		
		clearPercepts("human");
		clearPercepts("forklift");
		
//		if(server.moveHuman("supplier")){
//			System.out.println("Arrived");
//		}

		
	}
	
	public boolean executeAction(String ag , Structure action){
		
		boolean result = false;
		try {
		if(action.equals(getItem)){
			
				System.out.println("[" + ag + "] getting: " + action.getTerm(0).toString());
				if(ag.equals("human")){
					result = server.humanGetItem(action.getTerm(0).toString());

				} else {
					result = server.forkGetItem(action.getTerm(0).toString());

				}
				Thread.sleep(5000);
			
		}
		else if(action.getFunctor().equals("move_to")){
			
				System.out.println("[" + ag + "] going to: " + action.getTerm(0).toString());
				if(ag.equals("forklift")){
					result = server.moveFork(action.getTerm(0).toString());
					
				}
				else {
					result = server.moveHuman(action.getTerm(0).toString());
					if(result){
						clearPercepts("human");
					}
				}

		}
		if(result){
				updatePercepts();
				Thread.sleep(100);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void run() {
	
		
	}
}

