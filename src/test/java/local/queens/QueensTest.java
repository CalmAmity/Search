package local.queens;

import java.util.Collection;
import java.util.stream.Collectors;

import core.Action;
import core.Heuristic;
import local.hillclimbing.RandomRestart;
import local.hillclimbing.SteepestAscent;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueensTest {
	private static Logger log = LoggerFactory.getLogger(QueensTest.class);
	
	@Test
	public void randomRestart() {
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		RandomRestart<State> search = new RandomRestart<>(heuristic);
		search.run(() -> new State(8));
	}
	
	@Test
	public void steepestAscent() {
		State state = new State(8);
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		SteepestAscent<State> climb = new SteepestAscent<>(state, heuristic, 100);
		climb.run();
	}
	
	@Test
	public void availableActions() {
		State state1 = new State(new int[]{0, 1, 2});
		// Determine the set of states reachable from this one.
		Collection<State> statesReachableFrom1 = state1.determineAvailableActions().stream().map(Action::getResultingState).collect(Collectors.toList());
		// Check for a number of states that should be present in the resulting set.
		Assert.assertTrue(statesReachableFrom1.contains(new State(new int[]{2, 1, 2})));
		Assert.assertTrue(statesReachableFrom1.contains(new State(new int[]{0, 2, 2})));
		Assert.assertTrue(statesReachableFrom1.contains(new State(new int[]{0, 1, 1})));
		// Check for a number of states that should *not* be present in the resulting set.
		Assert.assertFalse(statesReachableFrom1.contains(new State(new int[]{0, 1, 2})));
		Assert.assertFalse(statesReachableFrom1.contains(new State(new int[]{1, 2, 2})));
		Assert.assertFalse(statesReachableFrom1.contains(new State(new int[]{2, 1, 0})));
		
		// Check the expected number of available actions from states in the default 8x8 space.
		State state2 = new State(8);
		Assert.assertEquals(56, state2.determineAvailableActions().size());
	}
	
	@Test
	public void testGoalDetection() {
		for (int testIteration = 0; testIteration < 10; testIteration++) {
			// Create an 8x8 state at random and check it.
			State state = new State(8);
			log.debug(state.toString());
			log.debug("Is " + (state.isGoalState() ? "" : "not ") + "a goal state");
		}
		
		// Create a goal state and check it.
		State state = new State(new int[]{4, 2, 0, 6, 1, 7, 5, 3});
		Assert.assertTrue(state.isGoalState());
	}
}
