package se.ernberg.math;

import java.awt.Color;

public class RandomFunctions {
	public static Color randomColor(int min, int max){
		return randomColor(min, max, max, max);
	}
	public static Color randomColor(int min, int rmax, int gmax, int bmax){
		return new Color(RandomFunctions.randomRange(min, rmax), RandomFunctions
				.randomRange(min, gmax), RandomFunctions.randomRange(min, bmax));
	}
	public static int randomRange(int min, int max) {
		
		return (int) (min + Math.random() * (1 + max - min));
	}
}