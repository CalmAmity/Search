package path;

import java.util.Collection;

import core.Action;

public interface State<S extends State<S>> extends core.State<S> {
	Collection<Action<S>> determineAvailableActions();
	
	S getPredecessor();
	
	void setPredecessor(S predecessor);
	
	double getCost();
	
	void setCost(double cost);
}
