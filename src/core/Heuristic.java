package core;

public abstract class Heuristic<S extends core.State<S>> {
	public double determineEstimatedDistanceToGoal(S state) {
		if (state.getHeuristicDistanceFromGoal() == null) {
			estimateDistanceToGoal(state);
		}
		return state.getHeuristicDistanceFromGoal();
	}
	
	protected abstract double estimateDistanceToGoal(S state);
}
