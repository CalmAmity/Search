package core;

import java.util.Stack;

public class PathSearch {
	protected void printPathToState(State state) {
		State currentState = state;
		Stack<State> goalPath = new Stack<>();
		goalPath.push(currentState);
		while (currentState != null) {
			goalPath.push(currentState);
			currentState = currentState.predecessor;
		}
		
		while (!goalPath.isEmpty()) {
			System.out.println("---------");
			System.out.println(goalPath.pop());
		}
	}
}
