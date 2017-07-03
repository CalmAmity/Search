package local.hillclimbing;

import java.util.Collections;
import java.util.List;

import core.Heuristic;
import core.State;

/** Implements a simple form of the steepest ascent hill-climbing algorithm. */
public class SteepestAscent<S extends State<S>> extends AbstractHillClimbing<S> {
	public SteepestAscent(S startState, Heuristic<S> heuristic, int maximumNrPlateauMoves) {
		super(startState, heuristic, maximumNrPlateauMoves);
	}
	
	@Override
	protected S determineSuccessorState(List<S> possibleSuccessors) {
		// Randomise the order of the list in order to prevent walking around a plateau in tiny circles.
		Collections.shuffle(possibleSuccessors);
		// Find the highest-quality successor state.
		S newState = currentState;
		for (S successorState : possibleSuccessors) {
			if (heuristic.determineQualityScore(successorState) >= newState.getQualityScore()) {
				// This successor state is of a higher quality than the current state. Make this the new state.
				newState = successorState;
			}
		}
		
		if (newState.getQualityScore() < currentState.getQualityScore()) {
			// All of the possible successors have a lower score than the current state. A local maximum has been reached, which means this search has finished. Return null to
			// signify this.
			return null;
		}
		
		return newState;
	}
	
	@Override
	protected void logStatus() {
		log.debug("Current state quality is {}.", currentState.getQualityScore());
	}
}
