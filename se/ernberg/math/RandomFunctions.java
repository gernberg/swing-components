package se.ernberg.math;

import java.awt.Color;

/**
 * This class helps with some standard random operations
 * 
 * @author Gustav Ernberg <gustav.ernberg@gmail.com>
 */
public class RandomFunctions {
	/**
	 * Generates a new random color
	 * 
	 * @param min
	 *            rgb value
	 * @param max
	 *            rgb value
	 * @return
	 */
	public static Color randomColor(int min, int max) {
		return randomColor(min, max, max, max);
	}

	/**
	 * Generates a new random color with the possibilitiy to define the max
	 * amount of each color(red, green, blue)
	 * 
	 * @param min
	 * @param rmax
	 * @param gmax
	 * @param bmax
	 * @return
	 */
	public static Color randomColor(int min, int rmax, int gmax, int bmax) {
		return new Color(RandomFunctions.randomRange(min, rmax),
				RandomFunctions.randomRange(min, gmax),
				RandomFunctions.randomRange(min, bmax));
	}

	/**
	 * Generates a number in the given range Example usage: randomRange(3,7)
	 * returns one of the following numbers: 3,4,5,6,7
	 * 
	 * @param min
	 *            (inclusive)
	 * @param max
	 *            (inclusive)
	 * @return
	 */
	public static int randomRange(int min, int max) {

		return (int) (min + Math.random() * (1 + max - min));
	}
}