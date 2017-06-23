package local.hillclimbing;

import core.Heuristic;
import local.queens.NumberOfClashesHeuristic;
import local.queens.State;
import org.junit.Assert;
import org.junit.Test;

// todo Create proper unit tests: mock the heuristic to make the outcome deterministic (rhyme unintentional), fixing expected behaviour.
public class HillClimbingTest {
	@Test
	public void randomRestart() {
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		RandomRestart<State> search = new RandomRestart<>(heuristic, null, 0);
		search.run(() -> new State(8));
		
		// Test the functionality for limiting the number of iterations.
		final int maximumNrIterations = 2;
		for (int testIteration = 0; testIteration < 10; testIteration++) {
			RandomRestart<State> searchWithMaxIterations = new RandomRestart<>(heuristic, maximumNrIterations, 0);
			searchWithMaxIterations.run(() -> new State(8));
			Assert.assertTrue(searchWithMaxIterations.nrIterationsPerformed <= maximumNrIterations);
		}
		
		// Test the functionality for quality margins.
		final double qualityMargin = 1;
		for (int testIteration = 0; testIteration < 10; testIteration++) {
			RandomRestart<State> searchWithMaxIterations = new RandomRestart<>(heuristic, null, qualityMargin);
			State state = searchWithMaxIterations.run(() -> new State(4));
			Assert.assertTrue(heuristic.determineEstimatedDistanceToGoal(state) <= heuristic.getBestPossibleScore() + qualityMargin + .0001);
		}
	}
	
	@Test
	public void steepestAscent() {
		State state = new State(8);
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		SteepestAscent<State> climb = new SteepestAscent<>(state, heuristic, 100);
		climb.run();
	}
}
