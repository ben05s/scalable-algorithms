package clc3.montecarlo.database.entities;

import clc3.montecarlo.database.enums.TaskStatus;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.*;

@Entity
public class MCTask {
    @Id
    Long id;
    String name;
    int iterations;
    int concurrentWorkers;
    MCSettings mcSettings;
    int status;
    @Index
    Date created;
    Date started;
    Date completed;
    long duration;
    List<String> queueTaskNames;

    public static Key<MCTask> key(long id) {
        return Key.create(MCTask.class, id);
    }

    public MCTask() {
        status = TaskStatus.CREATED;
    }

    public long getId() {
        return this.id;
    }

    public Key<MCTask> getKey() {
        return MCTask.key(this.id);
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

    public MCSettings getMCSettings() {
        return mcSettings;
    }

    public void setMCSettings(MCSettings mcSettings) {
        this.mcSettings = mcSettings;
    }

    public List<String> getQueueTaskNames() {
        return queueTaskNames;
    }

    public void setQueueTaskNames(List<String> queueTaskNames) {
        this.queueTaskNames = queueTaskNames;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}