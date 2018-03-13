package nl.calmamity.search.local.hillclimbing

import nl.calmamity.search.core.{Heuristic, State}
import util.Util

import scala.collection.JavaConverters._

class Stochastic[StateImplementation <: State[StateImplementation]](
	val startState: StateImplementation
	, val heuristic: Heuristic[StateImplementation]
	, val maximumNumPlateauMoves: Int
) extends AbstractHillClimbing[StateImplementation] {
	override def determineSuccessorState(currentState: StateImplementation): Option[StateImplementation] = {
		// Start by collecting only those successors that have an equal or better quality compared to the current state.
		val nonDownhillSuccessors = currentState
			.createAvailableActionsIterator()
			.toSeq
			.map(_.getResultingState)
			.filter(state =>
				heuristic.determineQualityScore(state)
					>= heuristic.determineQualityScore(currentState) - Util.ERROR_MARGIN_FOR_FLOAT_COMPARISON
			)
		
		val qualityScores = nonDownhillSuccessors.map(heuristic.determineQualityScore)
		
		// Determine the lowest quality score among all successors.
		val lowestScore = qualityScores.min
		
		// Determine the individual weights of the states. The weight for a state is its quality score minus the lowest
		// score in the set. This ensures that the lowest-scoring state still has a non-negative weight (namely 0).
		val stateWeights = qualityScores.map(_ - lowestScore)
		
		// Determine the cumulative weights of the states in this list.
		val cumulativeWeights = stateWeights.scanLeft(0d)((weight1, weight2) => weight1 + weight2).tail
		
		if (cumulativeWeights.isEmpty) {
			None
		} else {
			// Pick a random number between 0 and the total weight.
			val randomValue = math.random() * cumulativeWeights.last
			// Use binary search to find the position of this random number in the list of cumulative weights.
			val selectedStateIndex =
				Util.binarySearch(randomValue, cumulativeWeights.map(java.lang.Double.valueOf).asJava)
			// Return the state at this index.
			Some(nonDownhillSuccessors(selectedStateIndex))
		}
	}
	
	override def logStatus(currentState: StateImplementation): Unit = {
		log.trace("Current state:\n{}", currentState)
		log.debug("Current state quality is {}.", heuristic.determineQualityScore(currentState))
	}
}
