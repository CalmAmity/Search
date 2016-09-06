package core;

public abstract class Heuristic<State extends core.State> {
	protected abstract int determineEstimatedTotalCost(State state);
}
