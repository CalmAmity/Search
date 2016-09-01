package sliding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State extends core.State
{
	public static final Integer blankValue = 0;
	List<List<Integer>> state;
	int width;
	int height;
	int xBlank;
	int yBlank;
	
	public State(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		// Create the solved state.
		xBlank = 0;
		yBlank = 0;
		state = new ArrayList<>(width);
		Integer currentTile = 0;
		for (int row = 0; row < height; row++)
		{
			List<Integer> currentRow = new ArrayList<>(width);
			for (int column = 0; column < width; column++)
			{
				currentRow.add(currentTile);
				currentTile++;
			}
			state.add(currentRow);
		}
	}
	
	public State(State stateToCopy)
	{
		this.width = stateToCopy.getWidth();
		this.height = stateToCopy.getHeight();
		this.xBlank = stateToCopy.getxBlank();
		this.yBlank = stateToCopy.getyBlank();
		state = new ArrayList<>(width);
		for (int row = 0; row < height; row++)
		{
			List<Integer> currentRow = new ArrayList<>(width);
			for (int column = 0; column < width; column++)
			{
				currentRow.add(stateToCopy.findTileAt(column, row));
			}
			state.add(currentRow);
		}
	}
	
	public State randomMove()
	{
		// Decide (at random) in which direction to slide.
		List<Action> possibleActions = determinePossibleActions();
		Action move = possibleActions.get((int) (Math.random() * possibleActions.size()));
		return performAction(move);
	}
	
	public List<State> determineSuccessors()
	{
		List<Action> possibleActions = determinePossibleActions();
		List<State> successors = new ArrayList<>(possibleActions.size());
		for (Action action : possibleActions)
		{
			successors.add(performAction(action));
		}
		
		return successors;
	}
	
	static State createRandom(int width, int height, int nrSteps)
	{
		State newState = new State(width, height);
		for (int step = 0; step < nrSteps; step++)
		{
			newState.randomMove();
		}
		return newState;
	}
	
	public Integer findTileAt(int x, int y)
	{
		return state.get(y).get(x);
	}
	
	private void overwriteTileAt(int x, int y, Integer newTile)
	{
		state.get(y).set(x, newTile);
	}
	
	/**
	 * Switches the tiles at the indicated positions.
	 */
	public void switchTiles(int firstX, int firstY, int secondX, int secondY)
	{
		Integer tileAtFirstPosition = findTileAt(firstX, firstY);
		overwriteTileAt(firstX, firstY, findTileAt(secondX, secondY));
		overwriteTileAt(secondX, secondY, tileAtFirstPosition);
	}
	
	public boolean isActionPossible(Action action)
	{
		// Determine the position of the tile that would be slid as a result of this action.
		int xTile = xBlank + action.relPosX;
		int yTile = yBlank + action.relPosY;
		// Determine whether this tile is within the bounds.
		return xTile >= 0 && xTile < width && yTile >= 0 && yTile < height;
	}
	
	public State performAction(Action action)
	{
		State successor = new State(this);
		successor.switchTiles(xBlank, yBlank, xBlank + action.relPosX, yBlank + action.relPosY);
		successor.xBlank += action.relPosX;
		successor.yBlank += action.relPosY;
		successor.setPredecessor(this);
		return successor;
	}
	
	public List<Action> determinePossibleActions()
	{
		List<Action> possibleActions = new ArrayList<>(4);
		for (Action action : Action.values())
		{
			if (isActionPossible(action))
			{
				possibleActions.add(action);
			}
		}
		
		return possibleActions;
	}
	
	public boolean isGoalState()
	{
		State goalState = new State(width, height);
		return this.equals(goalState);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof State))
		{
			// The object isn't even of the correct class.
			return false;
		}
		
		State otherState = (State) other;
		// Only the contents of state matter; defer to the default implementation of equals for ArrayList and Integer.
		return state.equals(otherState.state);
	}
	
	@Override
	public int hashCode()
	{
		return state.hashCode();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		for (int row = 0; row < height; row++)
		{
			result.append("[");
			for (int column = 0; column < width; column++)
			{
				result.append(findTileAt(column, row));
				if (column < width - 1)
				{
					result.append(", ");
				}
			}
			result.append("]");
			if (row < height - 1)
			{
				result.append("\n");
			}
		}
		
		return result.toString();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getxBlank()
	{
		return xBlank;
	}
	
	public int getyBlank()
	{
		return yBlank;
	}
}
