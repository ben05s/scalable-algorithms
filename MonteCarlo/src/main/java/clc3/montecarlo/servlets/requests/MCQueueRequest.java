package clc3.montecarlo.servlets.requests;

public class MCQueueRequest {
    Long taskId;
    Long iterationStart;
    Long iterationEnd;
    
    public MCQueueRequest() {}

    public MCQueueRequest(Long taskId, Long iterationStart, Long iterationEnd) {
        this.taskId = taskId;
        this.iterationStart = iterationStart;
        this.iterationEnd = iterationEnd;
    }

    public Long getTaskId() { return this.getTaskId(); }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public Long getIterationStart() { return iterationStart; }
    public void setIterationStart(Long iterationStart) { this.iterationStart = iterationStart; }
    public Long getIterationEnd() { return iterationEnd; }
    public void setIterationEnd(Long iterationEnd) { this.iterationEnd = iterationEnd; }
}
