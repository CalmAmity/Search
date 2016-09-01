package core;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DepthFirstGraph extends PathSearch {
	private Stack<State> frontier;
	private Set<State> explored;
	
	public DepthFirstGraph(State startState) {
		frontier = new Stack<>();
		frontier.push(startState);
		
		explored = new HashSet<>();
	}
	
	public boolean next() {
		State currentState = frontier.pop();
		if (!explored.add(currentState)) {
			// This state was already contained in the explored set; ignore it.
			return true;
		}
		
		if (currentState.isGoalState()) {
			printPathToState(currentState);
			return false;
		}
		frontier.addAll(currentState.determineSuccessors());
		
		return true;
	}
	
}
