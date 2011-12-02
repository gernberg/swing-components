package se.ernberg.math;

public class BetterMath {
	public static int randomRange(int min, int max) {
		
		return (int) (min + Math.random() * (1 + max - min));
	}
}