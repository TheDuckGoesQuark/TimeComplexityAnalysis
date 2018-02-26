package main.timeTests;

/**
 * Created by 150008022
 * Stores the details of a test run using a given algorithm
 */
public class TestDetails {
    private String algorithmUsed;
    private int dimensions;
    private String matrixType;
    private double densityOfA;
    private double densityOfB;
    private long totalTime;

    public TestDetails(String algorithmUsed, int dimensions, String matrixType, double densityOfA, double densityOfB, long totalTime) {
        this.algorithmUsed = algorithmUsed;
        this.dimensions = dimensions;
        this.matrixType = matrixType;
        this.densityOfA = densityOfA;
        this.densityOfB = densityOfB;
        this.totalTime = totalTime;
    }

    String getAlgorithmUsed() {
        return algorithmUsed;
    }

    int getDimensions() {
        return dimensions;
    }

    String getMatrixType() {
        return matrixType;
    }

    public double getDensityOfA() {
        return densityOfA;
    }

    public double getDensityOfB() {
        return densityOfB;
    }

    long getTotalTime() {
        return totalTime;
    }


}
