package nl.calmamity.search.local.queens

import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory

class StateTest extends FlatSpec {
	val log = LoggerFactory.getLogger(this.getClass.getSimpleName)
	
	"isGoalState" should "recognise whether states are goal states" in {
		for (_ <- 1 to 10) {
			// Create an 8x8 state at random and check it.
			val state = State(8)
			log.debug(state.toString())
			val not = if (state.isGoalState) {
				""
			} else {
				"not"
			}
			log.debug(s"Is $not a goal state")
		}
		
		// Create a goal state and check it.
		val state = State(Seq(4, 2, 0, 6, 1, 7, 5, 3))
		assert(state.isGoalState)
	}
	
	"queensAreClashing" should "detect clashing queens" in {
		val state = new State(Seq(0, 1, 2, 0, 1, 2))
		assert(state.queensAreClashing(0, 1))
		assert(state.queensAreClashing(2, 1))
		assert(state.queensAreClashing(1, 4))
		assert(state.queensAreClashing(3, 5))
		assert(!state.queensAreClashing(0, 4))
		assert(!state.queensAreClashing(5, 1))
		assert(!state.queensAreClashing(2, 4))
		assert(!state.queensAreClashing(1, 3))
	}
	
	"createAvailableActionsIterator" should "determine the available actions" in {
		val state1 = State(Seq(0, 1, 2))
		// Determine the set of states reachable from this one.
		val statesReachableFrom1 = state1.createAvailableActionsIterator().toSeq.map(_.getResultingState)
		// Check for a number of states that should be present in the resulting set.
		assert(statesReachableFrom1.contains(State(Seq(2, 1, 2))))
		assert(statesReachableFrom1.contains(State(Seq(0, 2, 2))))
		assert(statesReachableFrom1.contains(State(Seq(0, 1, 1))))
		// Check for a number of states that should *not* be present in the resulting set.
		assert(!statesReachableFrom1.contains(State(Seq(0, 1, 2))))
		assert(!statesReachableFrom1.contains(State(Seq(1, 2, 2))))
		assert(!statesReachableFrom1.contains(State(Seq(2, 1, 0))))
		
		// Check the expected number of available actions from states in the default 8x8 space.
		val state2 = State(8)
		assert(state2.createAvailableActionsIterator().size == 56)
	}
}
