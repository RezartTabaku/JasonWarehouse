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
import mvc.Model;
import server.FXServer;


public class WareEnv  extends Environment implements Runnable{
	public static final Literal at_supplier = Literal.parseLiteral("at_supplier");
	public static final Literal getItem = Literal.parseLiteral("get(item)");
	
	FXServer server;
	
	public void init(String[] Rez){
		try {
				server = (FXServer) Naming.lookup("rmi://localhost:5099/hello");
				if(server.handshake()){
					if(Rez.length>0){
					if(!server.setModel(Integer.parseInt(Rez[0]), Integer.parseInt(Rez[1])))
						System.exit(1);;
//					server.sendButton();
				}
					else System.exit(1);
				}

		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(5000);
			updatePercepts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updatePercepts() throws RemoteException {
		System.out.println("Here");
		clearPercepts("robot");
		clearPercepts("owner");
		if(!server.moveHuman(10, 10)){
			System.out.println("The Human reached the destination");
		}
//		Location human = model.getAgPos(0);
//		
//		if(human.equals(model.supplier)){
//			addPercept("human",at_supplier);
//		}
		
	}
	
	public boolean executeAction(String ag , Structure action){
		System.out.println("[" + ag + "] doing: " + action);
		boolean result = false;
		if(action.equals(getItem)){
			try {
				result = server.getItem(action.getTerm(0).toString());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		else if(action.equals(clf)){
//			result = model.closeFridge();
//		}
//		else if(action.getFunctor().equals("go")){
//			String l = action.getTerm(0).toString();
//			Location dest = null;
//			if(l.equals("fridge")){
//				dest = model.lFridge;
//			} else if(l.equals("owner"))
//				dest = model.lOwner;
//			try{
//				result = model.moveTowards(dest);
//				
//			} catch (Exception e){
//				e.printStackTrace();
//			}
//		}else if(action.equals(gb)){
//			result = model.getBeer();
//		}
//		else if(action.equals(hb)){
//			result = model.handInBeer();
//		}
//		else if(action.equals(sb)){
//			result = model.sipBeer();
//		}
//		else if(action.getFunctor().equals("deliver")){
//			try{
//				Thread.sleep(4000);
//				result = model.addBeer( (int)((NumberTerm)action.getTerm(1)).solve());
//			} catch (Exception e){
//				e.printStackTrace();
//			}
//		}
//		else System.out.println("Could not deliver" + action.toString());
//		if(result){
//			updatePercepts();
//			try{
//				Thread.sleep(100);
//			} catch (Exception e ){}
//		}
		return result;
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
}

