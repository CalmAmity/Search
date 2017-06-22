package local.queens;

import core.Heuristic;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberOfClashesHeuristicTest {
	private static Logger log = LoggerFactory.getLogger(NumberOfClashesHeuristicTest.class);
	
	@Test
	public void test() {
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		
		for (int testIteration = 0; testIteration < 10; testIteration++) {
			// Create an 8x8 state at random and determine its distance.
			State state = new State(8);
			heuristic.determineEstimatedDistanceToGoal(state);
			log.debug("\n{}", state.toString());
		}
		
		// Create a specific non-goal state (11 clashes) and determine its distance.
		State state = new State(new int[]{1, 2, 5, 4, 0, 6, 4, 4});
		Assert.assertEquals(11, heuristic.determineEstimatedDistanceToGoal(state), .1);
		
		// Create a goal state and determine its distance.
		state = new State(new int[]{4, 2, 0, 6, 1, 7, 5, 3});
		Assert.assertEquals(0, heuristic.determineEstimatedDistanceToGoal(state), .1);
	}
}
