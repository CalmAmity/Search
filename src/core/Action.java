package core;

/**
 * Defines an action that can be taken to move to another state.
 * @param <S> The type of state that this action will result in.
 */
public class Action<S extends State<S>> {
	/** The state that this action will reesult in. */
	private S resultingState;
	/** The cost of performing this action. */
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
