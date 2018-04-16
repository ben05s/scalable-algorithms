package clc3.webapi.mc.responses;

import clc3.montecarlo.database.entities.MCSeasonResult;

public class MCTaskIterationResponse {
    int iteration;
    MCSeasonResult seasonResult;
 
    public int getIteration() { return iteration; }
    public void setIteration(int iteration) { this.iteration = iteration; }
    public MCSeasonResult getSeasonResult() {
        return seasonResult;
    }
    public void setSeasonResult(MCSeasonResult seasonResult) {
        this.seasonResult = seasonResult;
    }
}