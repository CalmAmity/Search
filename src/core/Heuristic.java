package core;

public interface Heuristic<State extends core.State> {
	double determineEstimatedDistanceToGoal(State state);
}
