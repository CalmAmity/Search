package path.route;

import util.Node;
import util.Point;

public class Location extends Node {
	/** The coordinates of this location. */
	protected Point<Double> point;
	
	public Location(Point point) {
		super();
		this.point = point;
	}
	
	public void link(Location other) {
		if (this.getChildren().contains(other)) {
			return;
		}
		
		this.getChildren().add(other);
		other.getChildren().add(this);
	}
	
	public Point<Double> getPoint() {
		return point;
	}
}
