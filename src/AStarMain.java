
public class AStarMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Generate map with dimension, difficulty, layout, file name
		//dimension: >= 20
		//3 level of difficulty: 0, 1, 2
		//3 different layout: 0, 1, 2
		//since each map is random,
		//it is important to save your map 
		//so that you can keep a record if something is wrong.
		char[][] map = Map.generate(20, 2, 2, "temp");
		
		//read map from a saved map file
		char[][] map2 = Map.read("temp");
		
		//print map to console
		Map.printToConsole(map);
		
		AStar as = new AStar(map);
		as.start();
		
		System.out.println("This is your path:");
		Map.printToConsole(map);
		System.out.println("Your total moves are: "+as.stop());
		
	}

}
