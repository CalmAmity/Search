package local.queens;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import core.Action;

/** Represents a single state in the 8 Queens Problem (generalised to the X Queens Problem). */
public class State implements core.State<State> {
	/** Represents the chess board. Every position in the array corresponds to a column on the board, and the integer in that position indicates the row the queen is in. */
	private int[] board;
	
	private Double qualityScore;

	/**
	 * Creates a valid state with the specified dimensions and random queen placement.
	 * @param dimensions The number of rows and columns the board should have.
	 */
	public State(int dimensions) {
		board = new int[dimensions];
		Random rng = new Random();
		for (int column = 0; column < dimensions; column++) {
			// Place the queen for this column in a randomly selected row.
			board[column] = rng.nextInt(dimensions);
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
	public boolean queensAreClashing(int column1, int column2) {
		// Calculate the horizontal and vertical distances between the queens in the two columns.
		int horizontalDistance = Math.abs(column1 - column2);
		int verticalDistance = Math.abs(board[column1] - board[column2]);
		
		// The queens are clashing if
		//   - the vertical distance is zero (which means that the two queens are in the same row, clashing horizontally), OR
		//   - the vertical distance is equal to the horizontal distance (which means that the queens are clashing diagonally).
		// Thanks to the data structure, this method does not need to check for vertical clashing.
		return verticalDistance == 0 || horizontalDistance == verticalDistance;
	}
	
	/**
	 * Creates an {@link Iterator} that provides all states 'reachable' from this one. In this case, a 'reachable' state is a state produced by moving a single queen to another
	 * position in the same column.
	 */
	public Iterator<Action<State>> createAvailableActionsIterator() {
		return new Iterator<Action<State>>() {
			// The column that the cursor is currently on.
			private int column;
			// The row that the cursor is currently on.
			private int row = -1;
			
			@Override
			public boolean hasNext() {
				// Check if there is an open position after the cursor.
				if (column == board.length - 1
						&& row == board.length - 2
						&& board[column] == board.length - 1) {
					// This is a special case: the queen in the last column is also in the last row. The cursor is on the position immediately preceding this queen (last column,
					// second-to-last row) so no more open positions are available.
					return false;
				}
				
				// In general, another successor state is available if the cursor is not on the very last space of the board.
				return column < board.length - 1 || row < board.length - 1;
			}
			
			@Override
			public Action<State> next() {
				if (!hasNext()) {
					throw new NoSuchElementException("No next successor available.");
				}
				
				// Find the next open position on the board.
				findNextOpenPosition();
				// Move the queen in the current column to the open position, which will result in a different board configuration. Create a new action for it and add it to the
				// list.
				int[] successorBoard = board.clone();
				successorBoard[column] = row;
				return new Action<>(new State(successorBoard), 0);
			}
			
			/** Moves the cursor to the next open position on the board. */
			private void findNextOpenPosition() {
				do {
					nextPosition();
					// Keep going until the current position does not contain an queen.
				} while (board[column] == row);
			}
			
			/** Moves the cursor to the next position on the board. */
			private void nextPosition() {
				row++;
				if (row >= board.length) {
					row = 0;
					column++;
				}
			}
		};
	}
	
	@Override
	public Action<State> randomlySelectAvailableAction() {
		Random rng = new Random();
		
		// Randomly select a queen to move.
		int column = rng.nextInt(board.length);
		// Randomly select a row to move her to.
		int row = rng.nextInt(board.length - 1);
		if (row >= board[column]) {
			// The selected row is equal to or greater than the row the selected queen is in. Move down one row to compensate for this.
			row++;
		}
		
		// Clone the board and move the queen to her new position on this board.
		int[] successorBoard = board.clone();
		successorBoard[column] = row;
		return new Action<>(new State(successorBoard), 0);
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if (super.equals(otherObject)) {
			// This is the same state object.
			return true;
		}
		
		if (!(otherObject instanceof State)) {
			// The other object is not of the same class.
			return false;
		}
		
		// The other object is an instance of this class. The only thing that matters now is the board configuration.
		return Arrays.equals(this.board, ((State) otherObject).board);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(board);
	}
	
	@Override
	public String toString() {
		// Prints the board [0,2,1] as follows:
		// [()    ]
		// [    ()]
		// [  ()  ]
		// This is done to give a close-to-square image of the board, for easier viewing.
		
		StringBuilder result = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			result.append("[");
			
			for (int column : board) {
				result.append(column == row ? "()" : "  ");
			}
			
			result.append("]");
			
			if (row < board.length - 1) {
				result.append("\n");
			}
		}
		
		if (qualityScore != null) {
			result.append("\nQuality: ").append(qualityScore);
		}
		
		return result.toString();
	}
	
	/** @return The length of one side of the board. Because a board in this problem is always square, this length describes the size in both dimensions. */
	public int determineDimensions() {
		return board.length;
	}

	@Override
	public Double getQualityScore() {
		return qualityScore;
	}
	
	@Override
	public void setQualityScore(Double qualityScore) {
		this.qualityScore = qualityScore;
	}
}
