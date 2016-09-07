package core;

import java.util.Collection;

public abstract class State {
	public abstract boolean isGoalState();
	
	public abstract Collection<? extends State> determineChildren();
}
