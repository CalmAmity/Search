package local.queens;

import core.Heuristic;
import local.hillclimbing.RandomRestart;
import local.hillclimbing.SteepestAscent;
import org.junit.Test;

public class QueensTest {
	@Test
	public void randomRestart() {
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		RandomRestart<State> search = new RandomRestart<>(heuristic, null, 0);
		search.run(() -> new State(8));
		// TODO test maximum iterations
		// TODO test distance margin
	}
	
	@Test
	public void steepestAscent() {
		State state = new State(8);
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		SteepestAscent<State> climb = new SteepestAscent<>(state, heuristic, 100);
		climb.run();
	}
}
