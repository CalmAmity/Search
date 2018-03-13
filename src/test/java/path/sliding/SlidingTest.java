package path.sliding;

import nl.calmamity.search.core.Heuristic;
import nl.calmamity.search.path.BreadthFirstTree;
import nl.calmamity.search.path.DepthFirstGraph;
import nl.calmamity.search.path.sliding.ManhattanDistance;
import nl.calmamity.search.path.sliding.Move;
import nl.calmamity.search.path.sliding.State;
import org.junit.Assert;
import org.junit.Test;
import path.AStarTree;
import path.PathSearchUtil;

public class SlidingTest {
	@Test
	public void bfs() {
		State startState = State.apply(3, 3);
		for (int step = 0; step < 15; step++) {
			startState = startState.randomMove();
		}
		
		BreadthFirstTree<State> breadthFirstTree = new BreadthFirstTree<>(State.apply(3, 3));
		State goalState = breadthFirstTree.run();
		PathSearchUtil.printPathToState(goalState);
	}
	
	@Test
	public void dfs() {
		State startState = State.apply(3, 3);
		for (int step = 0; step < 15; step++) {
			startState = startState.randomMove();
		}
		
		DepthFirstGraph<State> depthFirstGraph = new DepthFirstGraph<>(startState);
		State goalState = depthFirstGraph.run();
		PathSearchUtil.printPathToState(goalState);
	}
	
	@Test
	public void aStar() {
		State startState = State.apply(3, 3);
		for (int step = 0; step < 10; step++) {
			startState = startState.randomMove();
		}
		
		AStarTree<State> aStarTree = new AStarTree<>(startState, new ManhattanDistance(State.apply(3, 3)));
		while (aStarTree.performStep()) {
		
		}
	}
	
	@Test
	public void manhattan() {
		State state = State.apply(3, 3);
		Heuristic<State> heuristic = new ManhattanDistance(state);
		Assert.assertEquals(0, heuristic.determineQualityScore(state), 0.00001);
		state = state.performMove(Move.SlideUp());
		Assert.assertEquals(-2, heuristic.determineQualityScore(state), 0.00001);
		state = state.performMove(Move.SlideLeft());
		Assert.assertEquals(-4, heuristic.determineQualityScore(state), 0.00001);
		state = state.performMove(Move.SlideDown());
		Assert.assertEquals(-4, heuristic.determineQualityScore(state), 0.00001);
		state = state.performMove(Move.SlideRight());
		Assert.assertEquals(-4, heuristic.determineQualityScore(state), 0.00001);
		state = state.performMove(Move.SlideUp());
		Assert.assertEquals(-6, heuristic.determineQualityScore(state), 0.00001);
		state = state.performMove(Move.SlideUp());
		Assert.assertEquals(-8, heuristic.determineQualityScore(state), 0.00001);
	}
}
