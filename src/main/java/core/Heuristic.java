package core;

/**
 * Superclass of all heuristic functions.
 * @param <S> The type of state that is checked by this heuristic.
 */
public abstract class Heuristic<S extends core.State<S>> {
	/**
	 * Determines the quality of the provided state using the heuristic function defined by this class, and stores it with the state.
	 * @param state The state for which to determine the quality.
	 * @return The quality score of the provided state, as determined by the heuristic function.
	 */
	public double determineQualityScore(S state) {
		if (state.getQualityScore() == null) {
			// The quality score for this state has not yet been defined. Do so now.
			state.setQualityScore(estimateQualityScore(state));
		}
		return state.getQualityScore();
	}
	
	/**
	 * Implements the heuristic function, using it to determine the provided state's quality.
	 * @param state The state for which to determine the quality.
	 * @return The quality score for the provided state, as determined by the heuristic function.
	 */
	protected abstract double estimateQualityScore(S state);
	
	/** @return the best possible score a state can achieve with this heuristic. A state with this score can be regarded as a goal state. */
	public abstract double getBestPossibleScore();
}
