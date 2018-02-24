package main;

import main.structures.IntMatrix;

import java.util.concurrent.ThreadLocalRandom;

public class SampleGenerator {
    private static final int MAX_DIMENSION = 1000;
    private static final int MAX_VALUE = 1000;
    private static final int MIN_VALUE = -1000;

    static IntMatrix createZeroedMatrixSample() {
        int dim = generateRandomDimension();
        return createZeroedMatrixSample(dim);
    }

    static IntMatrix createZeroedMatrixSample(int dim) {
        IntMatrix intMatrix = new IntMatrix(dim);

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                intMatrix.set(i, j, 0);
            }
        }
        return intMatrix;
    }

    static IntMatrix createRandomMatrixSample() {
        int dim = generateRandomDimension();
        return createRandomMatrixSample(dim);
    }

    static IntMatrix createRandomMatrixSample(int dim) {
        IntMatrix intMatrix = new IntMatrix(dim);

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                intMatrix.set(i, j, generateRandomInteger());
            }
        }
        return intMatrix;
    }

    private static int generateRandomDimension() {
        return ThreadLocalRandom.current().nextInt(0, MAX_DIMENSION);
    }

    private static int generateRandomInteger() {
        return ThreadLocalRandom.current().nextInt(MIN_VALUE, MAX_VALUE);
    }

    private static int generateRandomPositiveInteger() {
        return ThreadLocalRandom.current().nextInt(0, MAX_VALUE);
    }
}
