package path.route;

import java.util.ArrayList;
import java.util.List;

import util.Node;
import util.Point;
import util.Vertex;

/** Represents a location in the route search problem. */
public class Location implements Node<Location> {
	/** The coordinates of this location. */
	protected Point<Double> point;
	/** The list of all connections this location has to others. */
	protected List<Vertex<Location>> connections;
	/** Indicates whether this location is the goal of the search. */
	protected boolean isGoal;
	
	public Location(Point<Double> point) {
		super();
		this.point = point;
		connections = new ArrayList<>();
	}
	
	/**
	 * Creates a connection from this location to the provided location. This connection is symmetrical.
	 * @param other The location to link this one to.
	 * @param cost The cost of moving between the locations once.
	 */
	public void link(Location other, double cost) {
		// Create the connection.
		Vertex<Location> newConnection = new Vertex<>(this, other, cost);
		if (this.getConnections().contains(newConnection)) {
			// This connection already exists. Do nothing.
			return;
		}
		
		// Connections in this problem are symmetrical; add a vertex for both directions.
		this.getConnections().add(newConnection);
		other.getConnections().add(new Vertex<>(other, this, cost));
	}
	
	@Override
	public boolean equals(Object other) {
		if (super.equals(other)) {
			// This is the same location object.
			return true;
		}
		
		if (!(other instanceof Location)) {
			// The other object is not of the same class.
			return false;
		}
		
		Location otherLocation = (Location) other;
		// Defer to the default implementation of equals for Point.
		return point.equals(otherLocation.getPoint());
	}
	
	public Point<Double> getPoint() {
		return point;
	}
	
	@Override
	public List<Vertex<Location>> getConnections() {
		return connections;
	}
	
	public boolean getIsGoal() {
		return isGoal;
	}
}
