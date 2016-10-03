package util;

public class Vertex<N extends Node<N>> {
	private N origin;
	private N destination;
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
