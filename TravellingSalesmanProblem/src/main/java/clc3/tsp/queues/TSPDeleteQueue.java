package clc3.tsp.queues;

import clc3.common.AbstractQueue;

public class TSPDeleteQueue extends AbstractQueue {
    public String getQueueName() { return "tsp-delete"; }


    @Override
    public String getWorkerEndpoint() { return "delete"; }

}