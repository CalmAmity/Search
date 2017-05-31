package path.sliding;

import core.BreadthFirstTree;
import core.DepthFirstGraph;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import path.AStarTree;
import path.PathSearchUtil;

public class StateTest {
	private static Logger log = LoggerFactory.getLogger(StateTest.class);
	
	@Test
	public void copy() {
		State state1 = new State(5, 5);
		State state2 = new State(state1);
		Assert.assertEquals(state1, state2);
		State state3 = state2.randomMove();
		// Check that state2 has not been affected by the copy action.
		Assert.assertEquals(state1, state2);
		Assert.assertNotEquals(state2, state3);
		
		// Thoroughly randomise a state.
		for (int step = 0; step < 99; step++) {
			state3 = state3.randomMove();
		}
		// Check that copying still works for a non-starting state.
		State state4 = new State(state3);
		Assert.assertEquals(state3, state4);
	}
	
	/** Creates a 3x3 start state and randomises it. No explicit tests are done, but every random move is logged at DEBUG level. */
	@Test
	public void randomise() {
		State startState = new State(3, 3);
		
		log.debug("\n" + startState.toString());
		log.debug("scrambling...");
		
		for (int step = 0; step < 150; step++) {
			startState = startState.randomMove();
			log.debug("\n{}", startState.toString());
		}
	}
	
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