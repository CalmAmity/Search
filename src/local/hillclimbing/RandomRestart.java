package local.hillclimbing;

import java.util.function.Supplier;

import core.Heuristic;
import core.State;

/** Implements random restart hill climbing. */
public class RandomRestart<S extends State<S>> {
	private Heuristic<S> heuristic;
	
	public RandomRestart(Heuristic<S> heuristic) {
		this.heuristic = heuristic;
	}
	
	/**
	 * Runs the algorithm to completion.
	 * @param stateConstructor The constructor of the relevant {@code State} class that constructs a random state.
	 * @return The best state that could be found.
	 */
	public S run(Supplier<S> stateConstructor) {
		S currentEndState;
		do {
			// Randomly create a start state for this iteration, and perform a steepest ascent hill climbing search starting from this state.
			SteepestAscent<S> search = new SteepestAscent<>(stateConstructor.get(), heuristic);
			currentEndState = search.run();
		// TODO maximum number of iterations
		// TODO acceptable error margin
		} while (currentEndState.getHeuristicDistanceFromGoal() > heuristic.getBestPossibleScore());
		
		return currentEndState;
	}
}
