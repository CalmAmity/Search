package local.hillclimbing;

import core.Action;
import core.Heuristic;
import core.State;
import util.Util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** Implements a simple form of the steepest ascent hill-climbing algorithm. */
public class SteepestAscent<S extends State<S>> {
	/** The best state that has been found. The next step will be performed from this state. */
	private S currentState;
	/** The heuristic used to judge the quality of states. */
	private Heuristic<S> heuristic;
	/** The maximum number of consecutive moves the algorithm is allowed to make among states with the same quality. */
	private int maximumNrPlateauMoves;
	/** The number of consecutive moves the algorithm has currently made without finding a state of higher quality. */
	private int currentNrPlateauMoves;
	
	/**
	 * Constructs a new state object.
	 * @param startState The state from which the algorithm should start searching.
	 * @param heuristic The heuristic that should be used to judge the quality of states.
	 */
	public SteepestAscent(S startState, Heuristic<S> heuristic) {
		currentState = startState;
		this.heuristic = heuristic;
	}
	
	public SteepestAscent(S startState, Heuristic<S> heuristic, int maximumNrPlateauMoves) {
		this(startState, heuristic);
		this.maximumNrPlateauMoves = maximumNrPlateauMoves;
	}
	
	/**
	 * Performs a single step in the algorithm, by generating all possible successor states and moving to the best one.
	 * @return The new best state, or {@code null} if no successor state is better than {@code #currentState}.
	 */
	public S performStep() {
		if (Util.equalValue(heuristic.determineEstimatedDistanceToGoal(currentState), heuristic.getBestPossibleScore())) {
			// The current state has the best score it is possible to achieve using the current heuristic. Stop the search.
			return null;
		} 
		
		// Determine the quality of the current state.
		double currentDistance = heuristic.determineEstimatedDistanceToGoal(currentState);
		// Create a complete list of possible successor states.
		List<S> successorStates = currentState.determineAvailableActions().stream().map(Action::getResultingState).collect(Collectors.toList());
		// Randomise the order of the list in order to prevent walking in tiny circles around a plateau.
		Collections.shuffle(successorStates);
		for (S successorState : successorStates) {
			if (heuristic.determineEstimatedDistanceToGoal(successorState) <= currentState.getHeuristicDistanceFromGoal()) {
				// This successor state is of a higher quality than the current state. Make this the new current state.
				currentState = successorState;
			}
		}
		
		if (currentDistance > currentState.getHeuristicDistanceFromGoal()) {
			// The newly found current state is of a higher quality than the state that was current at the start of this step. Return this new current state.
			currentNrPlateauMoves = 0;
			return currentState;
		} else if (Util.equalValue(currentDistance, currentState.getHeuristicDistanceFromGoal())
				&& currentNrPlateauMoves < maximumNrPlateauMoves) {
			// The newly found current state is of the same quality as the state that was current at the start of this step. However, the maximum number of moves among same-quality
			// states has not yet been exceeded, so keep going.
			currentNrPlateauMoves++;
			return currentState;
		} else {
			// All states adjacent to the current one are of lower quality. Return null to signify this.
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
