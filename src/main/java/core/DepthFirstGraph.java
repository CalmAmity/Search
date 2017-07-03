package core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import path.State;

/** Implements a graph-based form of the depth-first search algorithm. */
public class DepthFirstGraph<S extends State<S>> {
	/** A collection of all nodes that have not yet been expanded. */
	private Deque<S> frontier;
	/** The states that have already been explored; if encountered again, states in this set will not be expanded a second time. */
	private Set<S> explored;
	
	public DepthFirstGraph(S startState) {
		frontier = new ArrayDeque<>();
		frontier.push(startState);
		explored = new HashSet<>();
	}
	
	/**
	 * Runs the algorithm to completion.
	 * @return the goal state found by the algorithm.
	 */
	public S run() {
		S currentState;
		do {
			currentState = frontier.pop();
			if (!explored.add(currentState)) {
				// This state was already contained in the explored set; ignore it.
				continue;
			}
			// Determine the available actions, and add each of their resulting states to the frontier.
			Iterator<Action<S>> availableActionsIterator = currentState.createAvailableActionsIterator();
			while (availableActionsIterator.hasNext()) {
				frontier.add(availableActionsIterator.next().getResultingState());
			}
		} while (!currentState.isGoalState());
		
		return currentState;
	}
	
}
