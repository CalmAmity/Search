package path.route;

import core.Heuristic;

public class GlobalDistance extends Heuristic<State> {
	private State goalState;
	
	@Override
	protected double estimateDistanceToGoal(State state) {
		return goalState.getCurrentLocation().getPoint().euclideanDistanceTo(state.getCurrentLocation().getPoint());
	}
}
