package clc3.webapi.tsp.model;

import java.util.Date;

public class TSPTaskProgressDetails {
    double progress;
    double bestFitness;
    int status;
    Date started;
    Date completed;

    public TSPTaskProgressDetails() {}

    public TSPTaskProgressDetails(double progress, double bestFitness) {
        this.progress = progress;
        this.bestFitness = bestFitness;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }
}