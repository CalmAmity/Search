package util;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {
	@Test
	public void equalValue() {
		// Test some positives
		Assert.assertTrue(Util.equalValue(.001d, .001000000001d));
		Assert.assertTrue(Util.equalValue(Math.abs(-1d), .99999999d));
		Assert.assertTrue(Util.equalValue(-.0000002, -.0000005));
		// Test some negatives
		Assert.assertFalse(Util.equalValue(3d, 4d));
		// Test some positives with a custom margin
		Assert.assertTrue(Util.equalValue(3d, 4d, 2d));
		Assert.assertTrue(Util.equalValue(.1d, .2d, .5d));
		// Test some negatives with a custom margin
		Assert.assertFalse(Util.equalValue(0, -.1d, 0.01d));
	}
}
