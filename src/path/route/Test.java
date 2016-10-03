package path.route;

import java.util.ArrayList;
import java.util.List;

import util.Point;

public class Test {
	public static void main(String[] args) {
		final int nrLocations = 10;
		final int width = 10;
		final int height = 10;
		
		List<Location> locations = new ArrayList<>(nrLocations);
		for (int locationIndex = 0; locationIndex < nrLocations; locationIndex++) {
			Location location = new Location(Point.DoublePoint.createRandom2D(width, height));
			locations.add(location);
		}
		
		for (Location location : locations) {
			while ((double) location.getConnections().size() / 5 < Math.random()) {
				// Time to create a new link between this node and a random other node.
				int otherLocationIndex = (int) ((double) locations.size() * Math.random());
				location.link(locations.get(otherLocationIndex), Math.random() * 10);
			}
		}
		
		Visualiser visualiser = new Visualiser(width, height, locations);
		visualiser.setVisible(true);
	}
}
