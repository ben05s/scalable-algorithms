package clc3.webapi.tsp.responses;

import clc3.webapi.tsp.model.TSPProblem;
import clc3.webapi.tsp.model.TSPTaskConfigRunIteration;

import java.util.Date;

public class TSPTaskResponse {
    Long id;
    String name;
    TSPProblem problem;
    TSPTaskConfigRunIteration bestIteration;
    Date created;
    Date started;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TSPProblem getProblem() {
        return problem;
    }

    public void setProblem(TSPProblem problem) {
        this.problem = problem;
    }

    public TSPTaskConfigRunIteration getBestIteration() {
        return bestIteration;
    }

    public void setBestIteration(TSPTaskConfigRunIteration bestIteration) {
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
