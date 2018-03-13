package nl.calmamity.search.path.sliding

import java.util.NoSuchElementException

import core.Action
import nl.calmamity.search.path
import nl.calmamity.search.core
import org.apache.commons.lang3.StringUtils

object State {
	val blankTileValue: Int = 0
	
	val randomNumbers = new scala.util.Random()
	
	/**
	  * Creates the solved state for a sliding puzzle with the indicated dimensions.
	  * @param width  The number of tiles in each row of the puzzle.
	  * @param height The number of tiles in each column of the puzzle.
	  * @return
	  */
	def apply(width: Int, height: Int): State = {
		val tiles = for (row <- 0 until height) yield
			for (column <- 0 until width) yield
				(row * width) + column
		
		State(tiles, None, 0)
	}
	
	/**
	  * Creates a state with the specified dimensions and randomises it.
	  *
	  * @param width    The number of tiles in each row of the puzzle.
	  * @param height   The number of tiles in each column of the puzzle.
	  * @param numSteps The number of random moves to make.
	  * @return
	  */
	def apply(width: Int, height: Int, numSteps: Int): State = {
		var state = State(width, height)
		
		for (_ <- 1 to numSteps) {
			state = state.randomMove
		}
		
		state
	}
}

case class State(
	tiles: Seq[Seq[Int]], predecessor: Option[State], cost: Double
) extends path.State[State] with core.State[State] {
	val width: Int = tiles.head.size
	val height: Int = tiles.size
	
	val blankPosition: (Int, Int) = findTilePosition(State.blankTileValue)
	
	/**
	  * Chooses a random move from all possible moves and performs it.
	  * @return A new [[State]] object representing the state after one randomly selected move.
	  */
	def randomMove: State = {
		// Decide (at random) in which direction to slide.
		val possibleMoves = determinePossibleMoves
		val move = State.randomNumbers.shuffle(possibleMoves).head
		performMove(move)
	}
	
	override def createAvailableActionsIterator(): Iterator[Action[State]] = new Iterator[Action[State]]() {
		/** The list of all moves that are available in the current state. */
		val possibleMoves: Seq[Move.Value] = determinePossibleMoves.toSeq
		
		/** The index of the next move in `possibleMoves`. */
		var nextMoveIndex = 0
		
		override def hasNext: Boolean = {
			nextMoveIndex < possibleMoves.size
		}
		
		override def next: Action[State] = {
			if (!hasNext) {
				throw new NoSuchElementException("All available moves have been checked.")
			}
			
			// Get the next move and use it to create an action. Also increment the connection index!
			val availableMove = possibleMoves(nextMoveIndex)
			nextMoveIndex += 1
			new Action[State](performMove(availableMove), 1)
		}
	}
	
	override def randomlySelectAvailableAction: Action[State] = {
		// Determine the moves available from this state.
		val possibleMoves = State.randomNumbers.shuffle(determinePossibleMoves)
		// Select a random move from this list.
		val availableMove = possibleMoves.head
		// Use this move to create an action.
		new Action[State](performMove(availableMove), 1)
	}
	
	def findTilePosition(tile: Int): (Int, Int) = {
		val rowIndex = tiles.indexWhere(_.contains(tile))
		val columnIndex = tiles(rowIndex).indexOf(tile)
		
		if (rowIndex == -1 || columnIndex == -1) {
			throw new NoSuchElementException(s"Tile $tile not present in puzzle:\n$this")
		}
		
		(columnIndex, rowIndex)
	}
	
	/**
	  * Creates a successor state by performing the specified move from the current state.
	  * @param move The move to be made.
	  * @return A new { @link State} object representing the state after the move.
	  */
	def performMove(move: Move.Value): State = {
		val (relativeHorizontalPosition, relativeVerticalPosition) = Move.determineRelativePosition(move)
		val slidingTileX = blankPosition._1 + relativeHorizontalPosition
		val slidingTileY = blankPosition._2 + relativeVerticalPosition
		
		val blankTile = findTileAt(blankPosition._1, blankPosition._2)
		val slidingTile = findTileAt(slidingTileX, slidingTileY)
		
		val updatedTiles = tiles
			// Take the row which currently holds the blank tile, and update it to reflect the fact that the sliding
			// tile now occupies the space formerly occupied by the blank tile.
			.updated(blankPosition._2, tiles(blankPosition._2).updated(blankPosition._1, slidingTile))
			// Do the same the other way around.
			.updated(slidingTileY, tiles(slidingTileY).updated(slidingTileX, blankTile))
		
		State(updatedTiles, Some(this), this.cost + 1)
	}
	
	def findTileAt(x: Int, y: Int): Int = {
		tiles(y)(x)
	}
	
	def determinePossibleMoves: Set[Move.Value] = {
		Move.values.filter(isMovePossible)
	}
	
	def isMovePossible(move: Move.Value): Boolean = {
		// Determine the position of the tile that would be slid as a result of this action.
		val (relativeHorizontalPosition, relativeVerticalPosition) = Move.determineRelativePosition(move)
		val xTile = blankPosition._1 + relativeHorizontalPosition
		val yTile = blankPosition._2 + relativeVerticalPosition
		// Determine whether this tile is within the bounds of the puzzle.
		xTile >= 0 && xTile < width && yTile >= 0 && yTile < height
	}
	
	override def isGoalState: Boolean = {
		// Create the goal state and compare this with it.
		val goalState = State(width, height)
		this.equals(goalState)
	}
	
	override def equals(other: scala.Any): Boolean = {
		other match {
			case otherState: State =>
				// Only the positions of the tiles matter; defer to the implementation of equals for the tile list.
				this.tiles == otherState.tiles
			case _ =>
				// The other object is not of the same type.
				false
		}
	}
	
	override def hashCode(): Int = tiles.hashCode()
	
	override def toString: String = {
		// Determine the maximum number of digits in tile names, which will be used to determine the appropriate number
		// of leading zeroes.
		val maxNrDigits: Int = math.ceil(math.log10(width.toDouble * height)).toInt
		
		tiles.map(_.map(tile => StringUtils.leftPad(tile.toString, maxNrDigits)).mkString("[", "", "]")).mkString("\n")
	}
}
