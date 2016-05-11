package mvc;

import java.io.Serializable;

public class Agent implements AgentLocation, Serializable{
	private int ID;
	private String type = "Test";
	public int x;
	public int y;
	
	public Agent(int iD) {
		super();
		ID = iD;
		x = 1;
		y = 1;
		type ="Test"+iD;
	}

	public Agent(int id ,String type, int x, int y) {
		super();
		ID = id;
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public Agent( int id ,int x, int y) {
		super();
		ID = id;
		this.x = x;
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean sharespace(Agent a) {
		if(this.x == a.x && this.y == a.y) return true;
		return false;
	}
	
	
}
