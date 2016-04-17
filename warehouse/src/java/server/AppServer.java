package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppServer extends Application{
	public static void main(String[] rez) throws RemoteException {
		launch(rez);
	}

	public void showStage(Stage S) throws RemoteException{
		try {
			start(S);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void start(Stage S) throws Exception {
		Registry reg = LocateRegistry.createRegistry(5099);
		reg.rebind("hello", new Server());
		while(true)
		{
			if(Server.M!=null){
				Thread.sleep(2000);
				System.out.println(Server.V.getScene().getHeight());
				S.setScene(Server.V.getScene());
				S.show();

				break;
				}
			else{ 
				System.out.println("Waiting for client");
				Thread.sleep(4000);
			}
		}
		
	}
}
