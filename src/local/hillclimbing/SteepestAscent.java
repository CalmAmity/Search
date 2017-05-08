package local.hillclimbing;

import java.util.Collection;
import java.util.stream.Collectors;

import core.Heuristic;
import core.State;

public class SteepestAscent<S extends State<S>> {
	private S currentState;
	private Heuristic<S> heuristic;
	
	public SteepestAscent(S startState, Heuristic<S> heuristic) {
		currentState = startState;
		this.heuristic = heuristic;
	}
	
	public S performStep() {
		heuristic.determineEstimatedDistanceToGoal(currentState);
		double currentDistance = currentState.getHeuristicDistanceFromGoal();
		Collection<S> successorStates = currentState.determineAvailableActions().stream().map(action -> action.getResultingState()).collect(Collectors.toList());
		for (S successorState : successorStates) {
			heuristic.determineEstimatedDistanceToGoal(successorState);
			if (successorState.getHeuristicDistanceFromGoal() < currentState.getHeuristicDistanceFromGoal()) {
				currentState = successorState;
			}
		}
		
		if (currentDistance > currentState.getHeuristicDistanceFromGoal()) {
			return currentState;
		} else {
			return null;
		}
	}
	
	
	public S run() {
		S nextState;
		
		while(true) {
			nextState = performStep();
			if (nextState == null) {
				break;
			}
			currentState = nextState;
		}
		
		return currentState;
	}
	
	public S getCurrentState() {
		return currentState;
	}
}
