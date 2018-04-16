package clc3.tsp.database.entities;

import clc3.common.AbstractEntity;
import clc3.common.utils.StringUtils;
import clc3.tsp.database.enums.TaskStatus;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TSPTask extends AbstractEntity<TSPTask> {
    String name;
    @Load Ref<TSPProblem> problem;
    @Index @Load Ref<TSPTaskConfigRunIteration> bestIteration;
    @Index Date created;
    @Index Date started;

    public TSPTask() {
        this.name = StringUtils.EMPTY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public TSPTaskConfigRunIteration getBestIteration() {
        if (bestIteration == null) return null;
        return bestIteration.get();
    }

    public Ref<TSPTaskConfigRunIteration> getBestIterationRef() {
        return bestIteration;
    }

    public void setBestIteration(Ref<TSPTaskConfigRunIteration> bestIteration) {
        this.bestIteration = bestIteration;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }
}