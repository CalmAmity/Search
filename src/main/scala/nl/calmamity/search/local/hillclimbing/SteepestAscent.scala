package nl.calmamity.search.local.hillclimbing

import nl.calmamity.search.core.{Heuristic, State}

/** Implements a simple form of the steepest ascent hill-climbing algorithm. */
class SteepestAscent[StateImplementation <: State[StateImplementation]](
	val startState: StateImplementation
	, val heuristic: Heuristic[StateImplementation]
	, val maximumNumPlateauMoves: Int
) extends AbstractHillClimbing[StateImplementation] {
	override def determineSuccessorState(currentState: StateImplementation): Option[StateImplementation] = {
		var newState: StateImplementation = currentState
		var nrPossibleSuccessorsInspected: Int = 0
		val possibleSuccessors = currentState.createAvailableActionsIterator()
		while (possibleSuccessors.hasNext) {
			val successorState = possibleSuccessors.next().getResultingState
			if (heuristic.determineQualityScore(successorState) >= heuristic.determineQualityScore(newState)) {
				// This successor state is of a higher quality than the current state. Make this the new state.
				newState = successorState
			}
			nrPossibleSuccessorsInspected += 1
			log.trace("{} possible successors checked.", nrPossibleSuccessorsInspected)
		}
		
		if (heuristic.determineQualityScore(newState) < heuristic.determineQualityScore(currentState)) {
			// All of the possible successors have a lower score than the current state. A local maximum has been
			// reached, which means this search has finished. Return null to
			// signify this.
			None
		} else {
			Some(newState)
		}
	}
	
	override def logStatus(currentState: StateImplementation): Unit = {
		log.trace("Current state:\n{}", currentState)
		log.debug("Current state quality is {}.", heuristic.determineQualityScore(currentState))
	}
}
