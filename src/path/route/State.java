package path.route;

import java.util.Collection;
import java.util.stream.Collectors;

import core.Action;

/** Represents a state in the route search problem. */
public class State implements path.State<State> {
	/** The location represented by this state. */
	private Location currentLocation;
	/** The cost of reaching this state from the starting state. */
	private double cost;
	/** The distance between this state and the goal state, as estimated by a heuristic function. */
	private Double heuristicDistanceFromGoal;
	/** The state that preceded this one in the route currently being explored. */
	private State predecessor;
	
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
	public boolean equals(Object other) {
		if (super.equals(other)) {
			// This is the same state object.
			return true;
		}
		
		if (!(other instanceof State)) {
			// The other object is not of the same class.
			return false;
		}
		
		State otherState = (State) other;
		// Only the location matters; defer to the default implementation of equals for Location.
		return currentLocation.equals(otherState.getCurrentLocation());
	}
	
	@Override
	public Double getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}
	
	@Override
	public void setHeuristicDistanceFromGoal(Double distance) {
		heuristicDistanceFromGoal = distance;
	}
	
	public Location getCurrentLocation() {
		return currentLocation;
	}
	
	@Override
	public State getPredecessor() {
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
