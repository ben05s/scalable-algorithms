package clc3.tsp.interfaces.operators;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.Population;

public interface Selection {
    Individual execute(Population population);
}
