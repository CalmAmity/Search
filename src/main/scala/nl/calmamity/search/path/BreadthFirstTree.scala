package nl.calmamity.search.path

import core.Action

import scala.collection.mutable

/** Implements a simple form of the tree-based breadth-first search algorithm. */
class BreadthFirstTree[StateImplementation <: State[StateImplementation]](startState: StateImplementation) {
	/** A collection of all nodes that have not yet been expanded. */
	val frontier: mutable.Queue[StateImplementation] = new mutable.Queue[StateImplementation]()
	frontier.enqueue(startState)
	
	/**
	  * Runs the algorithm to completion.
	  * @return the goal state found by the algorithm.
	  */
	def run(): StateImplementation = {
		var currentState: StateImplementation = frontier.dequeue()
		
		while (!currentState.isGoalState) {
			// Determine the available actions in the current state and add their resulting states to the frontier.
			val availableActionsIterator = currentState.createAvailableActionsIterator()
			while (availableActionsIterator.hasNext) {
				frontier.enqueue(availableActionsIterator.next().getResultingState)
			}
			currentState = frontier.dequeue()
		}
		
		currentState
	}
}
