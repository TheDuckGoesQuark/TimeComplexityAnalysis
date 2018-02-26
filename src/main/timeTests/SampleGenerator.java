package main.timeTests;

import main.structures.IntMatrix;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SampleGenerator {
    private static final int MAX_DIMENSION = 2500;
    private static final int MAX_VALUE = 1000;
    private static final int MIN_VALUE = -1000;

    public static final String RANDOM_SPARSE = "Random Sparse";
    public static final String RANDOM = "Random";
    public static final String ZEROED = "Zeroed";

    public static IntMatrix createZeroedMatrixSample() {
        int dim = generateRandomDimension();
        return createZeroedMatrixSample(dim);
    }

    public static IntMatrix createZeroedMatrixSample(int dim) {
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

    public static IntMatrix createSparseMatrix() {
        int dim = generateRandomDimension();
        return createSparseMatrix(dim);
    }

    public static IntMatrix createSparseMatrix(int dim) {
        IntMatrix intMatrix = createRandomMatrixSample(dim);
        int nZeroes = generateRandomPositiveInteger(dim * dim);

        // Generate random matrix indices
        Set<Point> set = new HashSet<>();
        do {
            Point test = new Point();
            test.x = generateRandomPositiveInteger(dim);
            test.y = generateRandomPositiveInteger(dim);
            set.add(test);
        } while (set.size() < nZeroes);
        // Randomly distribute zeroes
        Point[] points = set.stream().toArray(Point[]::new);
        for (Point point : points) {
            intMatrix.set(point.x, point.y, 0);
        }
        return intMatrix;
    }

    public static double getDensity(IntMatrix matrix) {
        double nonZeroVals = 0;
        int dim = matrix.getDim();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (matrix.get(i,j) != 0) nonZeroVals++;
            }
        }
        return (nonZeroVals) / ((double) dim * dim);
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

    private static int generateRandomPositiveInteger(int upperBound) {
        return ThreadLocalRandom.current().nextInt(0, upperBound);
    }

}
