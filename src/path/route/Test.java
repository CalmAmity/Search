package path.route;

import util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		
		Random rng = new Random();
		for (Location location : locations) {
			while ((double) location.getConnections().size() / 5 < Math.random()) {
				// Time to create a new link between this node and a random other node.
				int otherLocationIndex = rng.nextInt(locations.size());
				
				// Determine the actual distance between the two locations.
				double actualDistance = location.point.euclideanDistanceTo(locations.get(otherLocationIndex).point);
				// Create a random number between 0.8 and 1.2 to randomise the cost of the action while still taking the actual distance into account.
				double randomisedDistanceModifier = .8 + (Math.random() * .4);
				
				// Link the two locations together with the randomly modified actual distance as the cost value.
				location.link(locations.get(otherLocationIndex), actualDistance * randomisedDistanceModifier);
			}
		}
		
		Visualiser visualiser = new Visualiser(width, height, locations);
		visualiser.setVisible(true);
	}
}
