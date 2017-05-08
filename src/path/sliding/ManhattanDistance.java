package path.sliding;

import core.Heuristic;
import util.Point;

/** Implements the Manhattan distance heuristic for sliding puzzles. */
public class ManhattanDistance extends Heuristic<State> {
	@Override
	protected double estimateDistanceToGoal(State state) {
		int nrTiles = state.getWidth() * state.getHeight();
		// Determine the goal state.
		State goalState = new State(state.getWidth(), state.getHeight());
		// The Manhattan distance between two states of the sliding puzzle is the sum of distances of the individual tile from their goal positions.
		int totalDistance = 0;
		for (int currentTile = 0; currentTile < nrTiles; currentTile++) {
			// Calculate the Manhattan distance between the current tile's position and its goal position.
			Point.IntegerPoint positionInGoalState = goalState.findTilePosition(currentTile);
			// Add this distance to the total.
			totalDistance += positionInGoalState.manhattanDistanceTo(state.findTilePosition(currentTile));
		}
		return totalDistance;
	}
}
