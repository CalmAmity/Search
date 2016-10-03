package path.route;

import java.util.Collection;
import java.util.stream.Collectors;

import core.Action;
import core.Heuristic;

public class State implements path.State<State> {
	private Location currentLocation;
	private double cost;
	private Double heuristicDistanceFromGoal;
	private path.State predecessor;
	
	public State(Location location) {
		super();
		currentLocation = location;
	}
	
	@Override
	public boolean isGoalState() {
		return currentLocation.getIsGoal();
	}
	
	@Override
	public Collection<Action<State>> determineAvailableActions() {
		// For every connection that this location has to another, create a new state based on that destination location.
		return currentLocation.getConnections().stream().map(connection -> new Action<>(new State(connection.getDestination()), connection.getCost())).collect(Collectors.toList());
	}
	
	@Override
	public double getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}
	
	@Override
	public void determineHeuristicDistanceFromGoal(Heuristic<State> heuristic) {
		if (heuristicDistanceFromGoal == null) {
			heuristicDistanceFromGoal = heuristic.determineEstimatedDistanceToGoal(this);
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (super.equals(other)) {
			return true;
		}
		
		if (!(other instanceof State)) {
			// The object isn't even of the correct class.
			return false;
		}
		
		State otherState = (State) other;
		// Only the location matters; defer to the default implementation of equals for Location.
		return currentLocation.equals(otherState.getCurrentLocation());
	}
	
	public Location getCurrentLocation() {
		return currentLocation;
	}
	
	@Override
	public path.State getPredecessor() {
		return predecessor;
	}
	
	@Override
	public void setPredecessor(State predecessor) {
		this.predecessor = predecessor;
	}
	
	@Override
	public double getCost() {
		return cost;
	}
	
	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}
}
