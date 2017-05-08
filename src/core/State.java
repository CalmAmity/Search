package core;

import java.util.Collection;

public interface State<S extends State<S>> {
	boolean isGoalState();
	
	Double getHeuristicDistanceFromGoal();
	
	Collection<Action<S>> determineAvailableActions();
}
