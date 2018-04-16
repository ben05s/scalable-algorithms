package clc3.webapi.tsp.responses;

import clc3.common.utils.EnumManager.IdDescriptionEntry;

import java.util.Set;

public class TSPOperatorsResponse {
    Set<IdDescriptionEntry> crossover;
    Set<IdDescriptionEntry> mutation;
    Set<IdDescriptionEntry> selection;

    public Set<IdDescriptionEntry> getCrossover() {
        return crossover;
    }
    public void setCrossover(Set<IdDescriptionEntry> crossover) {
        this.crossover = crossover;
    }

    public Set<IdDescriptionEntry> getMutation() {
        return mutation;
    }
    public void setMutation(Set<IdDescriptionEntry> mutation) { this.mutation = mutation; }

    public Set<IdDescriptionEntry> getSelection() {
        return selection;
    }
    public void setSelection(Set<IdDescriptionEntry> selection) { this.selection = selection; }
}
