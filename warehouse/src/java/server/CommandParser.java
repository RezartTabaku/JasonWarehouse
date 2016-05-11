package server;

public class CommandParser {
	static int[] locationParser(String location){
		int[] coo = new int[2];
		if(location.equals("supplier")){
			coo[0] = 4;
			coo[1] = 2;
		}
		else if(location.equals("restArea")){
			coo[0] = 4;
			coo[1] = 4;
		}else{
			String[] a = location.split(",");
			coo[0] = Integer.parseInt(a[0]);
			coo[1] = Integer.parseInt(a[1]);
		}
		return coo;
	}
}
