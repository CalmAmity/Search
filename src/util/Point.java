package util;

import java.util.Arrays;
import java.util.List;

public abstract class Point<T extends Number> {
	List<T> position;
	
	public Point(T... coordinates) {
		if (coordinates.length == 0) {
			throw new IllegalArgumentException("At least one coordinate should be specified.");
		}
		
		position = Arrays.asList(coordinates);
	}
	
	public T manhattanDistanceTo(Point<T> otherPoint) {
		if (this.determineNrDimensions() != otherPoint.determineNrDimensions()) {
			throw new IllegalArgumentException("Number of dimensions does not match.");
		}
		
		T result = null;
		
		for (int coordinate = 0; coordinate < determineNrDimensions(); coordinate++) {
			// TODO take the absolute of the result of the second add() call
			result = add(result, add(this.position.get(coordinate), otherPoint.position.get(coordinate)));
		}
		
		return result;
	}
	
	protected abstract T add(T number1, T number2);
	
	public int determineNrDimensions() {
		return position.size();
	}
	
	public T determineCoordinate(int coordinate) {
		return position.get(coordinate);
	}
	
	public static class IntegerPoint extends Point<Integer> {
		public IntegerPoint(Integer... coordinates) {
			super(coordinates);
		}
		
		@Override
		protected Integer add(Integer number1, Integer number2) {
			return number1 + number2;
		}
	}
	
	public static class DoublePoint extends Point<Double> {
		public DoublePoint(Double... coordinates) {
			super(coordinates);
		}
		
		@Override
		protected Double add(Double number1, Double number2) {
			return number1 + number2;
		}
	}
}
