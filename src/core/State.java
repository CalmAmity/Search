package core;

import java.util.Collection;

public abstract class State
{
	State predecessor;
	
	public abstract boolean isGoalState();
	
	public abstract Collection<? extends State> determineSuccessors();
	
	public State getPredecessor()
	{
		return predecessor;
	}
	
	public void setPredecessor(State predecessor)
	{
		this.predecessor = predecessor;
	}
}
