package warehouse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvc.Agent;
import mvc.Model;
import mvc.View;

public class wgas extends Application{
	
	 static Model M ;
	 static View V ;
	
	public static Model getM() {
		return M;
	}

	public static void setM(Model m) {
		M = m;
	}

	public static View getV() {
		return V;
	}

	public static void setV(View v) {
		V = v;
	}

	@Override
	public void start(Stage S) throws Exception {
		V.draw();
		M.setScene(V.getView());

		S.show();
		
	}
	
	public static void main(){
		launch();
	}

}
