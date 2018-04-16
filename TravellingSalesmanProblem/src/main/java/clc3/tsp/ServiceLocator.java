package clc3.tsp;

import clc3.tsp.database.daos.*;
import clc3.tsp.implementation.TSPOperatorManager;
import clc3.tsp.queues.TSPDeleteQueue;
import clc3.tsp.queues.TSPQueue;

public class ServiceLocator {
    private static final ServiceLocator instance_ = new ServiceLocator();

    private ServiceLocator() {}
    public static ServiceLocator getInstance() { return instance_; }

    // utils
    private TSPOperatorManager tspOperatorManager_;
    public TSPOperatorManager getTspOperatorManager() {
        if (tspOperatorManager_ == null) { tspOperatorManager_ = new TSPOperatorManager(); }
        return tspOperatorManager_;
    }

    // queues
    private TSPQueue tspQueue_;
    public TSPQueue getTspQueue() {
        if (tspQueue_ == null) { tspQueue_ = new TSPQueue(); }
        return tspQueue_;
    }

    private TSPDeleteQueue tspDeleteQueue_;
    public TSPDeleteQueue getTspDeleteQueue() {
        if (tspDeleteQueue_ == null) { tspDeleteQueue_ = new TSPDeleteQueue(); }
        return tspDeleteQueue_;
    }

    // daos
    private TSPProblemDao tspProblemDao_;

    public TSPProblemDao getTspProblemDao() {
        if (tspProblemDao_ == null) { tspProblemDao_ = new TSPProblemDao(); }
        return tspProblemDao_;
    }

    private TSPTaskDao tspTaskDao_;
    public TSPTaskDao getTspTaskDao() {
        if (tspTaskDao_ == null) { tspTaskDao_ = new TSPTaskDao(); }
        return tspTaskDao_;
    }

    private TSPTaskConfigDao tspTaskConfigDao_;
    public TSPTaskConfigDao getTspTaskConfigDao() {
        if (tspTaskConfigDao_ == null) { tspTaskConfigDao_ = new TSPTaskConfigDao(); }
        return tspTaskConfigDao_;
    }

    private TSPTaskConfigRunDao tspTaskConfigRunDao_;
    public TSPTaskConfigRunDao getTspTaskConfigRunDao() {
        if (tspTaskConfigRunDao_ == null) { tspTaskConfigRunDao_ = new TSPTaskConfigRunDao(); }
        return tspTaskConfigRunDao_;
    }

    private TSPTaskConfigRunIterationDao tspTaskConfigRunIterationDao_;
    public TSPTaskConfigRunIterationDao getTspTaskConfigRunIterationDao() {
        if (tspTaskConfigRunIterationDao_ == null) { tspTaskConfigRunIterationDao_ = new TSPTaskConfigRunIterationDao(); }
        return tspTaskConfigRunIterationDao_;
    }


}
