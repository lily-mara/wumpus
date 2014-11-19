package edu.miamioh.cse283.htw;

import java.util.HashSet;

/**
 * Created by Nate on 2014-11-18.
 */
public class Utils {
	public static int random(int high) {
		return (int) (Math.random() * high);
	}

	public static int random(int low, int high) {
		return (int) (Math.random() * (high - low)) + low;
	}

	/**
	 * Returns an array containing n unique random ints i such that low <= i < high. If high-low < n, an exception will be thrown.
	 *
	 * @param n    number of random elements to generate
	 * @param low  bottom of range of elements to be generated (inclusive)
	 * @param high top of range of elements to be generated (exclusive)
	 * @return array containing n random ints i such that low <= i < high
	 */
	public static int[] nRand(int n, int low, int high) {
		if (high - low < n)
			throw new IllegalArgumentException("n must be larger than range of random values");

		int[] rand = new int[n];
		HashSet<Integer> used = new HashSet<Integer>();

		for (int i = 0; i < n; i++) {
			int choice;
			while (used.contains(choice = random(low, high))) {
			}
			rand[i] = choice;
		}

		return rand;
	}

	public static int[] nRand(int n, int high) {
		return nRand(n, 0, high);
	}
}
