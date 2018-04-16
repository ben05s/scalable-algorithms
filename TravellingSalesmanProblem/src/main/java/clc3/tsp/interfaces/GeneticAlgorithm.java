package clc3.tsp.interfaces;

import clc3.tsp.interfaces.operators.Crossover;
import clc3.tsp.interfaces.operators.Mutation;
import clc3.tsp.interfaces.operators.Selection;
import clc3.tsp.interfaces.utils.RNG;

public class GeneticAlgorithm implements Algorithm {
    private Problem problem_;
    private Crossover crossover_;
    private Mutation mutation_;
    private Selection selection_;
    private boolean elitism_;
    private int populationSize_;
    private double mutationRate_;
    private int iterations_;
    private Population population_;
    private AlgorithmCallback callback_;

    // operators
    @Override public Problem getProblem() { return this.problem_; }
    @Override public void setProblem(Problem p) { this.problem_ = p; }

    @Override public Crossover getCrossover() { return this.crossover_; }
    @Override public void setCrossover(Crossover c) { this.crossover_ = c; }

    @Override public Mutation getMutation() { return this.mutation_; }
    @Override public void setMutation(Mutation m) { this.mutation_ = m; }

    @Override public Selection getSelection() { return this.selection_; }
    @Override public void setSelection(Selection s) { this.selection_ = s; }

    // parameters
    @Override public boolean getElitism() { return this.elitism_; }
    @Override public void setElitism(boolean e) { this.elitism_ = e; }

    @Override public int getPopulationSize() { return this.populationSize_; }
    @Override public void setPopulationSize(int n) { this.populationSize_ = n; }

    @Override public double getMutationRate() { return this.mutationRate_; }
    @Override public void setMutationRate(double rate) { this.mutationRate_ = rate; }

    @Override public int getIterations() { return this.iterations_; }
    @Override public void setIterations(int n) { this.iterations_ = n; }

    // callback
    @Override public void setCallback(AlgorithmCallback callback) { this.callback_ = callback; }

    @Override
    public Individual execute() {
        initialize();
        for (int i = 0; i < iterations_; ++i) {
            iterate(i);
        }
        return population_.getFittestIndividual();
    }

    private void initialize() {
        population_ = new Population(populationSize_);
        for (int i = 0; i < populationSize_; ++i) {
            population_.setIndividual(i, problem_.createRandomIndividual());
        }
    }

    private void iterate(int iteration) {
        int size = populationSize_;
        Individual[] newPopulation = new Individual[size];

        for (int i = 0; i < size; ++i) {
            // selection
            Individual i1 = selection_.execute(population_);
            Individual i2 = selection_.execute(population_);
            // crossover
            Individual newIndividual = crossover_.execute(i1, i2);
            // mutation
            if (mutation_ != null && RNG.nextDouble() < mutationRate_) {
                mutation_.execute(newIndividual);
            }
            newPopulation[i] = newIndividual;
        }

        if (elitism_) {
            newPopulation[RNG.nextInt(size)] = population_.getFittestIndividual();
        }

        // replace old population
        population_.replace(newPopulation);
        if (callback_ != null) {
            callback_.onIterationCompleted(iteration, population_.getFittestIndividual());
        }
    }
}
