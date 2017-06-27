package local.hillclimbing;

import java.util.Collections;
import java.util.List;

import core.Heuristic;
import core.State;
import util.Util;

/** Implements a simple form of the steepest ascent hill-climbing algorithm. */
public class SteepestAscent<S extends State<S>> extends AbstractHillClimbing<S> {
	public SteepestAscent(S startState, Heuristic<S> heuristic) {
		super(startState, heuristic);
	}
	
	public SteepestAscent(S startState, Heuristic<S> heuristic, int maximumNrPlateauMoves) {
		super(startState, heuristic, maximumNrPlateauMoves);
	}
	
	@Override
	protected S determineSuccessorState(List<S> possibleSuccessors) {
		// Randomise the order of the list in order to prevent walking in tiny circles around a plateau.
		Collections.shuffle(possibleSuccessors);
		// Find the highest-quality successor state.
		S newState = currentState;
		for (S successorState : possibleSuccessors) {
			if (heuristic.determineQualityScore(successorState) >= newState.getQualityScore()) {
				// This successor state is of a higher quality than the current state. Make this the new state.
				newState = successorState;
			}
		}
		
		// Determine the quality of the current state.
		double currentStateQuality = heuristic.determineQualityScore(currentState);
		if (currentStateQuality > newState.getQualityScore()) {
			// The new state is of a higher quality than the current state. Return this new state.
			currentNrPlateauMoves = 0;
			return newState;
		} else if (Util.equalValue(currentStateQuality, newState.getQualityScore())
				&& currentNrPlateauMoves < maximumNrPlateauMoves) {
			// The new state is of the same quality as the current state. However, the maximum number of moves among same-quality states has not yet been exceeded, so keep going.
			currentNrPlateauMoves++;
			return newState;
		} else {
			// All states adjacent to the current one are of lower quality. Return null to signify this.
			return null;
		}
	}
}
