package path.sliding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import core.Action;
import org.apache.commons.lang3.StringUtils;
import util.Point;

public class State implements path.State<State> {
	private State predecessor;
	private List<List<Integer>> tiles;
	private int width;
	private int height;
	private int xBlank;
	private int yBlank;
	private int cost;
	private Double qualityScore;
	
	public State(int width, int height) {
		this.width = width;
		this.height = height;
		
		// Create the solved state.
		xBlank = 0;
		yBlank = 0;
		tiles = new ArrayList<>(width);
		Integer currentTile = 0;
		for (int row = 0; row < height; row++) {
			List<Integer> currentRow = new ArrayList<>(width);
			for (int column = 0; column < width; column++) {
				currentRow.add(currentTile);
				currentTile++;
			}
			tiles.add(currentRow);
		}
	}
	
	public State(State stateToCopy) {
		this.width = stateToCopy.getWidth();
		this.height = stateToCopy.getHeight();
		this.xBlank = stateToCopy.getxBlank();
		this.yBlank = stateToCopy.getyBlank();
		tiles = new ArrayList<>(width);
		for (int row = 0; row < height; row++) {
			List<Integer> currentRow = new ArrayList<>(width);
			for (int column = 0; column < width; column++) {
				currentRow.add(stateToCopy.findTileAt(column, row));
			}
			tiles.add(currentRow);
		}
	}
	
	/**
	 * Creates a state with the specified dimensions and randomises it.
	 * @param width The desired width, in tiles, of the state.
	 * @param height The desired height, in tiles, of the state.
	 * @param nrSteps The number of random moves to make.
	 */
	public static State createRandomisedState(int width, int height, int nrSteps) {
		State state = new State(width, height);
		for (int step = 0; step < nrSteps; step++) {
			state = state.randomMove();
		}
		
		return state;
	}
	
	/**
	 * Chooses a random move from all possible moves and performs it.
	 * @return A new {@link State} object representing the state after one randomly selected move.
	 */
	public State randomMove() {
		// Decide (at random) in which direction to slide.
		List<Move> possibleMoves = determinePossibleMoves();
		Move move = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
		return performMove(move);
	}
	
	@Override
	public Iterator<Action<State>> createAvailableActionsIterator() {
		return new Iterator<Action<State>>() {
			/** The list of all moves that are available in the current state. */
			final List<Move> possibleMoves = determinePossibleMoves();
			/** The index of the next move in {@link #possibleMoves}. */
			int nextMoveIndex;
			
			@Override
			public boolean hasNext() {
				return nextMoveIndex < possibleMoves.size();
			}
			
			@Override
			public Action<State> next() {
				if (!hasNext()) {
					throw new NoSuchElementException("All available moves have been checked.");
				}
				
				// Get the next move and use it to create an action. Also increment the connection index!
				Move availableMove = possibleMoves.get(nextMoveIndex++);
				return new Action<>(performMove(availableMove), 1);
			}
		};
	}
	
	@Override
	public Action<State> randomlySelectAvailableAction() {
		Random rng = new Random();
		// Determine the moves available from this state.
		final List<Move> possibleMoves = determinePossibleMoves();
		// Select a random move from this list.
		Move availableMove = possibleMoves.get(rng.nextInt(possibleMoves.size()));
		// Use this move to create an action.
		return new Action<>(performMove(availableMove), 1);
	}
	
	public Integer findTileAt(int x, int y) {
		return tiles.get(y).get(x);
	}
	
	public Point.IntegerPoint findTilePosition(Integer tile) {
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (tile.equals(findTileAt(column, row))) {
					return new Point.IntegerPoint(column, row);
				}
			}
		}
		
		return null;
	}
	
	private void overwriteTileAt(int x, int y, Integer newTile) {
		tiles.get(y).set(x, newTile);
	}
	
	/** Switches the tiles at the indicated positions. */
	private void switchTiles(int firstX, int firstY, int secondX, int secondY) {
		Integer tileAtFirstPosition = findTileAt(firstX, firstY);
		overwriteTileAt(firstX, firstY, findTileAt(secondX, secondY));
		overwriteTileAt(secondX, secondY, tileAtFirstPosition);
	}
	
	private boolean isMovePossible(Move move) {
		// Determine the position of the tile that would be slid as a result of this action.
		int xTile = xBlank + move.relPosX;
		int yTile = yBlank + move.relPosY;
		// Determine whether this tile is within the bounds.
		return xTile >= 0 && xTile < width && yTile >= 0 && yTile < height;
	}
	
	/**
	 * Performs the specified move from the current state. This state object is not changed by this operation.
	 * @param move The move to be made.
	 * @return A new {@link State} object representing the state after the move.
	 */
	public State performMove(Move move) {
		State successor = new State(this);
		successor.switchTiles(xBlank, yBlank, xBlank + move.relPosX, yBlank + move.relPosY);
		successor.xBlank += move.relPosX;
		successor.yBlank += move.relPosY;
		successor.setPredecessor(this);
		successor.cost = this.cost + 1;
		return successor;
	}
	
	public List<Move> determinePossibleMoves() {
		List<Move> possibleMoves = new ArrayList<>(4);
		for (Move move : Move.values()) {
			if (isMovePossible(move)) {
				possibleMoves.add(move);
			}
		}
		
		return possibleMoves;
	}
	
	@Override
	public boolean isGoalState() {
		State goalState = new State(width, height);
		return this.equals(goalState);
	}
	
	@Override
	public boolean equals(Object other) {
		if (super.equals(other)) {
			return true;
		}
		
		if (!(other instanceof State)) {
			// The object isn't even of the correct class.
			return false;
		}
		
		State otherState = (State) other;
		// Only the positions of the tiles matter; defer to the default implementation of equals for ArrayList and Integer.
		return tiles.equals(otherState.tiles);
	}
	
	@Override
	public int hashCode() {
		return tiles.hashCode();
	}
	
	@Override
	public String toString() {
		// Determine the maximum number of digits in tile names, which will be used to determine the proper amount of leading zeroes.
		int maxNrDigits = (int) Math.ceil(Math.log10((double) width * height));
		StringBuilder result = new StringBuilder();
		for (int row = 0; row < height; row++) {
			result.append("[");
			for (int column = 0; column < width; column++) {
				result.append(StringUtils.leftPad(String.valueOf(findTileAt(column, row)), maxNrDigits));
				if (column < width - 1) {
					result.append(" ");
				}
			}
			result.append("]");
			if (row < height - 1) {
				result.append("\n");
			}
		}
		
		return result.toString();
	}
	
	@Override
	public State getPredecessor() {
		return predecessor;
	}
	
	@Override
	public void setPredecessor(State predecessor) {
		this.predecessor = predecessor;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getxBlank() {
		return xBlank;
	}
	
	public int getyBlank() {
		return yBlank;
	}
	
	@Override
	public double getCost() {
		return cost;
	}
	
	@Override
	public void setCost(double cost) {
		this.cost = (int) cost;
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
