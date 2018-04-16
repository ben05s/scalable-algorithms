package clc3.montecarlo.database.enums;

public interface TaskStatus {
    public final static int CREATED = 0;
    public final static int QUEUED = 1;
    public final static int STARTED = 2;
    public final static int DONE = 3;
    public final static int STOPPED = 4;
    public final static int ERROR = 5;
}
