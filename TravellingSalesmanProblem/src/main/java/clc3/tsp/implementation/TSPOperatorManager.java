package clc3.tsp.implementation;

import clc3.tsp.implementation.crossover.CrossoverType;
import clc3.tsp.implementation.crossover.CyclicCrossover;
import clc3.tsp.implementation.crossover.MaximalPreservativeCrossover;
import clc3.tsp.implementation.mutation.InversionMutation;
import clc3.tsp.implementation.mutation.MutationType;
import clc3.tsp.implementation.mutation.SwapMutation;
import clc3.tsp.implementation.selection.RandomSelection;
import clc3.tsp.implementation.selection.SelectionType;
import clc3.tsp.implementation.selection.TournamentSelection;
import clc3.common.utils.EnumManager;
import clc3.common.utils.EnumManager.IdDescriptionEntry;
import clc3.tsp.interfaces.operators.Crossover;
import clc3.tsp.interfaces.operators.Mutation;
import clc3.tsp.interfaces.operators.Selection;

import java.util.Set;

public class TSPOperatorManager {
    private EnumManager crossoverManager = new EnumManager();
    private EnumManager mutationManager = new EnumManager();
    private EnumManager selectionManager = new EnumManager();

    public TSPOperatorManager() {
        // crossover operators
        crossoverManager.add(CrossoverType.CYCLIC);
        crossoverManager.add(CrossoverType.MAXIMAL_PRESERVATIVE);
        // mutation operators
        mutationManager.add(MutationType.INVERSION);
        mutationManager.add(MutationType.SWAP);
        // selection operators
        selectionManager.add(SelectionType.RANDOM);
        selectionManager.add(SelectionType.TOURNAMENT);
    }

    public Set<IdDescriptionEntry> getCrossoverTypes() {
        return crossoverManager.getAll();
    }
    public Crossover getCrossover(Integer id) {
        if (id == CrossoverType.CYCLIC.ordinal()) {
            return new CyclicCrossover();
        } else if (id == CrossoverType.MAXIMAL_PRESERVATIVE.ordinal()) {
            return new MaximalPreservativeCrossover();
        }
        return new CyclicCrossover(); // default
    }

    public Set<IdDescriptionEntry> getMutationTypes() {
        return mutationManager.getAll();
    }
    public Mutation getMutation(Integer id) {
        if (id == MutationType.INVERSION.ordinal()) {
            return new InversionMutation();
        } else if (id == MutationType.SWAP.ordinal()) {
            return new SwapMutation(0.3);
        }
        return new InversionMutation(); // default
    }

    public Set<IdDescriptionEntry> getSelectionTypes() {
        return selectionManager.getAll();
    }
    public Selection getSelection(Integer id) {
        if (id == SelectionType.RANDOM.ordinal()) {
            return new RandomSelection();
        } else if (id == SelectionType.TOURNAMENT.ordinal()) {
            return new TournamentSelection(5);
        }
        return new RandomSelection(); // default
    }
}
