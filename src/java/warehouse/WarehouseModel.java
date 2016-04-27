package warehouse;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import mvc.Agent;
import mvc.Model;

public class WarehouseModel extends Model{
//	public Agent Fridge = new Agent(2,"Fridge",2,2);
	public final static int OBSTACLE = 128;
	public final static int SUPPLIER = 64;
	public static final int EXIT = 32;
	
	public Location supplier = new Location(5,0);
	
	public WarehouseModel(int h, int w,int ags) {
		super(w,h,ags);
				
	}



	
}
