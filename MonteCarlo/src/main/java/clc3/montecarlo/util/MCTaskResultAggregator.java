package clc3.montecarlo.util;

import java.util.*;

import at.hagenberg.master.montecarlo.entities.TeamResult;
import clc3.montecarlo.database.entities.MCTaskResult;

public final class MCTaskResultAggregator {

    public static MCTaskResult aggregateTaskResults(List<MCTaskResult> taskResults) {
        MCTaskResult aggregatedTaskResult = new MCTaskResult();
        int calculatedIterations = 0;
        List<Long> computeTimes = new ArrayList<>();

        Map<String, TeamResult> aggregatedTeamResults = new HashMap<>();    
        Iterator<MCTaskResult> taskResultIt = taskResults.iterator();
        while(taskResultIt.hasNext()) {
            MCTaskResult taskResult = taskResultIt.next();
            for(at.hagenberg.master.montecarlo.entities.TeamResult teamResult: taskResult.getTeamResults()) {
                TeamResult ts = aggregatedTeamResults.get(teamResult.getTeamName());
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

        List<TeamResult> sortedTeamResult = new ArrayList<>(aggregatedTeamResults.values());
        Collections.sort(sortedTeamResult);

        aggregatedTaskResult.setTeamResults(sortedTeamResult);
        aggregatedTaskResult.setCacluatedIterations(calculatedIterations);
        aggregatedTaskResult.setComputeTimes(computeTimes);
        return aggregatedTaskResult;
    }
}