package clc3.montecarlo.servlets.requests;

public class MCProcessRequest {
    Long taskId;
    Integer iterations;

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public Integer getIterations() { return iterations; }
    public void setIterations(Integer iterations) { this.iterations = iterations; }
}
