package local.hillclimbing;

import java.util.Collection;
import java.util.stream.Collectors;

import core.Heuristic;
import core.State;

/** Implements a simple form of the steepest ascent hill-climbing algorithm. */
public class SteepestAscent<S extends State<S>> {
	/** The best state that has been found. The next step will be performed from this state. */
	private S currentState;
	/** The heuristic used to judge the quality of states. */
	private Heuristic<S> heuristic;
	
	/**
	 * Constructs a new state object.
	 * @param startState The state from which the algorithm should start searching.
	 * @param heuristic The heuristic that should be used to judge the quality of states.
	 */
	public SteepestAscent(S startState, Heuristic<S> heuristic) {
		currentState = startState;
		this.heuristic = heuristic;
	}
	
	/**
	 * Performs a single step in the algorithm, by generating all possible successor states and moving to the best one.
	 * @return The new best state, or {@code null} if no successor state is better than {@code #currentState}.
	 */
	public S performStep() {
		// Determine the quality of the current state.
		double currentDistance = heuristic.determineEstimatedDistanceToGoal(currentState);
		// Create the complete set of possible successor states.
		Collection<S> successorStates = currentState.determineAvailableActions().stream().map(action -> action.getResultingState()).collect(Collectors.toSet());
		for (S successorState : successorStates) {
			if (heuristic.determineEstimatedDistanceToGoal(successorState) < currentState.getHeuristicDistanceFromGoal()) {
				// This successor state is of a higher quality than the current state. Make this the new current state.
				currentState = successorState;
			}
		}
		
		if (currentDistance > currentState.getHeuristicDistanceFromGoal()) {
			// The newly found current state is of a higher quality than the state that was current at the start of this step. Return this new current state.
			return currentState;
		} else {
			// No state could be found that is of a higher quality than the state that was current at the start of this step. Return null to signify this.
			return null;
		}
	}
	
	/**
	 * Runs the algorithm to completion.
	 * @return The best state that could be found.
	 */
	public S run() {
		while(true) {
			// Perform the next step.
			S nextState = performStep();
			if (nextState == null) {
				// No state better than the current state could be found. Stop searching.
				break;
			}
			// A new, better state has been found. Make it current.
			currentState = nextState;
		}
		
		return currentState;
	}
	
	public S getCurrentState() {
		return currentState;
	}
}
