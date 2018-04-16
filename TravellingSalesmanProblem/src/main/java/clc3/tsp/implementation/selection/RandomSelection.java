package clc3.tsp.implementation.selection;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.Population;
import clc3.tsp.interfaces.operators.Selection;
import clc3.tsp.interfaces.utils.RNG;

public class RandomSelection implements Selection {
    @Override
    public Individual execute(Population population) {
        int index = RNG.nextInt(population.getSize());
        return population.getIndividual(index);
    }
}
