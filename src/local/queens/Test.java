package local.queens;

import java.util.Arrays;

import core.Heuristic;
import local.hillclimbing.SteepestAscent;

public class Test {
	public static void main(String[] args) {
		testSteepestAscent();
	}
	
	public static void testSteepestAscent() {
		State state = new State(8);
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		SteepestAscent<State> climb = new SteepestAscent<State>(state, heuristic);
		while(state != null) {
			System.out.println(state);
			heuristic.determineEstimatedDistanceToGoal(state);
			System.out.println("Distance: " + state.getHeuristicDistanceFromGoal());
			System.out.println("==========");
			state = climb.performStep();
		}
	}
	
	public static void testAvailableActions() {
		State state1 = new State(new int[]{0,1,2});
		System.out.println(state1.determineAvailableActions());
		
		State state2 = new State(8);
		System.out.println(state2.determineAvailableActions().size());
	}
	
	public static void arrayClone() {
		int[] a = new int[]{1,2,3,4};
		int[] b = a.clone();
		b[0] = 5;
		System.out.println(Arrays.toString(a)); // original
		System.out.println(Arrays.toString(b)); // cloned and subsequently modified
	}
	
	public static void testHeuristic() {
		Heuristic<State> heuristic = new NumberOfClashesHeuristic();
		
		for (int testIteration = 0; testIteration < 10; testIteration++) {
			// Create an 8x8 state at random and determine its distance.
			State state = new State(8);
			heuristic.determineEstimatedDistanceToGoal(state);
			System.out.println(state);
			System.out.println("Heuristic distance from goal: " + state.getHeuristicDistanceFromGoal());
		}
		
		// Create a specific non-goal state (11 clashes) and determine its distance.
		State state = new State(new int[]{1,2,5,4,0,6,4,4});
		heuristic.determineEstimatedDistanceToGoal(state);
		System.out.println(state);
		System.out.println("Heuristic distance from goal: " + state.getHeuristicDistanceFromGoal());
		
		// Create a goal state and determine its distance.
		state = new State(new int[]{4, 2, 0, 6, 1, 7, 5, 3});
		heuristic.determineEstimatedDistanceToGoal(state);
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
