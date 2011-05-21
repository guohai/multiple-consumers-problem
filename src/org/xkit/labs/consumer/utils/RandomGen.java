package org.xkit.labs.consumer.utils;

import java.util.Random;

public class RandomGen {
	public static int from1To10() {
		return (int) Math.floor((new Random().nextFloat()) * 10) + 1;
	}
}
