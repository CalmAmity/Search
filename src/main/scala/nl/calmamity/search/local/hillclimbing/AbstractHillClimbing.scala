package nl.calmamity.search.local.hillclimbing

import nl.calmamity.search.core.{Heuristic, State}
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import util.Util

/**
 * An abstract superclass for single-state hill-climbing algorithms. Simply implement {@link #determineSuccessorState()} and you're good to go!
 * @param <S> The type of state that defines the state space.
 */
abstract class AbstractHillClimbing[StateImplementation <: State[StateImplementation]] {
	val startState: StateImplementation
	/** The heuristic used to judge the quality of states. */
	val heuristic: Heuristic[StateImplementation]
	/** The maximum number of consecutive moves the algorithm is allowed to make among states with the same quality. */
	val maximumNumPlateauMoves: Int
	/** Indicates if downhill moves are allowed. If {@code false} (the default), the search will stop in any local maximum it reaches. */
	val allowDownhillMoves: Boolean = false
	
	val log: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)
	/** The number of consecutive moves the algorithm has currently made without finding a state of higher quality. */
	var currentNrPlateauMoves: Int = 0
	
	/**
	 * Performs a single step in the algorithm, by generating all possible successor states and moving to the best one.
	 * @return The new best state, or {@code null} if no successor state is better than {@code #currentState}.
	 */
	def performStep(currentState: StateImplementation): Option[StateImplementation] = {
		if (heuristic.determineIsOptimalScore(heuristic.determineQualityScore(currentState))) {
			// The current state has the best score it is possible to achieve using the current heuristic. Stop the search.
			return None
		}
		
		Util.measureMemoryUse()
		// Determine the successor state.
		val selectedSuccessor: Option[StateImplementation] = determineSuccessorState(currentState)
		Util.measureMemoryUse()
		
		selectedSuccessor match {
			case Some(successorState) =>
				if (
					Util.equalValue(
						heuristic.determineQualityScore(successorState)
						, heuristic.determineQualityScore(currentState)
					)
					&& currentNrPlateauMoves < maximumNumPlateauMoves
				) {
					// The new state is of the same quality as the current state. The maximum number of moves among
					// same-quality states has not yet been exceeded, so keep going.
					currentNrPlateauMoves += 1
					selectedSuccessor
				} else if (
					heuristic.determineQualityScore(successorState) > heuristic.determineQualityScore(currentState)
				) {
					// The new state is superior to the current state. Indicate that we have left the plateau (if any),
					// and return the new state.
					currentNrPlateauMoves = 0
					selectedSuccessor
				} else if (allowDownhillMoves) {
					// The new state is inferior to the current state, and choosing inferior states is allowed. Return
					// the new state.
					selectedSuccessor
				} else {
					// The new state is inferior to the current state, and choosing inferior states is not allowed.
					// Return null to signify that the search has ended.
					None
				}
			case None =>
				// The selection algorithm has decided that the search is finished.
				None
		}
	}
	
	/**
	 * Determines the next state to step to.
	 * @return The new state, or {@code null} if the algorithm has finished (meaning {@code #currentState} is the final state).
	 */
	def determineSuccessorState(currentState: StateImplementation): Option[StateImplementation]
	
	/** Writes the status of the algorithm to the log. */
	def logStatus(currentState: StateImplementation): Unit
	
	/**
	 * Runs the algorithm to completion.
	 * @return The best state that could be found.
	 */
	def run(): StateImplementation = {
		log.info("Starting run of {}.", this.getClass.getSimpleName)
		
		var nextState: Option[StateImplementation] = Some(startState)
		var currentState: StateImplementation = nextState.get
		do {
			currentState = nextState.get
			logStatus(currentState)
			// Perform the next step.
			nextState = performStep(currentState)
		} while (nextState.isDefined)
		
		Util.logMemoryUsage()
		log.info("Final state:\n{}", currentState)
		currentState
	}
}
