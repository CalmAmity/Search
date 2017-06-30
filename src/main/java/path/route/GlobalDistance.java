package path.route;

import core.Heuristic;

/** Implements the heuristic function that judges a state based on the global distance from it to the goal state. */
public class GlobalDistance extends Heuristic<State> {
	/** The goal state from which to determine the distance. */
	private State goalState;
	
	public GlobalDistance() {
		super(0);
	}
	
	@Override
	protected double estimateQualityScore(State state) {
		// The quality of this state is the negative distance to the goal state.
		return -goalState.getCurrentLocation().getPoint().euclideanDistanceTo(state.getCurrentLocation().getPoint());
	}
}
