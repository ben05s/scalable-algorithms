package clc3.tsp.implementation;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.Problem;

public class TSP implements Problem {
    private City[] cities_;

    public TSP(City[] cities) {
        cities_ = cities;
    }

    public City[] getCities() { return cities_; }

    @Override
    public Individual createRandomIndividual() {
        return new Tour(this, true);
    }

    public double calculateDistance(int a, int b) {
        City cityA = cities_[a];
        City cityB = cities_[b];
        double xDist = Math.abs(cityA.getX() - cityB.getX());
        double yDist = Math.abs(cityA.getY() - cityB.getY());
        return Math.sqrt(xDist*xDist + yDist*yDist);
    }
}
