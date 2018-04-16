package clc3.montecarlo;

import clc3.montecarlo.database.daos.*;
import clc3.montecarlo.queues.MonteCarloQueue;

public class ServiceLocator {
    private static final ServiceLocator instance_ = new ServiceLocator();

    private ServiceLocator() {}
    public static ServiceLocator getInstance() { return instance_; }

    // queues
    private MonteCarloQueue monteCarloQueue_;
    public MonteCarloQueue getMonteCarloQueue() {
        if (monteCarloQueue_ == null) { monteCarloQueue_ = new MonteCarloQueue(); }
        return monteCarloQueue_;
    }

    // daos
    private MCTaskDao mcTaskDao_;
    public MCTaskDao getMcTaskDao() {
        if (mcTaskDao_ == null) { mcTaskDao_ = new MCTaskDao(); }
        return mcTaskDao_;
    }
    
    private MCTaskIterationDao mcTaskIterationDao_;
    public MCTaskIterationDao getMcTaskIterationDao() {
        if (mcTaskIterationDao_ == null) { mcTaskIterationDao_ = new MCTaskIterationDao(); }
        return mcTaskIterationDao_;
    }

    private MCTaskResultDao mcTaskResultDao_;
    public MCTaskResultDao getMcTaskResultDao() {
        if (mcTaskResultDao_ == null) { mcTaskResultDao_ = new MCTaskResultDao(); }
        return mcTaskResultDao_;
    }
}
