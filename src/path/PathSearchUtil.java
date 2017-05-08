package path;

import java.util.Stack;

/** Utility class that provides support methods for path search algorithms. */
public final class PathSearchUtil {
	// This class is not meant to be instantiated.
	private PathSearchUtil() {};
	
	/**
	 * Takes a state for a path search algorithm and prints the path from the start state to that state to the console. The path is determined using {@link State#getPredecessor()}.
	 * @param state
	 */
	public static void printPathToState(path.State<?> state) {
		// Create a stack to represent the path to this state and push the state itself onto it.
		path.State<?> currentState = state;
		Stack<path.State<?>> goalPath = new Stack<>();
		goalPath.push(currentState);
		
		// Moving from a predecessor to its predecessor, push all states onto the stack, ending with the starting state. 
		while (currentState != null) {
			goalPath.push(currentState);
			currentState = currentState.getPredecessor();
		}
		
		// Pop each state from the stack and print it to the console, starting with the starting state and ending with the provided state.
		while (!goalPath.isEmpty()) {
			System.out.println("---------");
			System.out.println(goalPath.pop());
		}
	}
}
