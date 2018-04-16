package clc3.webapi.mc.responses;

public class MCTaskProgressResponse {
    double progress;
    long duration;
    int taskStatus;

    public MCTaskProgressResponse(double progress, long duration, int taskStatus) {
        this.progress = progress;
        this.duration = duration;
        this.taskStatus = taskStatus;
    }
}