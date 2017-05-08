package path;

public interface State<S extends State<S>> extends core.State<S> {
	S getPredecessor();
	
	void setPredecessor(S predecessor);
	
	double getCost();
	
	void setCost(double cost);
}
