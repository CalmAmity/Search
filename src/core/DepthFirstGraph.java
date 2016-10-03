package core;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import path.PathSearchUtil;

public class DepthFirstGraph extends PathSearchUtil {
	private Stack<State> frontier;
	private Set<State> explored;
	
	public DepthFirstGraph(State startState) {
		frontier = new Stack<>();
		frontier.push(startState);
		
		explored = new HashSet<>();
	}
	
	public State run() {
		State currentState;
		do {
			currentState = frontier.pop();
			if (!explored.add(currentState)) {
				// This state was already contained in the explored set; ignore it.
				continue;
			}
			frontier.addAll(currentState.determineAvailableActions());
		} while (!currentState.isGoalState());
		
		return currentState;
	}
	
}
