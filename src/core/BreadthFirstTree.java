package core;

import java.util.ArrayDeque;
import java.util.Queue;

import path.PathSearchUtil;

public class BreadthFirstTree extends PathSearchUtil {
	Queue<State> frontier;
	
	public BreadthFirstTree(State startState) {
		frontier = new ArrayDeque<>();
		frontier.add(startState);
	}
	
	public State run() {
		State currentState;
		do {
			currentState = frontier.poll();
			frontier.addAll(currentState.determineChildren());
		} while (!currentState.isGoalState());
		
		return currentState;
	}
}
