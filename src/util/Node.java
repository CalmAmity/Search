package util;

import java.util.ArrayList;
import java.util.List;

public class Node {
	/** A list of other nodes reachable from this one. */
	protected List<Node> children;
	
	public Node() {
		children = new ArrayList<>();
	}
	
	public List<Node> getChildren() {
		return children;
	}
}
