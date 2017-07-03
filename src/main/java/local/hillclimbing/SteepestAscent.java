package local.hillclimbing;

import java.util.Iterator;

import core.Action;
import core.Heuristic;
import core.State;

/** Implements a simple form of the steepest ascent hill-climbing algorithm. */
public class SteepestAscent<S extends State<S>> extends AbstractHillClimbing<S> {
	public SteepestAscent(S startState, Heuristic<S> heuristic, int maximumNrPlateauMoves) {
		super(startState, heuristic, maximumNrPlateauMoves);
	}
	
	@Override
	protected S determineSuccessorState() {
		S newState = currentState;
		int nrPossibleSuccessorsInspected = 0;
		Iterator<Action<S>> possibleSuccessors = currentState.createAvailableActionsIterator();
		while (possibleSuccessors.hasNext()) {
			S successorState = possibleSuccessors.next().getResultingState();
			if (heuristic.determineQualityScore(successorState) >= newState.getQualityScore()) {
				// This successor state is of a higher quality than the current state. Make this the new state.
				newState = successorState;
			}
			log.trace("{} possible successors checked.", ++nrPossibleSuccessorsInspected);
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
		log.trace("Current state:\n{}", currentState);
		log.debug("Current state quality is {}.", currentState.getQualityScore());
	}
}
