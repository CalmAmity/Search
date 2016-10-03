package util;

import java.util.List;

public interface Node<N extends Node<N>> {
	List<Vertex<N>> getConnections();
}
