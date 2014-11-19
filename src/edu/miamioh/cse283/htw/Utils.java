package edu.miamioh.cse283.htw;

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
}
