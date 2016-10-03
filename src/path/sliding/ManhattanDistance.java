package path.sliding;

import core.Heuristic;
import util.Point;

public class ManhattanDistance implements Heuristic<State> {
	@Override
	public double determineEstimatedDistanceToGoal(State state) {
		int nrTiles = state.getWidth() * state.getHeight();
		State goalState = new State(state.getWidth(), state.getHeight());
		int totalDistance = 0;
		for (int currentTile = 0; currentTile < nrTiles; currentTile++) {
			Point.IntegerPoint positionInGoalState = goalState.findTilePosition(currentTile);
			totalDistance += positionInGoalState.manhattanDistanceTo(state.findTilePosition(currentTile));
		}
		return totalDistance;
	}
}
