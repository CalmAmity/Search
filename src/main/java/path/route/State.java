package path.route;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import core.Action;
import util.Vertex;

/** Represents a state in the route search problem. */
public class State implements path.State<State> {
	/** The location represented by this state. */
	private Location currentLocation;
	/** The cost of reaching this state from the starting state. */
	private double cost;
	/** This state's quality score, as estimated by a heuristic function. */
	private Double qualityScore;
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
	public Iterator<Action<State>> createAvailableActionsIterator() {
		return new Iterator<Action<State>>() {
			/** The current index of this iterator in the list of connections available from the current location. */
			private int nextConnectionIndex;
			
			@Override
			public boolean hasNext() {
				return nextConnectionIndex < currentLocation.getConnections().size();
			}
			
			@Override
			public Action<State> next() {
				if (!hasNext()) {
					throw new NoSuchElementException("All outgoing connections have been checked.");
				}
				
				// Get the next connection and use it to create an action. Also increment the connection index!
				Vertex<Location> outgoingConnection = currentLocation.getConnections().get(nextConnectionIndex++);
				return new Action<>(new State(outgoingConnection.getDestination()), outgoingConnection.getCost());
			}
		};
	}
	
	@Override
	public Action<State> randomlySelectAvailableAction() {
		// Select a random outgoing connection from this location.
		Random rng = new Random();
		Vertex<Location> outgoingConnection = currentLocation.getConnections().get(rng.nextInt(currentLocation.getConnections().size()));
		// Use the connection to create an action.
		return new Action<>(new State(outgoingConnection.getDestination()), outgoingConnection.getCost());
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
	public int hashCode() {
		return currentLocation.hashCode();
	}
	
	@Override
	public Double getQualityScore() {
		return qualityScore;
	}
	
	@Override
	public void setQualityScore(Double qualityScore) {
		this.qualityScore = qualityScore;
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
