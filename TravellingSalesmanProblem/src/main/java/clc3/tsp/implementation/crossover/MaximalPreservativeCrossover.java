package clc3.tsp.implementation.crossover;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.operators.Crossover;
import clc3.tsp.interfaces.utils.RNG;
import clc3.tsp.implementation.Tour;

public class MaximalPreservativeCrossover implements Crossover {
    @Override
    public Individual execute(Individual i1, Individual i2) {
        Tour child = new Tour(((Tour)i1).getTSP(), false);
        int[] pathA, pathB, newPath;
        int length, breakPoint1, breakPoint2, subtourLength, index;
        boolean[] cityCopied;

        pathA = ((Tour)i1).getPath();
        pathB = ((Tour)i2).getPath();
        newPath = child.getPath();
        length = pathA.length;
        cityCopied = new boolean[length];

        if (length >= 20) {
            breakPoint1 = RNG.nextInt(length - 9);
            subtourLength = 10 + RNG.nextInt((Math.min(length / 2, length - breakPoint1) + 1) - 10);
            breakPoint2 = breakPoint1 + subtourLength - 1;
        } else {
            breakPoint1 = RNG.nextInt(length - 1);
            breakPoint2 = breakPoint1 + 1 + RNG.nextInt(length - (breakPoint1 + 1));
        }

        index = 0;
        for (int i = breakPoint1; i <= breakPoint2; ++i) {
            newPath[index] = pathA[i];
            cityCopied[newPath[index]] = true;
            index++;
        }

        for (int i = 0; i < pathB.length; ++i) {
            if (!cityCopied[pathB[i]]) {
                newPath[index] = pathB[i];
                index++;
            }
        }

        child.setPath(newPath);
        return child;
    }
}
