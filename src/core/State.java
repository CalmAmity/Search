package core;

import java.util.Collection;

public interface State<S extends State<S>> {
	boolean isGoalState();
	
	double getHeuristicDistanceFromGoal();
	
	void determineHeuristicDistanceFromGoal(Heuristic<S> heuristic);
	
	Collection<Action<S>> determineAvailableActions();
}
