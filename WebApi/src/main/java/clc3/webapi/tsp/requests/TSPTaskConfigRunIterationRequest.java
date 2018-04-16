package clc3.webapi.tsp.requests;

public class TSPTaskConfigRunIterationRequest {
    Long id;
    int iteration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
}
