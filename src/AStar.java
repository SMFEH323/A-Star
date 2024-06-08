import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
	private Tracker tracker;
	private char[][] map;
	private int[] start_;
	private int[] goal_;

	public AStar(char[][] map) {
		tracker = new Tracker(map);
		this.map = map;
		start_ = new int[2];
		goal_ = new int[2];
		int count = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'S') {
					start_[0] = i;
					start_[1] = j;
					count++;
				} else if (map[i][j] == 'G') {
					goal_[0] = i;
					goal_[1] = j;
					count++;
				}

				if (count == 2) {
					break;
				}
			}
		}
	}

	public int stop() {
		return tracker.tally();
	}

	// your function for running the A* algorithm
	// don't put all code in this one method
	// use helper functions
	public void start() {
		try {

			// use tracker.move() to move up - 0, right - 1,
			// down - 2 and left - 3.
//			tracker.move(1);
//			tracker.move(1);
//			tracker.move(1);
//			tracker.move(1);
//			tracker.move(1);
//			tracker.move(1);

			List<int[]> directions = getPath();

			for (int i = 0; i < directions.size(); i++) {
				int[] current = directions.get(i);
				int[] next = directions.get(i + 1);
				if (current[0] < next[0]) {
					tracker.move(2);
				} else if (current[0] > next[0]) {
					tracker.move(0);
				} else if (current[1] < next[1]) {
					tracker.move(1);
				} else {
					tracker.move(3);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Node implements Comparable<Node> {
		private int[] location_;
		private int gScore_;
		private int fScore_;

		private Node(int[] location, int gScore) {
			location_ = location;
			gScore_ = gScore;
			fScore_ = gScore_ + manhattanDistance(location_);
		}

		public int compareTo(Node other) {
			return Integer.compare(fScore_, other.fScore_);
		}

	}

	private int manhattanDistance(int[] current) {
		return Math.abs(current[0] - goal_[0]) + Math.abs(current[1] - goal_[1]);
	}

	private boolean isValidLocation(int[] location) {
		int row = location[0];
		int col = location[1];
		if (row < 0 || row >= map.length || col < 0 || col >= map[0].length || map[row][col] == 'W') {
			return false;
		}
		return true;
	}

	private List<int[]> getPath() {
		PriorityQueue<Node> discovered = new PriorityQueue<Node>();
		HashMap<String, Integer> gScoreMap = new HashMap<String, Integer>();
		HashMap<String, int[]> parentMap = new HashMap<String, int[]>();
		HashSet<String> closedList = new HashSet<String>();
		List<int[]> path = new ArrayList<>();

		Node startNode = new Node(start_, 0);
		discovered.add(startNode);
		parentMap.put(startNode.location_[0] + "," + startNode.location_[1], null);
		gScoreMap.put(startNode.location_[0] + "," + startNode.location_[1], 0);

		while (!discovered.isEmpty()) {
			// remove node with lowest fScore from open list
			Node current = discovered.remove();
			int[] location = current.location_;

			// check if goal has been reached
			if (map[location[0]][location[1]] == 'G') {
				// construct path by following parent pointers
				int[] way = location;
				path.add(goal_);
				while (parentMap.get(way[0] + "," + way[1]) != null) {
					int[] parent = parentMap.get(way[0] + "," + way[1]);
					path.add(parent);
					way = parent;
				}
				Collections.reverse(path);
				break;
			}

			// add current node to closed list
			closedList.add(location[0] + "," + location[1]);

			// generate successors of current node
			int[][] successors = { { location[0] - 1, location[1] }, { location[0], location[1] + 1 },
					{ location[0] + 1, location[1] }, { location[0], location[1] - 1 } };
			for (int[] successor : successors) {
				// check if successor is valid
				if (isValidLocation(successor)) {
					String locationString = successor[0] + "," + successor[1];
					// check if successor is already in closed list
					if (!closedList.contains(locationString)) {
						int gScore = gScoreMap.get(location[0] + "," + location[1]) + 1;
						// check if successor is already in open list
						boolean inOpenList = false;
						for (Node node : discovered) {
							if (node.location_[0] == successor[0] && node.location_[1] == successor[1]) {
								inOpenList = true;
								if (gScore < gScoreMap.get(successor[0] + "," + successor[1])) {
									gScoreMap.put(successor[0] + "," + successor[1], gScore);
									parentMap.put(successor[0] + "," + successor[1], location);
									node.gScore_ = gScore;
									node.fScore_ = gScore + manhattanDistance(successor);
									// re-sort open list since priority of this node has changed
									PriorityQueue<Node> temp = new PriorityQueue<Node>(discovered);
									discovered.clear();
									discovered.addAll(temp);
								}
								break;
							}
						}
						// if successor is not in open list, add it
						if (!inOpenList) {
							gScoreMap.put(successor[0] + "," + successor[1], gScore);
							parentMap.put(successor[0] + "," + successor[1], location);
							Node successorNode = new Node(successor, gScore);
							discovered.add(successorNode);
						}
					}
				}
			}
		}
		return path;
	}

}
