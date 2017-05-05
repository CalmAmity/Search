package local.queens;

import core.Heuristic;

public class Test {
	public static void main(String[] args) {
		testHeuristic();
	}
	
	public static void testHeuristic() {
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		
		for (int testIteration = 0; testIteration < 10; testIteration++) {
			// Create an 8x8 state at random and determine its distance.
			State state = new State(8);
			state.determineHeuristicDistanceFromGoal(heuristic);
			System.out.println(state);
			System.out.println("Heuristic distance from goal: " + state.getHeuristicDistanceFromGoal());
		}
		
		// Create a specific non-goal state (11 clashes) and determine its distance.
		State state = new State(new int[]{1,2,5,4,0,6,4,4});
		state.determineHeuristicDistanceFromGoal(heuristic);
		System.out.println(state);
		System.out.println("Heuristic distance from goal: " + state.getHeuristicDistanceFromGoal());
		
		// Create a goal state and determine its distance.
		state = new State(new int[]{4, 2, 0, 6, 1, 7, 5, 3});
		state.determineHeuristicDistanceFromGoal(heuristic);
		System.out.println(state);
		System.out.println("Heuristic distance from goal: " + state.getHeuristicDistanceFromGoal());
	}
	
	public static void testGoalDetection() {
		for (int testIteration = 0; testIteration < 10; testIteration++) {
			// Create an 8x8 state at random and check it.
			State state = new State(8);
			System.out.println(state);
			System.out.println("Is " + (state.isGoalState() ? "" : "not ") + "a goal state");
		}
		
		// Create a goal state and check it.
		State state = new State(new int[]{4, 2, 0, 6, 1, 7, 5, 3});
		System.out.println(state);
		System.out.println("Is " + (state.isGoalState() ? "" : "not ") + "a goal state");
	}
}
