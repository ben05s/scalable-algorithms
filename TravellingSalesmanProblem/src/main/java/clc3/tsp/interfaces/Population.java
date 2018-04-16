package clc3.tsp.interfaces;

public class Population {
    private Individual[] population_;

    public Population(int size) {
        population_ = new Individual[size];
    }

    public int getSize() { return population_.length; }

    public void replace(Individual[] population) { population_ = population; }

    public Individual getIndividual(int index) { return population_[index]; }
    public void setIndividual(int index, Individual i) { population_[index] = i; }

    public Individual getFittestIndividual() {
        Individual fittest = population_[0];
        int size = population_.length;
        for (int i = 0; i < size; ++i) {
            Individual curIndividual = population_[i];
            if (curIndividual.getFitness() <= fittest.getFitness()) {
                fittest = curIndividual;
            }
        }
        return fittest;
    }
}
