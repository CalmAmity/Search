package core;

public interface State<S extends State<S>> {
	boolean isGoalState();
	
	double getHeuristicDistanceFromGoal();
	
	void determineHeuristicDistanceFromGoal(Heuristic<S> heuristic);
}
