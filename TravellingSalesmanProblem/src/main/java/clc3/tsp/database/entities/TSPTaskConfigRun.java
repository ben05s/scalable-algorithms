package clc3.tsp.database.entities;

import clc3.common.AbstractEntity;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

@Entity
public class TSPTaskConfigRun extends AbstractEntity<TSPTaskConfigRun> {
    @Index Ref<TSPTask> task;
    Ref<TSPProblem> problem;
    @Index Ref<TSPTaskConfig> config;
    @Index @Load Ref<TSPTaskConfigRunIteration> lastIteration;
    int maxIteration;
    int status;
    @Index Date started;
    @Index Date completed;

    public TSPTaskConfigRun() { }

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

    public TSPProblem getProblem() {
        if (problem == null) return null;
        return problem.get();
    }

    public Ref<TSPProblem> getProblemRef() {
        return problem;
    }

    public void setProblem(Ref<TSPProblem> problem) {
        this.problem = problem;
    }

    public TSPTaskConfig getConfig() {
        if (config == null) return null;
        return config.get();
    }

    public Ref<TSPTaskConfig> getConfigRef() {
        return config;
    }

    public void setConfig(Ref<TSPTaskConfig> config) {
        this.config = config;
    }

    public TSPTaskConfigRunIteration getLastIteration() {
        if (lastIteration == null) return null;
        return lastIteration.get();
    }

    public Ref<TSPTaskConfigRunIteration> getLastIterationRef() {
        return lastIteration;
    }

    public void setLastIteration(Ref<TSPTaskConfigRunIteration> lastIteration) {
        this.lastIteration = lastIteration;
    }

    public int getMaxIteration() {
        return maxIteration;
    }

    public void setMaxIteration(int maxIteration) {
        this.maxIteration = maxIteration;
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
