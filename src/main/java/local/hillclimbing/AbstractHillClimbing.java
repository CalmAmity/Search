package local.hillclimbing;

import core.Heuristic;
import core.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Util;

/**
 * An abstract superclass for single-state hill-climbing algorithms. Simply implement {@link #determineSuccessorState()} and you're good to go!
 * @param <S> The type of state that defines the state space.
 */
public abstract class AbstractHillClimbing<S extends State<S>> {
	protected static Logger log = LoggerFactory.getLogger(AbstractHillClimbing.class);
	/** The best state that has been found. The next step will be performed from this state. */
	protected S currentState;
	/** The heuristic used to judge the quality of states. */
	protected final Heuristic<S> heuristic;
	/** The maximum number of consecutive moves the algorithm is allowed to make among states with the same quality. */
	protected final int maximumNrPlateauMoves;
	/** The number of consecutive moves the algorithm has currently made without finding a state of higher quality. */
	protected int currentNrPlateauMoves;
	/** Indicates if downhill moves are allowed. If {@code false} (the default), the search will stop in any local maximum it reaches. */
	protected boolean allowDownhillMoves;
	
	/**
	 * @param startState The state from which the algorithm should start searching.
	 * @param heuristic The heuristic that should be used to judge the quality of states.
	 * @param maximumNrPlateauMoves The maximum number of consecutive moves the algorithm is allowed to make among states with the same quality.
	 */
	protected AbstractHillClimbing(S startState, Heuristic<S> heuristic, int maximumNrPlateauMoves) {
		currentState = startState;
		this.heuristic = heuristic;
		this.maximumNrPlateauMoves = maximumNrPlateauMoves;
	}
	
	/**
	 * Performs a single step in the algorithm, by generating all possible successor states and moving to the best one.
	 * @return The new best state, or {@code null} if no successor state is better than {@code #currentState}.
	 */
	protected S performStep() {
		if (heuristic.determineIsOptimalScore(heuristic.determineQualityScore(currentState))) {
			// The current state has the best score it is possible to achieve using the current heuristic. Stop the search.
			return null;
		}
		
		Util.measureMemoryUse();
		// Determine the successor state.
		S selectedSuccessor = determineSuccessorState();
		Util.measureMemoryUse();
		
		if (selectedSuccessor == null) {
			// The selection algorithm has decided that the search is finished.
			return null;
		}
		
		if (Util.equalValue(selectedSuccessor.getQualityScore(), currentState.getQualityScore())
				&& currentNrPlateauMoves < maximumNrPlateauMoves) {
			// The new state is of the same quality as the current state. The maximum number of moves among same-quality states has not yet been exceeded, so keep going.
			currentNrPlateauMoves++;
			return selectedSuccessor;
		} else if (selectedSuccessor.getQualityScore() > currentState.getQualityScore()) {
			// The new state is superior to the current state. Indicate that we have left the plateau (if any), and return the new state.
			currentNrPlateauMoves = 0;
			return selectedSuccessor;
		} else if (allowDownhillMoves) {
			// The new state is inferior to the current state, and choosing inferior states is allowed. Return the new state.
			return selectedSuccessor;
		} else {
			// The new state is inferior to the current state, and choosing inferior states is not allowed. Return null to signify that the search has ended.
			return null;
		}
	}
	
	/**
	 * Determines the next state to step to.
	 * @return The new state, or {@code null} if the algorithm has finished (meaning {@code #currentState} is the final state).
	 */
	protected abstract S determineSuccessorState();
	
	/** Writes the status of the algorithm to the log. */
	protected abstract void logStatus();
	
	/**
	 * Runs the algorithm to completion.
	 * @return The best state that could be found.
	 */
	public S run() {
		log.info("Starting run of {}.", this.getClass().getSimpleName());
		while (true) {
			logStatus();
			// Perform the next step.
			S nextState = performStep();
			if (nextState == null) {
				// No state better than the current state could be found. Stop searching.
				break;
			}
			// A new, better state has been found. Make it current.
			currentState = nextState;
		}
		
		Util.logMemoryUsage();
		log.info("Final state:\n{}", currentState);
		return currentState;
	}
}
