package path.sliding;

import java.util.ArrayList;
import java.util.List;

import core.BreadthFirstTree;
import core.DepthFirstGraph;
import path.PathSearchUtil;
import path.route.Location;
import path.route.Visualiser;
import util.Point;

public class Test {
	public static void main(String[] args) {
		final int nrLocations = 10;
		final int width = 10;
		final int height = 10;
		
		List<Location> locations = new ArrayList<>(nrLocations);
		for (int locationIndex = 0; locationIndex < nrLocations; locationIndex++) {
			Location location = new Location(Point.DoublePoint.createRandom2D(width, height));
			locations.add(location);
		}
		
		for (Location location : locations) {
			while ((double) location.getChildren().size() / 5 < Math.random()) {
				// Time to create a new link between this node and a random other node.
				int otherLocationIndex = (int) ((double) locations.size() * Math.random());
				location.link(locations.get(otherLocationIndex));
			}
		}
		
		Visualiser visualiser = new Visualiser(width, height, locations);
		visualiser.setVisible(true);
	}
	
	public static void testCompare() {
		State state1 = new State(3, 3);
		for (int step = 0; step < 50; step++) {
			state1 = state1.randomMove();
		}
		System.out.println(state1);
		
		State state2 = new State(3, 3);
		for (int step = 0; step < 50; step++) {
			state2 = state2.randomMove();
		}
		System.out.println("---------");
		System.out.println(state2);
		
		state1.cost = 10;
		state2.cost = 15;
		
		System.out.println(state1.determineHeuristicDistance());
		System.out.println(state2.determineHeuristicDistance());
		System.out.println(state1.compareTo(state2));
	}
	
	public static void testManhattanDistance() {
		State startState = new State(3, 3);
		System.out.println(startState);
		for (int step = 0; step < 50; step++) {
			startState = startState.randomMove();
		}
		
		System.out.println("Created scrambled state:");
		System.out.println(startState);
		System.out.println("Manhattan distance to goal state: "
				+ startState.determineManhattanDistance(new State(3, 3)));
	}
	
	public static void testAStar() {
		State startState = new State(3, 3);
		for (int step = 0; step < 50; step++) {
			startState = startState.randomMove();
		}
		
		// Reinitialise the start state as a copy of itself, to remove any unwanted extra information.
		startState = new State(startState);
		
		AStarTree aStarTree = new AStarTree(startState);
		while (aStarTree.next()) {
			
		}
	}
	
	public static void testDFS() {
		State startState = new State(3, 3);
		for (int step = 0; step < 15; step++) {
			startState = startState.randomMove();
		}
		
		// Reinitialise the start state as a copy of itself, to remove any unwanted extra information.
		startState = new State(startState);
		
		DepthFirstGraph depthFirstGraph = new DepthFirstGraph(startState);
		path.State goalState = (path.State) depthFirstGraph.run();
		PathSearchUtil.printPathToState(goalState);
	}
	
	public static void testBFS() {
		State startState = new State(3, 3);
		for (int step = 0; step < 15; step++) {
			startState = startState.randomMove();
		}
		
		BreadthFirstTree breadthFirstTree = new BreadthFirstTree(new State(startState));
		path.State goalState = (path.State) breadthFirstTree.run();
		PathSearchUtil.printPathToState(goalState);
	}
	
	public static void testCopy() {
		State startState = new State(3, 3);
		System.out.println(startState);
		for (int step = 0; step < 150; step++) {
			startState = startState.randomMove();
		}
		System.out.println("START STATE AFTER RANDOM MOVE");
		System.out.println(startState);
		State copyState = new State(startState);
		System.out.println("COPY STATE");
		System.out.println(copyState);
		for (int step = 0; step < 150; step++) {
			copyState = copyState.randomMove();
		}
		System.out.println("START STATE AFTER RANDOM MOVE IN COPY STATE");
		System.out.println(startState);
		System.out.println("COPY STATE AFTER RANDOM MOVE");
		System.out.println(copyState);
	}
	
	public static void testInit() {
		State startState = new State(3, 3);
		System.out.println(startState);
		System.out.println("scrambling...");
		
		for (int step = 0; step < 150; step++) {
			startState.randomMove();
			System.out.println(startState);
			System.out.println("---------");
		}
	}
}
