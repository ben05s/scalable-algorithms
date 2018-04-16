package clc3.tsp.implementation;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.utils.RNG;

public class Tour implements Individual<int[]> {
    private TSP tsp_;
    private int[] path_;
    private double fitness_ = Double.MAX_VALUE;

    public Tour(TSP problem, boolean shuffle) {
        int cities = problem.getCities().length;
        tsp_ = problem;
        path_ = new int[cities];
        for (int i = 0; i < cities; ++i) {
            path_[i] = i;
        }
        if (shuffle) {
            int index, tmp;
            for (int i = 0; i < cities - 1; ++i) {
                index = i + RNG.nextInt(cities - i);
                tmp = path_[i];
                path_[i] = path_[index];
                path_[index] = tmp;
            }
        }
    }

    public TSP getTSP() { return tsp_; }

    public void setPath(int[] path) { path_ = path; }
    public int[] getPath() { return path_; }

    public void setCity(int index, int city) {
        path_[index] = city;
    }

    public int getCity(int index) {
        return path_[index];
    }

    @Override
    public int[] getData() { return path_; }

    @Override
    public double getFitness() {
        if (fitness_ == Double.MAX_VALUE) {
            fitness_ = calculateFitness();
        }
        return fitness_;
    }

    private double calculateFitness() {
        double fitness = 0;
        int length = path_.length;
        for (int i = 1; i < length; ++i) {
            fitness += tsp_.calculateDistance(path_[i - 1], path_[i]);
        }
        return fitness + tsp_.calculateDistance(path_[length - 1], path_[0]);
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(getFitness(), o.getFitness());
    }
}
