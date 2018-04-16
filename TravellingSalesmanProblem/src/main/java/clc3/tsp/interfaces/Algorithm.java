package clc3.tsp.interfaces;

import clc3.tsp.interfaces.operators.Crossover;
import clc3.tsp.interfaces.operators.Mutation;
import clc3.tsp.interfaces.operators.Selection;

public interface Algorithm {
    Problem getProblem();
    void setProblem(Problem p);
    // operators
    Crossover getCrossover();
    void setCrossover(Crossover c);
    Mutation getMutation();
    void setMutation(Mutation m);
    Selection getSelection();
    void setSelection(Selection s);
    // parameters
    boolean getElitism();
    void setElitism(boolean e);
    int getPopulationSize();
    void setPopulationSize(int n);
    double getMutationRate();
    void setMutationRate(double rate);
    int getIterations();
    void setIterations(int n);
    // callback
    void setCallback(AlgorithmCallback callback);

    Individual execute();
}