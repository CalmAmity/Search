package nl.calmamity.search.local.queens

import org.scalatest.FlatSpec
import org.slf4j.{Logger, LoggerFactory}

class NumberOfClashesHeuristicTest extends FlatSpec {
	val log: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)
	
	"The heuristic" should "work" in {
		val heuristic = new NumberOfClashesHeuristic()
		
		for (testIteration <- 1 to 10) {
			// Create an 8x8 state at random and determine its quality score.
			val state = State(8)
			heuristic.determineQualityScore(state)
			log.debug("\n{}", state.toString())
		}
		
		// Create a specific non-goal state (11 clashes) and determine its quality score.
		val state1 = State(Seq(1, 2, 5, 4, 0, 6, 4, 4))
		assert(heuristic.determineQualityScore(state1) == -11)
		
		// Create a goal state and determine its quality score.
		val state2 = State(Seq(4, 2, 0, 6, 1, 7, 5, 3))
		assert(heuristic.determineQualityScore(state2) == 1)
	}
}
