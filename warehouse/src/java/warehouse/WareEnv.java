package warehouse;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.Location;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mvc.Agent;
import mvc.Model;
import mvc.View;
import server.FXServer;
import server.Server;


public class WareEnv  extends Environment implements Runnable{
	public static final Literal at_supplier = Literal.parseLiteral("at_supplier");
	public static final Literal getItem = Literal.parseLiteral("get(item)");
	public static final Literal go = Literal.parseLiteral("go(supplier)");

	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(3000);
			updatePercepts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updatePercepts() throws RemoteException {
		System.out.println("Here");
		clearPercepts("human");
		clearPercepts("forklift");
		Agent human = M.AgHuman;
//		if(server.moveHuman("supplier")){
//			System.out.println("Arrived");
//		}
		if(human.sharespace(M.AgSupplier)){
			addPercept("human",at_supplier);
		}
		
	}
	
	public boolean executeAction(String ag , Structure action){
		
		boolean result = false;
		if(action.equals(getItem)){
			try {
				System.out.println("[" + ag + "] getting: " + action.getTerm(0).toString());
				result = server.getItem(action.getTerm(0).toString());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(action.equals(go)){
			try {
				System.out.println("[" + ag + "] going to: " + action.getTerm(0).toString());
				result = server.moveHuman(action.getTerm(0).toString());
			} catch (RemoteException e) {

				e.printStackTrace();
			}
		}

		return result;
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
}

