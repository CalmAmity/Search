package core;

import util.Util;

/**
 * Superclass of all heuristic functions.
 * @param <S> The type of state that is checked by this heuristic.
 */
public abstract class Heuristic<S extends core.State<S>> {
	/** The best score that a state can attain using this heuristic. */
	protected final double optimalScore;
	
	protected Heuristic(double optimalScore) {
		this.optimalScore = optimalScore;
	}
	
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
	
	/**
	 * Determines whether a given score is the best score that can be attained using this heuristic. A state with this score is usually a goal state.
	 * @param score The score to check.
	 * @param qualityMargin The quality margin to employ; if the difference between {@code score} and the optimal score is less than this value, the score will be regarded as
	 * optimal.
	 * @return {@code true} if {@code score} is the best possible score a state can achieve with this heuristic.
	 */
	public boolean determineIsOptimalScore(double score, double qualityMargin) {
		return score + qualityMargin + Util.ERROR_MARGIN_FOR_FLOAT_COMPARISON >= optimalScore;
	}
	
	/**
	 * Determines whether a given score is the best score that can be attained using this heuristic. A state with this score is usually a goal state.
	 * @param score The score to check.
	 * @return {@code true} if {@code score} is the best possible score a state can achieve with this heuristic.
	 */
	public boolean determineIsOptimalScore(double score) {
		return determineIsOptimalScore(score, 0);
	}
}
