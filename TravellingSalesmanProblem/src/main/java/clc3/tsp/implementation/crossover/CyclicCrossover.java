package clc3.tsp.implementation.crossover;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.operators.Crossover;
import clc3.tsp.implementation.Tour;
import clc3.tsp.interfaces.utils.RNG;

public class CyclicCrossover implements Crossover {
    @Override
    public Individual execute(Individual i1, Individual i2) {
        Tour child = new Tour(((Tour)i1).getTSP(), false);
        int[] pathA, pathB, newPath;
        int length, index, city;
        boolean[] indexCopied;

        pathA = ((Tour)i1).getPath();
        pathB = ((Tour)i2).getPath();
        newPath = child.getPath();

        length = pathA.length;
        indexCopied = new boolean[length];
        // copy whole cycle to new tour
        index = RNG.nextInt(length);
        while (!indexCopied[index]) {
            newPath[index] = pathA[index];
            city = pathB[index];
            indexCopied[index] = true;
            // search copied city in second tour
            index = 0;
            while ((index < length) && (pathA[index] != city))
                index++;
        }
        // copy rest of second route to new route
        for (int i = 0; i < length; ++i) {
            if (!indexCopied[i]) {
                newPath[i] = pathB[i];
            }
        }
        child.setPath(newPath);
        return child;
    }
}
