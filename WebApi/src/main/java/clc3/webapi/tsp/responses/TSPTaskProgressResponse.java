package clc3.webapi.tsp.responses;


import clc3.webapi.tsp.model.TSPTaskProgressDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TSPTaskProgressResponse {
    TSPTaskProgressDetails taskDetails = new TSPTaskProgressDetails();
    Map<Long, TSPTaskProgressDetails> taskConfigProgress = new HashMap<>();
    Map<Long, TSPTaskProgressDetails> taskConfigRunProgress = new HashMap<>();
    List<Integer> bestTour = new ArrayList<>();

    public TSPTaskProgressDetails getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(TSPTaskProgressDetails taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Map<Long, TSPTaskProgressDetails> getTaskConfigProgress() {
        return taskConfigProgress;
    }

    public void setTaskConfigProgress(Map<Long, TSPTaskProgressDetails> taskConfigProgress) {
        this.taskConfigProgress = taskConfigProgress;
    }

    public Map<Long, TSPTaskProgressDetails> getTaskConfigRunProgress() {
        return taskConfigRunProgress;
    }

    public void setTaskConfigRunProgress(Map<Long, TSPTaskProgressDetails> taskConfigRunProgress) {
        this.taskConfigRunProgress = taskConfigRunProgress;
    }

    public List<Integer> getBestTour() {
        return bestTour;
    }

    public void setBestTour(List<Integer> bestTour) {
        this.bestTour = bestTour;
    }
}
