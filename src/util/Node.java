package util;

import java.util.List;

/** Defines methods common to classes that represent nodes in a graph. */
public interface Node<N extends Node<N>> {
	/** @return all connections from this node to other nodes. */
	List<Vertex<N>> getConnections();
}
