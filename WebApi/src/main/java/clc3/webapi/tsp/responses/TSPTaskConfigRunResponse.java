package clc3.webapi.tsp.responses;

import clc3.webapi.tsp.model.TSPTaskConfigRunIteration;

import java.util.Date;
import java.util.List;

public class TSPTaskConfigRunResponse {
    long id;
    long configId;
    TSPTaskConfigRunIteration latestIteration;
    int maxIteration;
    int status;
    Date started;
    Date completed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public TSPTaskConfigRunIteration getLatestIteration() {
        return latestIteration;
    }

    public void setLatestIteration(TSPTaskConfigRunIteration latestIteration) {
        this.latestIteration = latestIteration;
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
