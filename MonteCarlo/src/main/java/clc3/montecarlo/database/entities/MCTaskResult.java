package clc3.montecarlo.database.entities;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import at.hagenberg.master.montecarlo.entities.TeamSimulationResult;

import com.googlecode.objectify.Key;

@Entity
public class MCTaskResult {
    @Id
    Long id;
    @Index
    Key<MCTask> mcTask;
    List<TeamSimulationResult> teamResults;
    int calculatedIterations;
    List<Long> computeTimes;

    public static Key<MCTaskResult> key(long id) {
        return Key.create(MCTaskResult.class, id);
    }

    public MCTaskResult() {
    }

    public long getId() {
        return this.id;
    }

    public Key<MCTaskResult> getKey() {
        return MCTaskResult.key(this.id);
    }

    public Key<MCTask> getMcTask() {
        return mcTask;
    }

    public void setMcTask(Key<MCTask> mcTask) {
        this.mcTask = mcTask;
    }

    public List<TeamSimulationResult> getTeamResults() {
        return teamResults;
    }

    public void setTeamResults(List<TeamSimulationResult> teamResults) {
        this.teamResults = teamResults;
    }

    public int getCalculatedIterations() {
        return calculatedIterations;
    }

    public void setCacluatedIterations(int calculatedIterations) {
        this.calculatedIterations = calculatedIterations;
    }

    public List<Long> getComputeTimes() {
        return computeTimes;
    }

    public void setComputeTimes(List<Long> computeTimes) {
        this.computeTimes = computeTimes;
    }
}
