package nl.calmamity.search.local.queens

import java.util

import core.Action

import nl.calmamity.search.core

object State {
	val randomNumbers = new scala.util.Random()

	/**
	  * Creates a valid state with the specified dimensions and random queen placement.
	  * @param numDimensions The number of rows and columns the board should have.
	  */
	def apply(numDimensions: Int): State = {
		val board = for (_ <- 1 to numDimensions) yield randomNumbers.nextInt(numDimensions)
		State(board)
	}
}

/**
  * Represents a single state in the 8 Queens Problem (generalised to the X Queens Problem).
  * @param board Represents the chess board. Every position in the array corresponds to a column on the board, and the
  * integer in that position indicates the row the queen is in.
  */
case class State(board: Seq[Int]) extends core.State[State] {
	override def isGoalState: Boolean = {
		val pairsToCheck = for {
			columnBeingChecked <- board.indices
			columnToCheckAgainst <- columnBeingChecked + 1 until board.size
		} yield (columnBeingChecked, columnToCheckAgainst)

		!pairsToCheck.exists {
			case (columnBeingChecked, columnToCheckAgainst) =>
				queensAreClashing(columnBeingChecked, columnToCheckAgainst)
		}
	}

	/**
	  * Checks whether the two indicated queens are clashing.
	  * @param column1 The column containing one of the queens.
	  * @param column2 The column containing the other queen.
	  * @return `true` if the two indicated queens are in a position to threaten each other, `false` otherwise.
	  */
	def queensAreClashing(column1: Int, column2: Int): Boolean = {
		// Calculate the horizontal and vertical distances between the queens in the two columns.
		val horizontalDistance = math.abs(column1 - column2)
		val verticalDistance = math.abs(board(column1) - board(column2))

		// The queens are clashing if
		//   - the vertical distance is zero (which means that the two queens are in the same row, clashing
		//     horizontally), OR
		//   - the vertical distance is equal to the horizontal distance (which means that the queens are clashing
		//     diagonally).
		// Thanks to the data structure, this method does not need to check for vertical clashing.
		verticalDistance == 0 || horizontalDistance == verticalDistance
	}

	/**
	  * Creates an [[Iterator]] that provides all states that can be created by moving a single queen to another
	  * position in the same column.
	  */
	override def createAvailableActionsIterator(): Iterator[Action[State]] = new Iterator[Action[State]]() {
		// The column that the cursor is currently on.
		var column: Int = 0
		// The row that the cursor is currently on.
		var row: Int = -1

		override def hasNext(): Boolean = {
			// Check if there is an open position after the cursor.
			if (column == board.size - 1
					&& row == board.size - 2
					&& board(column) == board.size - 1) {
				// This is a special case: the queen in the last column is also in the last row. The cursor is on
				// the position immediately preceding this queen (last column, second-to-last row) so no more open
				// positions are available.
				false
			} else {
				// In general, another successor state is available if the cursor is not on the very last space of
				// the board.
				column < board.length - 1 || row < board.length - 1
			}
		}

		override def next(): Action[State] = {
			if (!hasNext()) {
				throw new NoSuchElementException("No next successor available.")
			}

			// Find the next open position on the board.
			moveCursorToNextOpenPosition()
			// Move the queen in the current column to the open position, which will result in a different board
			// configuration. Create a new action for it and add it to the list.
			val successorBoard = board.updated(column, row)
			new Action[State](new State(successorBoard), 0)
		}

		/** Moves the cursor to the next open position on the board. */
		def moveCursorToNextOpenPosition(): Unit = {
			do {
				moveCursorToNextPosition()
				// Keep going until the current position does not contain an queen.
			} while (board(column) == row)
		}

		/** Moves the cursor to the next position on the board. */
		def moveCursorToNextPosition(): Unit = {
			row += 1
			if (row >= board.length) {
				row = 0
				column += 1
			}
		}
	}

	override def randomlySelectAvailableAction: Action[State] = {
		// Randomly select a queen to move.
		val column = State.randomNumbers.nextInt(board.size)
		// Randomly select a row to move her to.
		val row = State.randomNumbers.nextInt(board.length - 1) match {
			case value if value < board(column) =>
				value
			case value =>
				// The selected row is equal to or greater than the row the selected queen is in. Move down one row to
				// compensate for this.
				value + 1
		}

		// Clone the board and move the queen to her new position on this board.
		val successorBoard = board.updated(column, row)
		new Action[State](new State(successorBoard), 0)
	}

	override def equals(other: scala.Any): Boolean = {
		other match {
			case otherState: State =>
				this.board == otherState.board
		}
	}

	override def hashCode(): Int = util.Arrays.hashCode(board.toArray)

	override def toString: String = {
		// Prints the board [0,2,1] as follows:
		// [()    ]
		// [    ()]
		// [  ()  ]
		// This is done to give a close-to-square image of the board, for easier viewing.

		val rowsAsStrings = for (row <- board.indices) yield
			board.map {
				column =>
					if (column == row) {
						"()"
					} else {
						"  "
					}
			}
			.mkString("[", "", "]")

		rowsAsStrings.mkString("\n")
	}

	/**
	  * @return The length of one side of the board. Because a board in this problem is always square, this length
	  * describes the size in both dimensions.
	  */
	def determineDimensions: Int = {
		board.length
	}
}
