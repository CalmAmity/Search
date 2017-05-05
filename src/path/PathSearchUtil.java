package path;

import java.util.Stack;

public class PathSearchUtil {
	public static void printPathToState(path.State<?> state) {
		path.State<?> currentState = state;
		Stack<path.State<?>> goalPath = new Stack<>();
		goalPath.push(currentState);
		while (currentState != null) {
			goalPath.push(currentState);
			currentState = currentState.getPredecessor();
		}
		
		while (!goalPath.isEmpty()) {
			System.out.println("---------");
			System.out.println(goalPath.pop());
		}
	}
}
