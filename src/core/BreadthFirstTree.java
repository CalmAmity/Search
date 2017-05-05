package core;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Collectors;

import path.State;

public class BreadthFirstTree<S extends State<S>> {
	Queue<S> frontier;
	
	public BreadthFirstTree(S startState) {
		frontier = new ArrayDeque<>();
		frontier.add(startState);
	}
	
	public S run() {
		S currentState;
		do {
			currentState = frontier.poll();
			// Determine the available actions in the current state and add their resulting states to the frontier.
			frontier.addAll(currentState.determineAvailableActions().stream().map(action -> action.getResultingState()).collect(Collectors.toList()));
		} while (!currentState.isGoalState());
		
		return currentState;
	}
}
