package path;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

import core.Action;
import core.Heuristic;

/** Implements the A* path-finding algorithm. */
public class AStarTree<S extends State<S>> {
	/** The collection of nodes that have been encountered but not yet expanded. */
	private Queue<S> frontier;
	/** The heuristic used to judge the quality of states. */
	private Heuristic<S> heuristic;
	
	public AStarTree(S startState, Heuristic<S> heuristic) {
		this.heuristic = heuristic;
		// Initialise the frontier as a priority queue that orders members based on the score assigned to them by the heuristic function.
		frontier = new PriorityQueue<>(
				(state1, state2) -> {
					double difference = (state1.getCost() + state1.getHeuristicDistanceFromGoal()) - (state2.getCost() + state2.getHeuristicDistanceFromGoal());
					if (difference < 0) {
						return -1;
					} else if (difference > 0) {
						return 1;
					} else {
						return 0;
					}
				}
		);
		
		// Use the heuristic function to determine the distance between the start and goal states.
		heuristic.determineEstimatedDistanceToGoal(startState);
		frontier.add(startState);
	}
	
	/**
	 * Performs a single step in the algorithm, by adding all possible successor states to the frontier and moving to the best one in this expanded frontier.
	 * @return {@code true} if the step was performed as normal; {@code false} if a goal state has been encountered (and hence, no further steps need to be performed).
	 */
	public boolean performStep() {
		// From the frontier, get the next best state.
		S currentState = frontier.poll();
		if (currentState.isGoalState()) {
			// This state is a goal state.
			double finalCost = currentState.getCost();
			PathSearchUtil.printPathToState(currentState);
			System.out.println("FINAL COST: " + finalCost);
			return false;
		}
		
		// Create a collection of all actions available from the current state.
		Collection<Action<S>> availableActions = currentState.determineAvailableActions();
		for (Action<S> action : availableActions) {
			// Update this successor state with the cost of reaching it and add it to the frontier.
			S resultingState = action.getResultingState();
			resultingState.setCost(action.getCost());
			heuristic.determineEstimatedDistanceToGoal(resultingState);
			frontier.add(resultingState);
		}
		
		return true;
	}
	
	/** @return a string that specifies the estimated cost of all states currently in the frontier. */
	public String totalEstimatedCosts() {
		StringBuilder result = new StringBuilder("[ ");
		for (S state : frontier) {
			double estimatedTotalCostForState = state.getCost() + state.getHeuristicDistanceFromGoal();
			result.append(estimatedTotalCostForState).append(" ");
		}
		return result.append("]").toString();
	}
}
