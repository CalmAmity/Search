package core;

import java.util.Collection;

public interface State<S extends State<S>> {
	boolean isGoalState();
	
	Collection<Action<S>> determineAvailableActions();
	
	double getHeuristicDistanceFromGoal();
	
	void determineHeuristicDistanceFromGoal(Heuristic<S> heuristic);
}
