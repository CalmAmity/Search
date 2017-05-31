package path.sliding;

import core.Action;
import util.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class State implements path.State<State> {
	private State predecessor;
	private List<List<Integer>> tiles;
	private int width;
	private int height;
	private int xBlank;
	private int yBlank;
	private int cost;
	private Double heuristicDistanceFromGoal;
	
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
	public State(int width, int height, int nrSteps) {
		this(width, height);
		for (int step = 0; step < nrSteps; step++) {
			randomMove();
		}
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
	public Collection<Action<State>> determineAvailableActions() {
		List<Move> possibleMoves = determinePossibleMoves();
		List<Action<State>> actions = new ArrayList<>(possibleMoves.size());
		actions.addAll(possibleMoves.stream().map(move -> new Action<>(performMove(move), 1)).collect(Collectors.toList()));
		
		return actions;
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
	
	/**
	 * Switches the tiles at the indicated positions.
	 */
	public void switchTiles(int firstX, int firstY, int secondX, int secondY) {
		Integer tileAtFirstPosition = findTileAt(firstX, firstY);
		overwriteTileAt(firstX, firstY, findTileAt(secondX, secondY));
		overwriteTileAt(secondX, secondY, tileAtFirstPosition);
	}
	
	public boolean isMovePossible(Move move) {
		// Determine the position of the tile that would be slid as a result of this action.
		int xTile = xBlank + move.relPosX;
		int yTile = yBlank + move.relPosY;
		// Determine whether this tile is within the bounds.
		return xTile >= 0 && xTile < width && yTile >= 0 && yTile < height;
	}
	
	/**
	 * Performs the specified move from the current state.
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
		StringBuilder result = new StringBuilder();
		for (int row = 0; row < height; row++) {
			result.append("[");
			for (int column = 0; column < width; column++) {
				result.append(findTileAt(column, row));
				if (column < width - 1) {
					result.append(", ");
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
	public Double getHeuristicDistanceFromGoal() {
		return heuristicDistanceFromGoal;
	}
	
	@Override
	public void setHeuristicDistanceFromGoal(Double distance) {
		heuristicDistanceFromGoal = distance;
	}
}
