package clc3.montecarlo.util;

import java.util.*;

import at.hagenberg.master.montecarlo.entities.TeamSimulationResult;
import clc3.montecarlo.database.entities.MCTaskResult;

public final class MCTaskResultAggregator {

    public static MCTaskResult aggregateTaskResults(List<MCTaskResult> taskResults) {
        MCTaskResult aggregatedTaskResult = new MCTaskResult();
        int calculatedIterations = 0;
        List<Long> computeTimes = new ArrayList<>();

        Map<String, TeamSimulationResult> aggregatedTeamResults = new HashMap<>();    
        Iterator<MCTaskResult> taskResultIt = taskResults.iterator();
        while(taskResultIt.hasNext()) {
            MCTaskResult taskResult = taskResultIt.next();
            for(TeamSimulationResult teamResult: taskResult.getTeamResults()) {
                TeamSimulationResult ts = aggregatedTeamResults.get(teamResult.getTeamName());
                if(ts == null) {
                    ts = teamResult;
                } else {
                    ts.aggregate(teamResult);
                }

                calculatedIterations = ts.getTotalSeasons();

                aggregatedTeamResults.put(ts.getTeamName(), ts);
            }
            
            computeTimes.addAll(taskResult.getComputeTimes());
        }

        List<TeamSimulationResult> sortedTeamResult = new ArrayList<>(aggregatedTeamResults.values());
        Collections.sort(sortedTeamResult);

        aggregatedTaskResult.setTeamResults(sortedTeamResult);
        aggregatedTaskResult.setCacluatedIterations(calculatedIterations);
        aggregatedTaskResult.setComputeTimes(computeTimes);
        return aggregatedTaskResult;
    }
}