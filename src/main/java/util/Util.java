package util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Contains general-purpose utility methods. */
public class Util {
	/** A pre-defined error margin for comparison of floating point numbers. */
	public static final double ERROR_MARGIN_FOR_FLOAT_COMPARISON = 0.000001;
	
	private static Logger log = LoggerFactory.getLogger(Util.class);
	/** The memory usage of the JVM in kB, averaged over all measurements. */
	private static double averageMemoryUsageInKb;
	/** The number of memory measurements that have been performed. */
	private static long nrMemoryMeasurements;
	
	/** Everything is static / everything is cool when you're utilities! */
	private Util() {}
	
	/**
	 * Determines whether two {@code double} values are equal within the margin of error specified by {@link #ERROR_MARGIN_FOR_FLOAT_COMPARISON}.
	 * @param value1 The first value to compare.
	 * @param value2 The second value to compare.
	 * @return {@code true} if the difference between the two values is less than or equal to {@link #ERROR_MARGIN_FOR_FLOAT_COMPARISON}; {@code false} otherwise.
	 */
	public static boolean equalValue(double value1, double value2) {
		return equalValue(value1, value2, ERROR_MARGIN_FOR_FLOAT_COMPARISON);
	}
	
	/**
	 * Determines whether two {@code double} values are equal within a specified margin of error.
	 * @param value1 The first value to compare.
	 * @param value2 The second value to compare.
	 * @param margin The margin of error.
	 * @return {@code true} if the difference between the two values is less than or equal to {@code margin}; {@code false} otherwise.
	 */
	public static boolean equalValue(double value1, double value2, double margin) {
		return Math.abs(value1 - value2) <= Math.abs(margin);
	}
	
	/**
	 * Finds the first value in {@code searchList} that is greater than or equal to {@code searchValue}.
	 * @param searchValue The value to search for.
	 * @param searchList A list of double values, in ascending order. If the values are not in ascending order, the behaviour of this method is undefined.
	 * @return the index of the result value.
	 */
	public static int binarySearch(double searchValue, List<Double> searchList) {
		// Initialise the bounds of the search range as the bounds of entire list. These bounds are both inclusive.
		int beginIndex = 0;
		int endIndex = searchList.size() - 1;
		
		if (searchValue > searchList.get(endIndex)) {
			// The search value is outside of the search list.
			throw new IndexOutOfBoundsException("Search value " + searchValue + " not found in list with maximum value " + searchList.get(endIndex));
		}
		
		while (true) {
			if (beginIndex == endIndex) {
				// The (inclusive) lower and upper bounds are equal, which means that only one possible value remains. Return that value's index.
				return beginIndex;
			}
			
			// Determine the middle of the search range.
			int middleIndex = (beginIndex + endIndex) / 2;
			
			if (searchList.get(middleIndex) < searchValue) {
				// The value in the middle of the search range is lower than the value that is being searched for, which means that it is not a valid result. The position
				// immediately after the middle index is the new lower bound.
				beginIndex = middleIndex + 1;
			} else {
				// The value in the middle of the search range is higher than the value that is being searched for, which means that it is the latest possible valid result. The
				// middle index is the new upper bound.
				endIndex = middleIndex;
			}
		}
	}
	
	/** Measures the current memorage usage of the JVM and updates {@link #averageMemoryUsageInKb} accordingly. */
	public static void measureMemoryUse() {
		// Determine the current memory usage in kB.
		long currentlyInUseMemoryInKb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
		// Reduce the average by the appropriate factor, and increment the measurement counter.
		averageMemoryUsageInKb *= (double) nrMemoryMeasurements / ++nrMemoryMeasurements;
		// Divide the current memory usage by the number of measurements.
		double inc = currentlyInUseMemoryInKb / (double) nrMemoryMeasurements;
		// Increase the average memory usage by the appropriate amount.
		averageMemoryUsageInKb += inc;
	}
	
	/** Writes the average memory usage of the JVM to the log. */
	public static void logMemoryUsage() {
		log.info("Average memory usage (kB): {}", averageMemoryUsageInKb);
	}
}
