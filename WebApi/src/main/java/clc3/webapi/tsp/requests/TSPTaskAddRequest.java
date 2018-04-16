package clc3.webapi.tsp.requests;

public class TSPTaskAddRequest {
    String name;
    long problemId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }
}
