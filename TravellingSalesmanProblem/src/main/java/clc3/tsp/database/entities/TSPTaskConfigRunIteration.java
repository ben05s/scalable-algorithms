package clc3.tsp.database.entities;

import clc3.common.AbstractEntity;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TSPTaskConfigRunIteration extends AbstractEntity<TSPTaskConfigRunIteration> {
    @Index Ref<TSPTaskConfigRun> taskConfigRun;
    @Index int iteration;
    @Index double fitness;
    List<Integer> tour;

    public TSPTaskConfigRunIteration() { this.tour = new ArrayList<>(); }

    public TSPTaskConfigRun getTaskConfigRun() {
        if (taskConfigRun == null) return null;
        return taskConfigRun.get();
    }

    public Ref<TSPTaskConfigRun> getTaskConfigRunRef() {
        return taskConfigRun;
    }

    public void setTaskConfigRun(Ref<TSPTaskConfigRun> taskConfigRun) {
        this.taskConfigRun = taskConfigRun;
    }

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

    public void setTour(List<Integer> tour) {
        this.tour = tour;
    }
}
