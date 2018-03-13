package nl.calmamity.search.local.hillclimbing

import nl.calmamity.search.local.queens.{NumberOfClashesHeuristic, State}
import org.scalatest.FlatSpec

class HillClimbingTest extends FlatSpec {
	"Random restart" should "climb a couple of hills" in {
		val heuristic = new NumberOfClashesHeuristic()
		val search = new RandomRestart[State](heuristic, 0, None, 0)
		search.run(() => State(8))
		
		// Test the functionality for limiting the number of iterations.
//		val maximumNrIterations = 2
//		for (testIteration <- 1 to 10) {
//			val searchWithMaxIterations = new RandomRestart[State](heuristic, 0, Some(maximumNrIterations), 0)
//			searchWithMaxIterations.run(() => State.apply(8))
//			Assert.assertTrue(searchWithMaxIterations.numIterationsPerformed <= maximumNrIterations)
//		}
		
		// Test the functionality for quality margins.
		val qualityMargin = 1
		for (_ <- 1 to 10) {
			val searchWithMaxIterations = new RandomRestart[State](heuristic, 0, None, qualityMargin)
			val state = searchWithMaxIterations.run(() => State(4))
			assert(heuristic.determineIsOptimalScore(heuristic.determineQualityScore(state), qualityMargin))
		}
	}
	
	it should "do something big" ignore {
		val heuristic = new NumberOfClashesHeuristic()
		val searchWithMaxIterations = new RandomRestart[State](heuristic, 20, Some(100), 0)
		searchWithMaxIterations.run(() => State(88))
	}
	
	"Steepest ascent" should "climb a single hill" in {
		val state = State(8)
		val heuristic = new NumberOfClashesHeuristic()
		val climb = new SteepestAscent[State](state, heuristic, 100)
		climb.run()
	}
	
	"Stochastic" should "maybe climb a hill" in {
		val state = State(8)
		val heuristic = new NumberOfClashesHeuristic()
		val climb = new Stochastic[State](state, heuristic, 100)
		climb.run()
	}
	
	"Simulated annealing" should "do something like annealing" in {
		val state = State(8)
		val heuristic = new NumberOfClashesHeuristic()
		val climb = new SimulatedAnnealing[State](state, heuristic, .05, 100)
		climb.run()
	}
	
	it should "do something big" ignore {
		val state = State(888)
		val heuristic = new NumberOfClashesHeuristic()
		val climb = new SimulatedAnnealing[State](state, heuristic, 1d / 5000, 1000)
		climb.run()
	}
}
