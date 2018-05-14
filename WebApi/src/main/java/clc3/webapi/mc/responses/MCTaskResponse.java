package clc3.webapi.mc.responses;

import java.util.*;

import clc3.montecarlo.database.entities.*;

public class MCTaskResponse {
    Long id;
    String name;
    int iterations;
    int concurrentWorkers;
    MCSettings mcSettings;
    int status;
    Long created;
    Long started;
    Long completed;
    long duration;

    public MCTaskResponse() {
    }

    public MCTaskResponse(MCTask task) {
        this.id = task.getId();
        this.name = task.getName();
        this.iterations = task.getIterations();
        this.concurrentWorkers = task.getConcurrentWorkers();
        this.mcSettings = task.getMCSettings();
        this.status = task.getStatus();
        this.created = task.getCreated() != null ? task.getCreated().getTime() : null;
        this.started = task.getStarted() != null ? task.getStarted().getTime() : null;
        this.completed = task.getCompleted() != null ? task.getCompleted().getTime() : null;
        this.duration = task.getDuration();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getConcurrentWorkers() {
        return concurrentWorkers;
    }

    public void setConcurrentWorkers(int concurrentWorkers) {
        this.concurrentWorkers = concurrentWorkers;
    }

    public MCSettings geMCSettings() {
        return mcSettings;
    }

    public void setMCSettings(MCSettings mcSettings) {
        this.mcSettings = mcSettings;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getStarted() {
        return started;
    }

    public void setStarted(Long started) {
        this.started = started;
    }

    public Long getCompleted() {
        return completed;
    }

    public void setCompleted(Long completed) {
        this.completed = completed;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
