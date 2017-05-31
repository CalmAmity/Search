package util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PointTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void constructor() {
		// Test some valid calls.
		new Point.IntegerPoint(1, 2, 3);
		new Point.DoublePoint(new Double[]{4d, 5d, 6d});
		// Test some invalid calls.
		expectedException.expect(IllegalArgumentException.class);
		new Point.IntegerPoint();
		new Point.DoublePoint(new Double[]{});
	}
	
	@Test
	public void distance() {
		Point.IntegerPoint point1 = new Point.IntegerPoint(1, 7, 3);
		Point.IntegerPoint point2 = new Point.IntegerPoint(5, 3, -9);
		Assert.assertEquals(Integer.valueOf(20), point1.manhattanDistanceTo(point2));
		Assert.assertEquals(5.604078661310774, point1.euclideanDistanceTo(point2), .00000001);
		
		// Check for exceptions when the points are incomparable.
		expectedException.expect(IllegalArgumentException.class);
		Point.IntegerPoint point3 = new Point.IntegerPoint(7, 8);
		point1.manhattanDistanceTo(point3);
		point3.euclideanDistanceTo(point1);
	}
	
	@Test
	public void sum() {
		Point.IntegerPoint point1 = new Point.IntegerPoint(1);
		Assert.assertEquals(Integer.valueOf(-3), point1.sum(-5, 2));
		Point.DoublePoint point2 = new Point.DoublePoint(1d);
		Assert.assertEquals(-17.957, point2.sum(-7.305, -10.652), .00000001);
	}
	
	@Test
	public void absoluteDifference() {
		Point.IntegerPoint point1 = new Point.IntegerPoint(1);
		Assert.assertEquals(Integer.valueOf(7), point1.absoluteDifference(-5, 2));
		Point.DoublePoint point2 = new Point.DoublePoint(1d);
		Assert.assertEquals(3.347, point2.absoluteDifference(-7.305, -10.652), .00000001);
	}
	
	@Test
	public void equals() {
		// Positive cases
		Point.IntegerPoint point1 = new Point.IntegerPoint(34, 7, 1, 57);
		Assert.assertEquals(point1, point1);
		Point.IntegerPoint point2 = new Point.IntegerPoint(34, 7, 1, 57);
		Assert.assertEquals(point2, point1);
		// Points of different number types can still be equal.
		Point.DoublePoint point3 = new Point.DoublePoint(33.99999999, 7d, 1d, 57d);
		Assert.assertEquals(point1, point3);
		Assert.assertEquals(point3, point1);
		// Negative cases
		Point.IntegerPoint point4 = new Point.IntegerPoint(34, 7, 1);
		Assert.assertNotEquals(point1, point4);
		Point.DoublePoint point5 = new Point.DoublePoint(33d, 7d, 1d, 57d);
		Assert.assertNotEquals(point3, point5);
	}
	
	@Test
	public void createRandom2D() {
		Point.DoublePoint point1 = Point.DoublePoint.createRandom2D(1.5, 7.3);
		Assert.assertTrue(point1.determineNrDimensions() == 2);
		Assert.assertTrue(point1.determineCoordinate(0) > 0);
		Assert.assertTrue(point1.determineCoordinate(0) < 1.5);
		Assert.assertTrue(point1.determineCoordinate(1) > 0);
		Assert.assertTrue(point1.determineCoordinate(1) < 7.3);
	}
}
