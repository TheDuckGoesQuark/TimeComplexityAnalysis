package main.timeTests;

/**
 * Created by 150008022
 * Stores the details of a test run using a given algorithm
 */
public class TestDetails {
    private String algorithmUsed;
    private int dimensions;
    private long totalTime;

    public TestDetails(String algorithmUsed, int dimensions, long totalTime) {
        this.algorithmUsed = algorithmUsed;
        this.dimensions = dimensions;
        this.totalTime = totalTime;
    }

    String getAlgorithmUsed() {
        return algorithmUsed;
    }

    int getDimensions() {
        return dimensions;
    }

    long getTotalTime() {
        return totalTime;
    }

}
