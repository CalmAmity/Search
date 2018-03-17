package nl.calmamity.search.core

import nl.calmamity.search.SearchTest
import nl.calmamity.search.local.queens

class HeuristicTest extends SearchTest {
	"determineQualityScore" should "only call estimateQualityScore() if necessary" in {
		// Create an implementation of the Heuristic trait that keeps track of the number of calls to its implementation
		// of estimateQualityScore().
		val fakeHeuristic = new Heuristic[queens.State] {
			// The number of calls that has been done to estimateQualityScore().
			var numEstimateCalls = 0
			override val optimalScore: Double = 100
			override def estimateQualityScore(state: queens.State): Double = {
				// Increment the variable to keep track of calls to this function.
				numEstimateCalls += 1
				// Return an arbitrary quality score.
				state.board.size
			}
		}
		
		// Create a state.
		val state1 = queens.State(3)
		// Call determineQualityScore() ten times for this single state.
		for (_ <- 1 to 10) {
			fakeHeuristic.determineQualityScore(state1)
		}
		
		// Check that the quality score for this state is as expected.
		assert(fakeHeuristic.determineQualityScore(state1) == 3)
		// Check that the estimateQualityScore() has been called exactly once.
		assert(fakeHeuristic.numEstimateCalls == 1)
		
		// Create a different state.
		val state2 = queens.State(4)
		
		// Call determineQualityScore() ten times for this single state.
		for (_ <- 1 to 10) {
			fakeHeuristic.determineQualityScore(state2)
		}
		
		// Check that the quality score for this state is as expected.
		assert(fakeHeuristic.determineQualityScore(state2) == 4)
		// Check that the estimateQualityScore() has now been called twice.
		assert(fakeHeuristic.numEstimateCalls == 2)
	}
}
