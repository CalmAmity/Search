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
	
	/** Calculates the Manhattan distance between this and another point. */
	public T manhattanDistanceTo(Point<T> otherPoint) {
		if (this.determineNrDimensions() != otherPoint.determineNrDimensions()) {
			throw new IllegalArgumentException("Number of dimensions does not match.");
		}
		
		T result = null;
		for (int coordinate = 0; coordinate < determineNrDimensions(); coordinate++) {
			// Take the absolute difference between the two coordinates (distance along this axis) and add it to the result.
			result = sum(result, absoluteDifference(this.position.get(coordinate), otherPoint.position.get(coordinate)));
		}
		
		return result;
	}
	
	/** Calculates the euclidean distance between this and another point. */
	public double euclideanDistanceTo(Point<T> otherPoint) {
		if (this.determineNrDimensions() != otherPoint.determineNrDimensions()) {
			throw new IllegalArgumentException("Number of dimensions does not match.");
		}
		
		double result = 0;
		for (int coordinate = 0; coordinate < determineNrDimensions(); coordinate++) {
			// Take square of the absolute difference between the two coordinates (distance along this axis) and add it to the result.
			result += Math.pow(absoluteDifference(this.position.get(coordinate), otherPoint.position.get(coordinate)).doubleValue(), 2);
		}
		
		// Take the nth root of the result, where n is the number of dimensions.
		return Math.pow(result, 1 / determineNrDimensions());
	}
	
	/** @return the sum of the two parameters. */
	protected abstract T sum(T number1, T number2);
	
	/** @return the absolute difference between the two parameters. */
	protected abstract T absoluteDifference(T number1, T number2);
	
	/** @return the number of dimensions in the space that this point is defined in. */
	public int determineNrDimensions() {
		return position.size();
	}
	
	/** @return The value of the coordinate of this point in the indicated dimension. */
	public T determineCoordinate(int coordinate) {
		return position.get(coordinate);
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if (super.equals(otherObject)) {
			// The argument is the same object.
			return true;
		}
		
		if (!(otherObject instanceof Point)) {
			// The argument is not an instance of Point.
			return false;
		}
		
		Point otherPoint = (Point) otherObject;
		if (determineNrDimensions() != otherPoint.determineNrDimensions()) {
			// The points have a different number of coordinates, meaning they are not even part of the same space.
			return false;
		}
		
		// Check every coordinate.
		for (int dimension = 0; dimension < this.position.size(); dimension++) {
			if (!this.determineCoordinate(dimension).equals(otherPoint.determineCoordinate(dimension))) {
				// The coordinates for the current dimension differ between the two points.
				return false;
			}
		}
		
		return true;
	}
	
	public static class IntegerPoint extends Point<Integer> {
		public IntegerPoint(Integer... coordinates) {
			super(coordinates);
		}
		
		@Override
		protected Integer sum(Integer number1, Integer number2) {
			return number1 + number2;
		}
		
		@Override
		protected Integer absoluteDifference(Integer number1, Integer number2) {
			return Math.abs(number1 - number2);
		}
	}
	
	public static class DoublePoint extends Point<Double> {
		public DoublePoint(Double... coordinates) {
			super(coordinates);
		}
		
		@Override
		protected Double sum(Double number1, Double number2) {
			return number1 + number2;
		}
		
		@Override
		protected Double absoluteDifference(Double number1, Double number2) {
			return Math.abs(number1 - number2);
		}
		
		/**
		 * Creates a random point within the indicated bounds in a 2-dimensional space.
		 * @param xMax The maximum value of the first coordinate.
		 * @param yMax The maximum value of the second coordinate.
		 * @return A random point within the rectangle bounded by {@code (0, 0)} and {@code (xMax, yMax)}.
		 */
		public static DoublePoint createRandom2D(double xMax, double yMax) {
			return new DoublePoint(Math.random() * xMax, Math.random() * yMax);
		}
	}
}
