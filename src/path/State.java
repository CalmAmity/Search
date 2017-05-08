package path;

/** Defines shared methods for states in path search algorithms. */
public interface State<S extends State<S>> extends core.State<S> {
	/** @return the state immediately preceding this one in the path currently being explored. */
	S getPredecessor();
	
	/** Updates the state immediately preceding this one in the path currently being explored. */
	void setPredecessor(S predecessor);
	
	/** @return the cost of reaching this state from the starting state along the path of predecessors. */
	double getCost();
	
	/** Updates the cost of reaching this state. */
	void setCost(double cost);
}
