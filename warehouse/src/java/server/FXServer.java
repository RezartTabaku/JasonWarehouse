package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import mvc.Model;

public interface FXServer extends Remote {
	public boolean handshake() throws RemoteException;
	public boolean showStage() throws RemoteException;
	public boolean moveHuman(String location) throws RemoteException;
	public void sendButton() throws RemoteException;
	public boolean forkGetItem(String string)throws RemoteException;
	public boolean humanGetItem(String string)throws RemoteException;
	public Model setModel(int width, int height) throws RemoteException;
	public boolean moveFork(String loc) throws RemoteException;
}
