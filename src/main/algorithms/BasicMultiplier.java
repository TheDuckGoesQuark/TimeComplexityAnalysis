package main.algorithms;

import main.structures.IntMatrix;

public class BasicMultiplier extends BaseMultiplier<IntMatrix> {

    @Override
    public String toString() {
        return "BasicMultiplier";
    }

    @Override
    public IntMatrix multiply(IntMatrix a, IntMatrix b) {
        int dim = a.getDim();
        IntMatrix result = new IntMatrix(dim);

        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                int sum = 0;
                for(int k = 0; k < dim; k++) {
                    sum += a.get(i,k) * b.get(k,j);
                }
                result.set(i,j,sum);
            }
        }

        return result;
    }

}
