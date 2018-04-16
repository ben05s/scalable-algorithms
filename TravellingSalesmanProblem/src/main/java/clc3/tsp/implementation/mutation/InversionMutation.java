package clc3.tsp.implementation.mutation;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.operators.Mutation;
import clc3.tsp.interfaces.utils.RNG;
import clc3.tsp.implementation.Tour;

public class InversionMutation implements Mutation {
    @Override
    public void execute(Individual individual) {
        Tour tour = (Tour)individual;

        int[] oldPath = tour.getPath();
        int[] newPath = oldPath.clone();

        int breakPoint1 = RNG.nextInt(oldPath.length - 1);
        int breakPoint2 = breakPoint1 + 1 + RNG.nextInt(oldPath.length - (breakPoint1 + 1));

        // inverse tour between breakpoints
        for (int i = 0; i <= (breakPoint2 - breakPoint1); ++i) {
            newPath[breakPoint1 + i] = oldPath[breakPoint2 - i];
        }

        tour.setPath(newPath);
    }
}
