package nl.calmamity.search.local.queens

import nl.calmamity.search.core.Heuristic

/** Implements the heuristic function that checks the number of pairs of queens that threaten each other. */
class NumberOfClashesHeuristic extends Heuristic[State] {
	override val optimalScore: Double = 0
	
	override def estimateQualityScore(state: State): Double = {
		// A higher number of clashes means a lower quality, so multiply the number of clashes by -1.
		-1 *
			// Create a list of column pairs to check against each other. This list should contain every possible pair
			// of distinct columns where the first column is to the left of the second one.
			(
				for {
					columnBeingChecked <- 0 until state.determineDimensions
					columnToCheckAgainst <- columnBeingChecked + 1 until state.determineDimensions
				} yield (columnBeingChecked, columnToCheckAgainst)
			)
			// Count the number of column pairs where the queens in those columns are clashing.
			.count {
				case (columnBeingChecked, columnToCheckAgainst) =>
					state.queensAreClashing(columnBeingChecked, columnToCheckAgainst)
			}
	}
}
