package local.queens;

import core.Heuristic;

public class NumberOfClashesHeuristic implements Heuristic<State> {
	@Override
	public double determineEstimatedDistanceToGoal(State state) {
		double numberOfClashes = 0;
		// Loop over the columns on the board. 
		for (int columnBeingChecked = 0; columnBeingChecked < state.determineDimensions(); columnBeingChecked++) {
			// Loop over the columns to the right of the current column.
			for (int columnToCheckAgainst = columnBeingChecked + 1; columnToCheckAgainst < state.determineDimensions(); columnToCheckAgainst++) {
				if (state.queensAreClashing(columnBeingChecked, columnToCheckAgainst)) {
					// These two queens are clashing. This board is not a solution.
					numberOfClashes++;
				}
			}
		}
		
		return numberOfClashes;
	}
}
