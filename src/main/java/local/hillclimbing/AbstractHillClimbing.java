package local.hillclimbing;

import java.util.List;
import java.util.stream.Collectors;

import core.Action;
import core.Heuristic;
import core.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Util;

/**
 * An abstract superclass for all hill-climbing algorithms. Simply implement {@link #determineSuccessorState(List)} and you're good to go!
 * @param <S> The type of state that defines the state space.
 */
public abstract class AbstractHillClimbing<S extends State<S>> {
	private static Logger log = LoggerFactory.getLogger(AbstractHillClimbing.class);
	/** The best state that has been found. The next step will be performed from this state. */
	protected S currentState;
	/** The heuristic used to judge the quality of states. */
	protected Heuristic<S> heuristic;
	/** The maximum number of consecutive moves the algorithm is allowed to make among states with the same quality. */
	protected final int maximumNrPlateauMoves;
	/** The number of consecutive moves the algorithm has currently made without finding a state of higher quality. */
	protected int currentNrPlateauMoves;
	
	/**
	 * Constructs a new state object.
	 * @param startState The state from which the algorithm should start searching.
	 * @param heuristic The heuristic that should be used to judge the quality of states.
	 */
	public AbstractHillClimbing(S startState, Heuristic<S> heuristic) {
		this(startState, heuristic, 0);
	}
	
	public AbstractHillClimbing(S startState, Heuristic<S> heuristic, int maximumNrPlateauMoves) {
		currentState = startState;
		this.heuristic = heuristic;
		this.maximumNrPlateauMoves = maximumNrPlateauMoves;
	}
	
	/**
	 * Performs a single step in the algorithm, by generating all possible successor states and moving to the best one.
	 * @return The new best state, or {@code null} if no successor state is better than {@code #currentState}.
	 */
	private S performStep() {
		if (Util.equalValue(heuristic.determineEstimatedDistanceToGoal(currentState), heuristic.getBestPossibleScore())) {
			// The current state has the best score it is possible to achieve using the current heuristic. Stop the search.
			return null;
		}
		
		// Create a complete list of possible successor states.
		List<S> successorStates = currentState.determineAvailableActions().stream().map(Action::getResultingState).collect(Collectors.toList());
		return determineSuccessorState(successorStates);
	}
	
	/**
	 * Determines the next state to step to.
	 * @param possibleSuccessors The complete list of available successor states.
	 * @return The new state, or {@code null} if the algorithm has finished (meaning {@code #currentState} is the final state).
	 */
	protected abstract S determineSuccessorState(List<S> possibleSuccessors);
	
	/**
	 * Runs the algorithm to completion.
	 * @return The best state that could be found.
	 */
	public S run() {
		while (true) {
			log.debug("Current state:\n{}", currentState);
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
