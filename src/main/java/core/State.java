package core;

import java.util.Collection;

/**
 * Defines the basic methods shared by states for all problems.
 * @param <S> The state class implementing this interface.
 */
public interface State<S extends State<S>> {
	/** @return {@code true} if this state is a goal state according to the definition of the problem; {@code false} otherwise. */
	boolean isGoalState();
	
	/** @return a collection of all actions available to an agent in this state. */
	Collection<Action<S>> determineAvailableActions();
	
	/** @return the quality score of this state, as determined/estimated by a heuristic function. This value is stored with the state to prevent states from lingering in memory. */
	Double getQualityScore();
	
	/** Updates the quality score of this state, as determined/estimated by a heuristic function. This value is stored with the state to prevent states from lingering in memory. */
	void setQualityScore(Double qualityScore);
}
