package nl.calmamity.search.path.route

import nl.calmamity.search.core.Heuristic

/**
  * Implements the heuristic function that judges a state based on the global distance from it to the goal state.
  * @param goalState The goal state from which to determine the distance.
  */
class GlobalDistance(goalState: State) extends Heuristic[State] {
	override val optimalScore: Double = 0
	
	override def estimateQualityScore(state: State): Double = {
		// The quality of this state is the negative distance to the goal state.
		-goalState.currentLocation.getPoint.euclideanDistanceTo(state.currentLocation.getPoint)
	}
}
