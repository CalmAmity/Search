package path.route;

import core.Heuristic;

public class GlobalDistance implements Heuristic<State> {
	private State goalState;
	
	@Override
	public double determineEstimatedDistanceToGoal(State state) {
		return goalState.getCurrentLocation().getPoint().euclideanDistanceTo(state.getCurrentLocation().getPoint());
	}
}
