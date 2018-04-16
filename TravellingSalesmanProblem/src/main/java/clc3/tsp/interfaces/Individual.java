package clc3.tsp.interfaces;

public interface Individual<T> extends Comparable<Individual> {
    T getData();
    double getFitness();
}
