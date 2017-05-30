package core;

import path.State;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Collectors;

/** Implements a simple form of the tree-based breadth-first search algorithm. */
public class BreadthFirstTree<S extends State<S>> {
	/** A collection of all nodes that have not yet been expanded. */
	private Queue<S> frontier;
	
	public BreadthFirstTree(S startState) {
		frontier = new ArrayDeque<>();
		frontier.add(startState);
	}
	
	/**
	 * Runs the algorithm to completion.
	 * @return the goal state found by the algorithm.
	 */
	public S run() {
		S currentState;
		do {
			currentState = frontier.poll();
			// Determine the available actions in the current state and add their resulting states to the frontier.
			frontier.addAll(currentState.determineAvailableActions().stream().map(Action::getResultingState).collect(Collectors.toList()));
		} while (!currentState.isGoalState());
		
		return currentState;
	}
}