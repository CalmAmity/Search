package util;

/** Contains general-purpose utility methods. */
public class Util {
	/** A pre-defined error margin for comparison of floating point numbers. */
	public static final double ERROR_MARGIN_FOR_FLOAT_COMPARISON = 0.000001;
	
	private Util() {
	}
	
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
}
