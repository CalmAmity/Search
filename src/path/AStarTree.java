package path;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

import core.Action;
import core.Heuristic;

public class AStarTree<S extends State<S>> {
	private Queue<S> frontier;
	private Heuristic<S> heuristic;
	
	public AStarTree(S startState, Heuristic<S> heuristic) {
		this.heuristic = heuristic;
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
		
		startState.determineHeuristicDistanceFromGoal(heuristic);
		frontier.add(startState);
	}
	
	public boolean next() {
		S currentState = frontier.poll();
		if (currentState.isGoalState()) {
			double finalCost = currentState.getCost();
			PathSearchUtil.printPathToState(currentState);
			System.out.println("FINAL COST: " + finalCost);
			return false;
		}
		
		Collection<Action<S>> availableActions = currentState.determineAvailableActions();
		for (Action<S> action : availableActions) {
			S resultingState = action.getResultingState();
			resultingState.setCost(action.getCost());
			resultingState.determineHeuristicDistanceFromGoal(heuristic);
			frontier.add(resultingState);
		}
		
		return true;
	}
	
	public String totalEstimatedCosts() {
		StringBuilder result = new StringBuilder("[ ");
		for (S state : frontier) {
			double estimatedTotalCostForState = state.getCost() + state.getHeuristicDistanceFromGoal();
			result.append(estimatedTotalCostForState).append(" ");
		}
		return result.append("]").toString();
	}
}
