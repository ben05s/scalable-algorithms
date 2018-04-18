package clc3.webapi.mc.responses;

import java.util.*;

import at.hagenberg.master.montecarlo.entities.TeamSimulationResult;
import clc3.montecarlo.database.entities.MCTaskResult;

public class MCTaskResultResponse {
    List<TeamSimulationResult> teamResults;
    int calculatedIterations;
    List<Long> computeTimes;
    
    public MCTaskResultResponse() { }

    public MCTaskResultResponse(MCTaskResult taskResult) {
        this.teamResults = taskResult.getTeamResults();
        this.calculatedIterations = taskResult.getCalculatedIterations();
        this.computeTimes = taskResult.getComputeTimes();
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
