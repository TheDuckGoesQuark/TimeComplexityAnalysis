package main.algorithms;

import main.structures.IntMatrix;

public class IgnoreZeroesBasicMultiplier extends BaseMultiplier<IntMatrix> {

    @Override
    public String toString() {
        return "IgnoreZeroesBasicMultiplier";
    }

    @Override
    public IntMatrix multiply(IntMatrix a, IntMatrix b) {
        int dim = a.getDim();
        IntMatrix result = new IntMatrix(dim);

        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                if (a.get(i, j) != 0) { // dont perform operations on zeroed elements
                    for (int k = 0; k < dim; k++) {
                        result.set(i,k, result.get(i,k) + (a.get(i, j) * b.get(j, k)));
                    }
                }
            }
        }

        return result;
    }
}