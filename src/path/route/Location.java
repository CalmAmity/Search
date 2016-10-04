package path.route;

import java.util.ArrayList;
import java.util.List;

import util.Node;
import util.Point;
import util.Vertex;

public class Location implements Node<Location> {
	/** The coordinates of this location. */
	protected Point<Double> point;
	
	protected List<Vertex<Location>> connections;
	
	protected boolean isGoal;
	
	public Location(Point point) {
		super();
		this.point = point;
		connections = new ArrayList<>();
	}
	
	public void link(Location other, double cost) {
		Vertex<Location> newConnection = new Vertex<>(this, other, cost);
		
		if (this.getConnections().contains(newConnection)) {
			return;
		}
		
		// Connections in this problem are symmetrical; add a vertex for both directions.
		this.getConnections().add(newConnection);
		other.getConnections().add(new Vertex<>(other, this, cost));
	}
	
	@Override
	public boolean equals(Object other) {
		if (super.equals(other)) {
			return true;
		}
		
		if (!(other instanceof Location)) {
			// The object isn't even of the correct class.
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
