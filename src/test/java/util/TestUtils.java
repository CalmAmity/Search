package util;

import org.junit.Assert;

/** Contains utility methods specifically for the automated tests. This class does <strong>not</strong> perform any tests itself! */
public class TestUtils {
	/**
	 * Executes a method call and checks that an expected exception is thrown.
	 * @param methodCall The method call to execute.
	 * @implNote TODO: Once JUnit 5 is available, this method can probably be replaced with calls to Assertions.assertThrows().
	 */
	public static <T extends Throwable> void executeMethodCallExpectException(Runnable methodCall, Class<T> exceptionType) {
		try {
			// Execute the method call.
			methodCall.run();
		} catch (Throwable exception) {
			if (!exceptionType.isAssignableFrom(exception.getClass())) {
				Assert.fail("Expected an instance of (a subclass of) " + exceptionType.getSimpleName() + " but a(n) " + exception.getClass().getSimpleName() + " was thrown.");
			}
			
			return;
		}
		
		// No exceptions were thrown.
		Assert.fail("Expected a(n) " + exceptionType.getSimpleName() + " but no exception was thrown.");
	}
}
