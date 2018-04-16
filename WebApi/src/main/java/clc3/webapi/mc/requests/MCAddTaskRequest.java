package clc3.webapi.mc.requests;

import clc3.montecarlo.database.entities.MCSettings;

public class MCAddTaskRequest {
    String name;
    int iterations;
    int concurrentWorkers;
    MCSettings mcSettings;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getIterations() { return iterations; }
    public void setIterations(int iterations) { this.iterations = iterations; }
    public int getConcurrentWorkers() { return concurrentWorkers; }
    public void setConcurrentWorkers(int concurrentWorkers) { this.concurrentWorkers = concurrentWorkers; }
    public MCSettings getMCSettings() { return mcSettings; }
    public void setMCSettings(MCSettings mcSettings) { this.mcSettings = mcSettings; }
}
