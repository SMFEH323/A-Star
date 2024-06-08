
public class Tracker {
	private char[][] map;
	private int steps = 0;
	private boolean done = false;
	private int x = 0;
	private int y = 0;
	
	
	public Tracker(char[][] map) {
		this.map = map;
		for (int i = 0 ; i < map.length; i++) {
			for (int j = 0 ; j < map.length; j++) {
				if (map[i][j] == 'S') {
					x = i;
					y = j;
				}
			}
		}
	}
	
	//move 1 step towards direction.
	//direction 0, move up
	//direction 1, move right;
	//direction 2, move down;
	//direction 3, move left;
	//return true if goal is reached, otherwise return false;
	public boolean move(int direction) throws IllegalMoveException {
		int ti = x;
		int tj = y;
		if (direction == 0) ti--;
		if (direction == 1) tj++;
		if (direction == 2) ti++;
		if (direction == 3) tj--;
		 if (map[ti][tj] == 'W' || ti < 0 || tj < 0 || ti >= map.length || tj >= map.length) {
			throw new IllegalMoveException();
		} else if (map[ti][tj] == 'G') {
			steps++;
			x=ti;
			y=tj;
			done = true;
			return true;
		}else {
			map[ti][tj] = '#';
			steps++;
			x = ti;
			y = tj;
			return false;
			
		}
	}
	
	public int tally() {
		if (! done) return -1;
		return steps;
	}
}
