package core;

public interface Heuristic<S extends core.State<S>> {
	double determineEstimatedDistanceToGoal(S state);
}
