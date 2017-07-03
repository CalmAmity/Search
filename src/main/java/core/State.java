package core;

import java.util.Iterator;

/**
 * Defines the basic methods shared by states for all problems.
 * @param <S> The state class implementing this interface.
 */
public interface State<S extends State<S>> {
	/** @return {@code true} if this state is a goal state according to the definition of the problem; {@code false} otherwise. */
	boolean isGoalState();
	
	/** @return an iterator that produces all actions available from this state exactly ones. */
	Iterator<Action<S>> createAvailableActionsIterator();
	
	/**
	 * @return one of the actions available in the current state, randomly selected from all available actions. Multiple consecutive calls to this method may produce the same
	 * action multiple times.
	 */
	Action<S> randomlySelectAvailableAction();
	
	/** @return the quality score of this state, as determined/estimated by a heuristic function. This value is stored with the state to prevent states from lingering in memory. */
	Double getQualityScore();
	
	/** Updates the quality score of this state, as determined/estimated by a heuristic function. This value is stored with the state to prevent states from lingering in memory. */
	void setQualityScore(Double qualityScore);
}
