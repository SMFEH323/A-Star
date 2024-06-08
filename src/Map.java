import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {
	public static final int RANDOM_WALL = 0;
	public static final int HORIZONTAL_WALL = 1;
	public static final int ROOMS = 2;
	
	//read map from provided filename
	public static char[][] read(String filename){
		try {
			FileInputStream fstream = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			ArrayList<char[]> lines = new ArrayList<char[]>();
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  lines.add(strLine.toCharArray());
			}
			
			char[][] map = new char[lines.size()][lines.size()];
			for (int i = 0 ; i < lines.size(); i++) {
				for (int j = 0 ; j < lines.size(); j++) {
					map[i][j] = lines.get(i)[j];
				}
			}

			//Close the input stream
			fstream.close();
			
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//print map to console
	public static void printToConsole(char[][] map) {
		for (int i = 0 ; i < map.length; i++) {
			for (int j = 0 ; j < map.length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	
	//generate map, and save to file;
	//dimension: >= 20
	//difficulty: 0, 1, 2
	//layout: 0, 1, 2
	public static char[][] generate(int dimension, int difficulty, int layout, String file){
		try {
			if (dimension < 20) dimension = 20;
			if (layout < 0 )layout = 0;
			else if (layout > 2) layout = 2;
			if (difficulty < 0) difficulty = 0;
			else if (difficulty > 2) difficulty = 2;
			char[][] map = new char[dimension][dimension];
			if (layout == 0) {
				layout_0(dimension, difficulty, map);
			} else if (layout == 1) {
				layout_1(dimension, difficulty, map);
			}  else if (layout == 2) {
				layout_2(dimension, difficulty, map);
			}
			
			FileWriter f = new FileWriter(file);
			for (int i = 0 ; i < dimension; i++) {
				for (int j = 0 ; j < dimension; j++) {
					f.write(map[i][j]);
				}
				f.write("\n");
			}
			f.close();
			return map;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static void layout_0(int dimension, int difficulty, char[][] map){
		
		for (int i = 0 ; i < dimension; i++) {
			for (int j = 0 ; j < dimension; j++) {
				double r =(Math.random() * 12);
				if (r < difficulty+1) {
					map[i][j] = 'W';
				} else {
					map[i][j] = '*';
				}
				
			}
		}
		map[1][1] = 'S';
		map[dimension-2][dimension-2] = 'G';
	}
	
	private static void layout_1(int dimension, int difficulty, char[][] map){
		for (int i = 0 ; i < dimension; i++) {
			for (int j = 0 ; j < dimension; j++) {
				
				if (i % 2 == 1) {
					map[i][j] = 'W';
				} else {
					map[i][j] = '*';
				}
				
			}
		}
		for (int i = 0 ; i < dimension; i++) {
			if (i%2 == 1) {
				for (int k = 0; k < difficulty; k++) {
					int r = (int)(Math.random()*dimension);
					map[i][r] = '*';
				}
			}
			
		}
		map[0][dimension/2] = 'S';
		map[dimension-1][dimension/2] = 'G';
	}
	
	private static void layout_2(int dimension, int difficulty, char[][] map){
		int roomSize = 8-difficulty*2;
		for (int i = 0 ; i < dimension; i++) {
			for (int j = 0 ; j < dimension; j++) {
				
				if (i % roomSize == 0) {
					map[i][j] = 'W';
				} else if (j %roomSize == 0){
					map[i][j] = 'W';
				}else {
					
					map[i][j] = '*';
				}
				
			}
		}
		for (int i = 0 ; i < dimension; i++) {
			if (i%roomSize == 0) {
				for (int j = 0 ; j < dimension; j++) {
					if (j%roomSize == 0) {
						int r =1+(int)(Math.random()*roomSize-1);
						
						if (j+r < dimension) map[i][j+r] = '*';
						else map[i][dimension-1] = '*';
					}
				}
			}
			
		}
		
		for (int j = 0 ; j < dimension; j++) {
			if (j%roomSize == 0) {
				for (int i = 0 ; i < dimension; i++) {
					if (i == 0 || i%roomSize == 0) {
						int r =1+(int)(Math.random()*roomSize-1);
						if (i+r < dimension) map[i+r][j] = '*';
						else map[dimension-1][j] = '*';
					}
				}
			}
			
		}
		map[1][1] = 'S';
		map[dimension-2][dimension-2] = 'G';
	}
	
	
}
