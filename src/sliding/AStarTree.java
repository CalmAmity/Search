package sliding;

import core.PathSearch;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarTree extends PathSearch{
	private Queue<State> frontier;
	
	public AStarTree(State startState) {
		frontier = new PriorityQueue<>();
		frontier.add(startState);
	}
	
	public boolean next() {
		String estimatedTotalCostsForFrontier = totalEstimatedCosts();
		State currentState = frontier.poll();
		int estimatedTotalCostForCurrentNode = currentState.cost + currentState.determineHeuristicDistance();
		System.out.println(estimatedTotalCostsForFrontier + ", expanding node with estimated total cost "
				+ estimatedTotalCostForCurrentNode + ".");
		if (currentState.isGoalState()) {
			int finalCost = currentState.cost;
			printPathToState(currentState);
			System.out.println("FINAL COST: " + finalCost);
			return false;
		}
		frontier.addAll(currentState.determineSuccessors());
		
		return true;
	}
	
	public String totalEstimatedCosts() {
		StringBuilder result = new StringBuilder("[ ");
		for (State state : frontier) {
			int estimatedTotalCostForState = state.cost + state.determineHeuristicDistance();
			result.append(estimatedTotalCostForState).append(" ");
		}
		return result.append("]").toString();
	}
}
