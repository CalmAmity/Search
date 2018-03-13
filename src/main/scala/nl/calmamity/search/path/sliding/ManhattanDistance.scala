package nl.calmamity.search.path.sliding

import nl.calmamity.search.core.Heuristic

/** Implements the Manhattan distance heuristic for sliding puzzles. */
class ManhattanDistance(goalState: State) extends Heuristic[State] {
	override val optimalScore: Double = 0
	
	override def estimateQualityScore(state: State): Double = {
		val numTiles: Int = state.width * state.height
		// The Manhattan distance between two states of the sliding puzzle is the sum of distances of the individual
		// tile from their goal positions.
		
		val distancesForIndividualTiles = for(tile <- 0 until numTiles) yield {
			// Calculate the Manhattan distance between the current tile's position and its goal position.
			val (desiredX, desiredY) = goalState.findTilePosition(tile)
			val (actualX, actualY) = state.findTilePosition(tile)
			math.abs(desiredX - actualX) + math.abs(desiredY - actualY)
		}
		
		// The quality of the state is the negative Manhattan distance.
		- distancesForIndividualTiles.sum
	}
}
