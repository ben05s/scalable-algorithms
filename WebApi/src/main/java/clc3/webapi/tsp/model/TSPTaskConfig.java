package clc3.webapi.tsp.model;

public class TSPTaskConfig {
    long taskConfigId;
    long taskId;
    int selection;
    int crossover;
    int mutation;
    double mutationRate;
    boolean elitism;
    int populationSize;
    int iterations;
    int runs;

    public long getTaskConfigId() { return taskConfigId; }

    public void setTaskConfigId(long taskConfigId) { this.taskConfigId = taskConfigId; }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public int getCrossover() {
        return crossover;
    }

    public void setCrossover(int crossover) {
        this.crossover = crossover;
    }

    public int getMutation() {
        return mutation;
    }

    public void setMutation(int mutation) {
        this.mutation = mutation;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public boolean isElitism() {
        return elitism;
    }

    public void setElitism(boolean elitism) {
        this.elitism = elitism;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }
}
