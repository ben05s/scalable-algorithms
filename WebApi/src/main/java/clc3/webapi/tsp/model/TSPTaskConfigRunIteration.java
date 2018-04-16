package clc3.webapi.tsp.model;

import java.util.ArrayList;
import java.util.List;

public class TSPTaskConfigRunIteration {
    int iteration;
    double fitness;
    List<Integer> tour = new ArrayList<>();

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public List<Integer> getTour() {
        return tour;
    }

    public void setTour(List<Integer> path) {
        this.tour = path;
    }
}
