package clc3.tsp.interfaces.operators;

import clc3.tsp.interfaces.Individual;

public interface Crossover {
    Individual execute(Individual i1, Individual i2);
}