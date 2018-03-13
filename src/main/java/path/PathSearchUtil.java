package path;

import nl.calmamity.search.path.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

/** Utility class that provides support methods for path search algorithms. */
public final class PathSearchUtil {
	private static Logger log = LoggerFactory.getLogger(PathSearchUtil.class);
	// This class is not meant to be instantiated.
	private PathSearchUtil() {
	}
	
	/**
	 * Takes a state for a path search algorithm and prints the path from the start state to that state to the console. The path is determined using {@link State#getPredecessor()}.
	 * @param state The state at the end of the path to print.
	 */
	public static void printPathToState(State state) {
//		// Create a stack to represent the path to this state and push the state itself onto it.
//		State currentState = state;
//		Deque<State> goalPath = new ArrayDeque<>();
//		goalPath.push(currentState);
//
//		// Moving from a predecessor to its predecessor, push all states onto the stack, ending with the starting state.
//		while (currentState != null) {
//			goalPath.push(currentState);
//			currentState = currentState.predecessor().get();
//		}
//
//		// Pop each state from the stack and print it to the console, starting with the starting state and ending with the provided state.
//		while (!goalPath.isEmpty()) {
//			log.info("\n{}", goalPath.pop());
//		}
	}
}
