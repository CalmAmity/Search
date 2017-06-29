package util;

import java.util.Arrays;
import java.util.List;

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
	
	@Test
	public void binarySearch() {
		List<Double> searchList = Arrays.asList(3d, 4d, 17d);
		Assert.assertEquals(0, Util.binarySearch(-2, searchList));
		Assert.assertEquals(1, Util.binarySearch(3.5, searchList));
		Assert.assertEquals(2, Util.binarySearch(13.34876324, searchList));
		Assert.assertEquals(2, Util.binarySearch(17, searchList));
		
		// A list of 100 values between 0 and 1000. No, obviously I did not create this by hand. I am not a barbarian.
		List<Double> searchList2 = Arrays.asList(10d, 18d, 28d, 31d, 31d, 37d, 59d, 93d, 101d, 108d, 121d, 123d, 127d, 127d, 129d, 129d, 145d, 148d, 156d, 162d, 166d, 167d,
				190d, 197d, 216d, 234d, 234d, 236d, 243d, 245d, 246d, 250d, 250d, 276d, 286d, 304d, 327d, 332d, 341d, 391d, 397d, 403d, 405d, 411d, 418d, 426d, 429d, 438d, 439d,
				439d, 452d, 469d, 471d, 472d, 492d, 492d, 500d, 515d, 520d, 521d, 524d, 525d, 534d, 536d, 541d, 551d, 573d, 574d, 583d, 590d, 591d, 612d, 613d, 620d, 629d, 640d,
				664d, 688d, 691d, 704d, 726d, 731d, 748d, 768d, 809d, 811d, 815d, 819d, 831d, 838d, 848d, 865d, 871d, 872d, 880d, 884d, 898d, 907d, 946d, 957d);
		Assert.assertEquals(80, Util.binarySearch(717, searchList2));
		TestUtils.executeMethodCallExpectException(() -> Util.binarySearch(1000, searchList2), IndexOutOfBoundsException.class);
	}
}
