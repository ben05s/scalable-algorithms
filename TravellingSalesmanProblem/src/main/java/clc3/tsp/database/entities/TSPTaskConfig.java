package clc3.tsp.database.entities;

import clc3.common.AbstractEntity;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class TSPTaskConfig extends AbstractEntity<TSPTaskConfig> {
    @Index Ref<TSPTask> task;
    int selection;
    int crossover;
    int mutation;
    double mutationRate;
    boolean elitism;
    int populationSize;
    int iterations;
    int runs;

    public TSPTaskConfig() {}

    public TSPTaskConfig(Ref<TSPTask> task) {
        this.task = task;
    }

    public TSPTask getTask() {
        if (task == null) return null;
        return task.get();
    }

    public Ref<TSPTask> getTaskRef() {
        return task;
    }

    public void setTask(Ref<TSPTask> task) {
        this.task = task;
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
