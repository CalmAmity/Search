package util;

/** Represents a vertex in a graph. */
public class Vertex<N extends Node<N>> {
	/** The origin node. */
	private N origin;
	/** The destination node. */
	private N destination;
	/** The cost of moving from {@link #origin} to {@link destination}. */
	private double cost;
	
	public Vertex(N origin, N destination, double cost) {
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
	}
	
	public N getOrigin() {
		return origin;
	}
	
	public N getDestination() {
		return destination;
	}
	
	public double getCost() {
		return cost;
	}
}
