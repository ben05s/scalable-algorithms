package clc3.tsp.interfaces.utils;

import java.util.Random;

public class RNG {
    private static Random random_ = new Random();

    public static int nextInt() {
        return random_.nextInt();
    }

    public static int nextInt(int bound) {
        return random_.nextInt(bound);
    }

    public static double nextDouble() {
        return random_.nextDouble();
    }
}
