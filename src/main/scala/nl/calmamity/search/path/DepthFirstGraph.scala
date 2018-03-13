package nl.calmamity.search.path

import core.Action

import scala.collection.mutable

/** Implements a graph-based form of the depth-first search algorithm. */
class DepthFirstGraph[StateImplementation <: State[StateImplementation]](startState: StateImplementation) {
	/** A collection of all nodes that have not yet been expanded. */
	val frontier: mutable.ArrayStack[StateImplementation] = new mutable.ArrayStack[StateImplementation]()
	frontier.push(startState)
	
	/** The states that have already been explored; if encountered again, states in this set will not be expanded a second time. */
	val explored: mutable.Set[StateImplementation] = mutable.Set[StateImplementation]()
	
	/**
	  * Runs the algorithm to completion.
	  * @return the goal state found by the algorithm.
	  */
	def run(): StateImplementation = {
		var currentState: StateImplementation = frontier.pop()
		
		while (!currentState.isGoalState) {
			if (explored.add(currentState)) {
				// Determine the available actions, and add each of their resulting states to the frontier.
				val availableActionsIterator = currentState.createAvailableActionsIterator()
				while (availableActionsIterator.hasNext) {
					frontier.push(availableActionsIterator.next().getResultingState)
				}
			}
			currentState = frontier.pop()
		}
		
		currentState
	}
}
