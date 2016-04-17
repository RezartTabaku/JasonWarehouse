package mvc;

import java.io.Serializable;
import java.util.ArrayList;


public class Model extends World implements Serializable{
	
	public ArrayList<Agent> AgList;
	public static int height = 30;		// Height and width of the grid (in cells)
	public static int width  = 30;
	
	public Agent[][] AgMap;
	public boolean[][] visited ;
	public Agent AgHuman;
	
	public Model() {
		super(height * BlockH  , width * BlockW);
		AgList = new ArrayList<Agent>();
	}
	
	/* Create a h by w field */
	public Model( int h, int w) {
		super(h * BlockH  , w * BlockW);	// Create 'World' with height and width
		this.height = h;
		this.width = w;
		
		AgList = new ArrayList<Agent>();
		AgMap = new Agent[h+1][w+1];
		visited = new boolean[h+1][w+1];
		
	}

	public Model(int w, int h, int ags) {
		super(height * BlockH  , width * BlockW);
		AgList = new ArrayList<Agent>();
		for(int i = 0 ; i < ags ; i++){
			AgList.add(new Agent(1,"human",(int)Math.random()*w+1,(int)Math.random()*h+1));
		}
	}
	public void resetVisited(){
		for(int i = 1; i <= height; i++){
			for(int j = 1; j <= width; j++){
				visited[i][j] = false;
			}
		}
		System.out.println("Reset visited");
	}
	public void addAgent(Agent a){
		try{
			if(a.x >= width || a.y >= height || a.x<=0 || a.y <= 0)
				throw new Exception();
			AgList.add(a);
			if(a.getType().equals("human")){
				AgHuman = a;
			}
			AgMap[a.x][a.y] = a;
		}
		catch(Exception e){
			System.out.println(a.getType() + " has been initialized out of bounds.");
		}
		
	}
	
	public void addObstacle(int x , int y){
		try{
			if( x >= width || y >= height || x <= 0 || y <= 0)
				throw new Exception("Obstacle out of bounds");
			AgList.add(new Agent(-1,"obstacle",x,y));
			AgMap[x][y] = new Agent(-1,"obstacle",x,y);
		}
		catch(Exception e){
			e.toString();
		}
		
	}
	
	public void addObstacleArea(int UX, int UY, int LX, int LY){
		try{
			if(UX >= width || LX >= width || LY >= height || UY >= height || UX <= 0 || UY <= 0 || LX <= 0 || LY <= 0 )
				throw new Exception("Obstacle out of bounds");
			for(int i = LX; i <= UX ; i++){
				for(int j = UY ; j <= LY ; j ++){
					AgList.add(new Agent(-1,"obstacle",i,j));
					AgMap[i][j] = new Agent(-1,"obstacle",i,j);
				}
			}
		}
		catch(Exception e){
			e.toString();
		}
		
	}
	
	public void addAgents(Agent...a){
		for(Agent ag : a){
			try{
				if(ag.x>width || ag.y > height || ag.x<=0 || ag.y <=0)
					throw new Exception();
				AgList.add(ag);
				AgMap[ag.x][ag.y] = ag;
			}
			catch(Exception e){
				System.out.println(ag.getType() + " has been initialized out of bounds.");
			}
		}
	}
	
	public Agent getAgent(int index){
		return AgList.get(index);
	}
}