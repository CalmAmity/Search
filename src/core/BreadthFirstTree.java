package core;

import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthFirstTree extends PathSearch
{
	Queue<State> frontier;
	
	public BreadthFirstTree(sliding.State startState)
	{
		frontier = new ArrayDeque<>();
		frontier.add(startState);
	}
	
	public boolean next()
	{
		State currentState = frontier.poll();
		if (currentState.isGoalState())
		{
			printPathToState(currentState);
			return false;
		}
		frontier.addAll(currentState.determineSuccessors());
		
		return true;
	}
}
