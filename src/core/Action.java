package core;

public class Action<S extends State<S>> {
	private S resultingState;
	private double cost;
	
	public Action(S resultingState, double cost) {
		this.resultingState = resultingState;
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		return "Resulting state:\n" + resultingState + "\nCost: " + cost;
	}
	
	public S getResultingState() {
		return resultingState;
	}
	
	public double getCost() {
		return cost;
	}
}
