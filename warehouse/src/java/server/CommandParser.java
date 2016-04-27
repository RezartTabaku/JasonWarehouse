package server;

public class CommandParser {
	static int[] locationParser(String location){
		int[] coo = new int[2];
		if(location.equals("supplier")){
			coo[0] = 10;
			coo[1] = 10;
		}
		else{
			String[] a = location.split(",");
			coo[0] = Integer.parseInt(a[0]);
			coo[1] = Integer.parseInt(a[1]);
		}
		return coo;
	}
}
