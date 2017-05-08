package local.queens;

import java.util.ArrayList;
import java.util.Collection;

import core.Action;
import core.Heuristic;

public class State implements core.State<State> {
	/** Represents the chess board. Every position in the array corresponds to a column on the board, and the integer in that position indicates the row the queen is in. */
	private int[] board;
	
	private Double heuristicDistanceFromGoal;

	/**
	 * Creates a valid state with the specified dimensions and random queen placement.
	 * @param dimensions The number of rows and columns the board should have.
	 */
	public State(int dimensions) {
		board = new int[dimensions];
		for (int column = 0; column < dimensions; column++) {
			// Place the queen for this column in a randomly selected row.
			board[column] = (int) (Math.random() * dimensions);
		}
	}
	
	/**
	 * Creates a state based using the provided board configuration.
	 * @param board The board that the state should represent.
	 */
	public State(int[] board) {
		this.board = board;
	}

	@Override
	public boolean isGoalState() {
		// Loop over the columns on the board. 
		for (int columnBeingChecked = 0; columnBeingChecked < board.length; columnBeingChecked++) {
			// Loop over the columns to the right of the current column.
			for (int columnToCheckAgainst = columnBeingChecked + 1; columnToCheckAgainst < board.length; columnToCheckAgainst++) {
				if (queensAreClashing(columnBeingChecked, columnToCheckAgainst)) {
					// These two queens are clashing. This board is not a solution.
					return false;
				}
			}
		}
		
		// All checks succeeded.
		return true;
	}
	
	/**
	 * Checks whether the two indicated queens are clashing.
	 * @param column1 The column containing one of the queens.
	 * @param column2 The column containing the other queen.
	 * @return {@code true} if the two indicated queens are in such a relative position that they threaten each other.
	 */
	boolean queensAreClashing(int column1, int column2) {
		// Calculate the horizontal and vertical distances between the queens in the two columns.
		int horizontalDistance = Math.abs(column1 - column2);
		int verticalDistance = Math.abs(board[column1] - board[column2]);
		
		// The queens are clashing if
		//   - the vertical distance is zero (which means that the two queens are in the same row, clashing horizontally), OR
		//   - the vertical distance is equal to the horizontal distance (which means that the queens are clashing diagonally).
		// Thanks to the data structure, this method does not need to check for vertical clashing.
		return verticalDistance == 0 || horizontalDistance == verticalDistance;
	}
	
	@Override
	public Collection<Action<State>> determineAvailableActions() {
		Collection<Action<State>> availableActions = new ArrayList<>();
		
		for (int column = 0; column < board.length; column++) {
			for (int row = 0; row < board.length; row++) {
				// Move the queen in the current column to the current row. If this results in a different board configuration, create a new action for it and add it to the list.
				if (board[column] != row) {
					int[] successorBoard = board.clone();
					successorBoard[column] = row;
					availableActions.add(new Action<State>(new State(successorBoard), 0));
				}
			}
		}
		
		return availableActions;
	}

	@Override
	public double getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}

	@Override
	public void determineHeuristicDistanceFromGoal(Heuristic<State> heuristic) {
		if (heuristicDistanceFromGoal == null) {
			heuristicDistanceFromGoal = heuristic.determineEstimatedDistanceToGoal(this);
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		for (int row = 0; row < board.length; row++) {
			result += "[";
			
			for (int column = 0; column < board.length; column++) {
				result += board[column] == row ? "()" : "  ";
			}
			
			result += "]";
			
			if (row < board.length - 1) {
				result += "\n";
			}
		}
		
		return result;
	}
	
	public int determineDimensions() {
		return board.length;
	}
}