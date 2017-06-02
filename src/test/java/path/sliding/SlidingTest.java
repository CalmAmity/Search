package path.sliding;

import core.BreadthFirstTree;
import core.DepthFirstGraph;
import core.Heuristic;
import org.junit.Assert;
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
	
	@Test
	public void manhattan() {
		Heuristic<State> heuristic = new ManhattanDistance();
		State state = new State(3, 3);
		Assert.assertEquals(0, heuristic.determineEstimatedDistanceToGoal(state), 0.00001);
		state = state.performMove(Move.SLIDE_UP);
		Assert.assertEquals(2, heuristic.determineEstimatedDistanceToGoal(state), 0.00001);
		state = state.performMove(Move.SLIDE_LEFT);
		Assert.assertEquals(4, heuristic.determineEstimatedDistanceToGoal(state), 0.00001);
		state = state.performMove(Move.SLIDE_DOWN);
		Assert.assertEquals(4, heuristic.determineEstimatedDistanceToGoal(state), 0.00001);
		state = state.performMove(Move.SLIDE_RIGHT);
		Assert.assertEquals(4, heuristic.determineEstimatedDistanceToGoal(state), 0.00001);
		state = state.performMove(Move.SLIDE_UP);
		Assert.assertEquals(6, heuristic.determineEstimatedDistanceToGoal(state), 0.00001);
		state = state.performMove(Move.SLIDE_UP);
		Assert.assertEquals(8, heuristic.determineEstimatedDistanceToGoal(state), 0.00001);
	}
}
