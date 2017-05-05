package core;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import path.State;

public class DepthFirstGraph<S extends State<S>> {
	private Stack<S> frontier;
	private Set<S> explored;
	
	public DepthFirstGraph(S startState) {
		frontier = new Stack<>();
		frontier.push(startState);
		
		explored = new HashSet<>();
	}
	
	public S run() {
		S currentState;
		do {
			currentState = frontier.pop();
			if (!explored.add(currentState)) {
				// This state was already contained in the explored set; ignore it.
				continue;
			}
			// Determine the available actions, and add each of their resulting states to the frontier.
			frontier.addAll(currentState.determineAvailableActions().stream().map(action -> action.getResultingState()).collect(Collectors.toList()));
		} while (!currentState.isGoalState());
		
		return currentState;
	}
	
}
