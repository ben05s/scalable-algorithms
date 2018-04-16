package clc3.tsp.implementation.mutation;

import clc3.tsp.interfaces.Individual;
import clc3.tsp.interfaces.operators.Mutation;
import clc3.tsp.interfaces.utils.RNG;
import clc3.tsp.implementation.Tour;

public class SwapMutation implements Mutation {
    private double swapRate_;

    public SwapMutation(double swapRate) {
        swapRate_ = swapRate;
    }

    @Override
    public void execute(Individual individual) {
        Tour tour = (Tour)individual;
        int length = tour.getPath().length;
        for (int i = 0; i < length; ++i) {
            if (RNG.nextDouble() < swapRate_) {
                int j = RNG.nextInt(length);

                int cityI = tour.getCity(i);
                int cityJ = tour.getCity(j);

                tour.setCity(j, cityI);
                tour.setCity(i, cityJ);
            }
        }
    }
}
