package path;

public interface State<S extends State<S>> extends core.State<S> {
	State getPredecessor();
	
	void setPredecessor(S predecessor);
	
	double getCost();
	
	void setCost(double cost);
}
