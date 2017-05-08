package core;

/**
 * Superclass of all heuristic functions.
 * @param <S> The type of state that is checked by this heuristic.
 */
public abstract class Heuristic<S extends core.State<S>> {
	/**
	 * Determines the estimated distance to a/the goal state using the heuristic function defined by this class, and stores it with the state.
	 * @param state The state for which to determine the distance.
	 * @return The distance of the provided state from the goal, as determined by the heuristic function.
	 */
	public double determineEstimatedDistanceToGoal(S state) {
		if (state.getHeuristicDistanceFromGoal() == null) {
			// The distance for this state has not yet been defined. Do so now.
			state.setHeuristicDistanceFromGoal(estimateDistanceToGoal(state));
		}
		return state.getHeuristicDistanceFromGoal();
	}
	
	/**
	 * Implements the heuristic function, using it to determine the provided state's distance from a/the goal state.
	 * @param state The state for which to determine the distance.
	 * @return The distance of the provided state from the goal, as determined by the heuristic function.
	 */
	protected abstract double estimateDistanceToGoal(S state);
	
	/** @return the best possible score a state can achieve with this heuristic. A state with this score can be regarded as a goal state. */
	public abstract double getBestPossibleScore();
}
