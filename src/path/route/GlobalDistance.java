package path.route;

import core.Heuristic;

/** Implements the heuristic function that judges a state based on the global distance from it to the goal state. */
public class GlobalDistance extends Heuristic<State> {
	/** The goal state from which to determine the distance. */
	private State goalState;
	
	@Override
	protected double estimateDistanceToGoal(State state) {
		return goalState.getCurrentLocation().getPoint().euclideanDistanceTo(state.getCurrentLocation().getPoint());
	}
	
	@Override
	public double getBestPossibleScore() {
		// The best possible score is a distance of zero.
		return 0;
	}
}
