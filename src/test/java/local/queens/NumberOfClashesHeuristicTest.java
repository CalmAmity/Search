package local.queens;

import core.Heuristic;
import nl.calmamity.search.local.queens.NumberOfClashesHeuristic;
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
			// Create an 8x8 state at random and determine its quality score.
			State state = new State(8);
			heuristic.determineQualityScore(state);
			log.debug("\n{}", state.toString());
		}
		
		// Create a specific non-goal state (11 clashes) and determine its quality score.
		State state = new State(new int[]{1, 2, 5, 4, 0, 6, 4, 4});
		Assert.assertEquals(-11, heuristic.determineQualityScore(state), .1);
		
		// Create a goal state and determine its quality score.
		state = new State(new int[]{4, 2, 0, 6, 1, 7, 5, 3});
		Assert.assertEquals(0, heuristic.determineQualityScore(state), .1);
	}
}
