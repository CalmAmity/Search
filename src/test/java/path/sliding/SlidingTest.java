package path.sliding;

import core.BreadthFirstTree;
import core.DepthFirstGraph;
import org.junit.Test;
import path.AStarTree;
import path.PathSearchUtil;

public class SlidingTest {
	@Test
	public void bfs() {
		State startState = new State(3, 3);
		for (int step = 0; step < 15; step++) {
			startState = startState.randomMove();
		}
		
		// Reinitialise the start state as a copy of itself, to remove any unwanted extra information.
		startState = new State(startState);
		
		BreadthFirstTree<State> breadthFirstTree = new BreadthFirstTree<>(new State(startState));
		State goalState = breadthFirstTree.run();
		PathSearchUtil.printPathToState(goalState);
	}
	
	@Test
	public void dfs() {
		State startState = new State(3, 3);
		for (int step = 0; step < 15; step++) {
			startState = startState.randomMove();
		}
		
		// Reinitialise the start state as a copy of itself, to remove any unwanted extra information.
		startState = new State(startState);
		
		DepthFirstGraph<State> depthFirstGraph = new DepthFirstGraph<>(startState);
		path.State<State> goalState = depthFirstGraph.run();
		PathSearchUtil.printPathToState(goalState);
	}
	
	@Test
	public void aStar() {
		State startState = new State(3, 3);
		for (int step = 0; step < 10; step++) {
			startState = startState.randomMove();
		}
		
		// Reinitialise the start state as a copy of itself, to remove any unwanted extra information.
		startState = new State(startState);
		
		AStarTree<State> aStarTree = new AStarTree<>(startState, new ManhattanDistance());
		while (aStarTree.performStep()) {
		
		}
	}
}
