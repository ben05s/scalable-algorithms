package clc3.tsp.implementation.selection;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.Population;
import clc3.tsp.interfaces.operators.Selection;
import clc3.tsp.interfaces.utils.RNG;

public class TournamentSelection implements Selection {
    private int tournamentSize_;

    public TournamentSelection(int size) {
        tournamentSize_ = size;
    }

    @Override
    public Individual execute(Population population) {
        Population tournament = new Population(tournamentSize_);
        // randomly select (tournamentSize_) individuals
        for (int i = 0; i < tournamentSize_; ++i) {
            int index = RNG.nextInt(population.getSize()); //int)(Math.random() * population.getSize());
            tournament.setIndividual(i, population.getIndividual(index));
        }
        // return the fittest individual
        return tournament.getFittestIndividual();
    }
}
