package main;

import main.algorithms.BasicMultiplier;
import main.timeTests.ResultsExporter;
import main.timeTests.TestDetails;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class Main {

    private static final int NUMBER_OF_RUNS_TO_AVERAGE = 3;
    private static final int NUMBER_OF_TESTS = 100;

    private static final ResultsExporter basicMultiplierExporter = new ResultsExporter("BasicMultiplier");
    private static final BasicMultiplier basicMultiplier = new BasicMultiplier();

    public static void main(String[] args) {

        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            long loopTimer = System.nanoTime();
            // Gives an idea of test progress.
            System.out.println(i);

            // Creates  random list of samples to run through, and sorted version for comparison
            IntMatrix a = SampleGenerator.createZeroedMatrixSample();
            IntMatrix b = SampleGenerator.createZeroedMatrixSample(a.getDim());
            runTests(a, b);

            System.out.println("TOTAL TIMES: " + (System.nanoTime() - loopTimer) / 1000000000L + " seconds");
        }
        saveResults(basicMultiplierExporter, 0);
    }

    private static void runTests(IntMatrix a, IntMatrix b) {
        // Runs random tests
        long basicMultiplierTime = 0L;

        for (int j = 0; j < NUMBER_OF_RUNS_TO_AVERAGE; j++) {
            basicMultiplierTime += timeBasicMultiplication(a, b);
        }

        // Creates TestDetails instance for tests
        basicMultiplierExporter.addTest(new TestDetails(
                "BasicMultiplier",
                a.getDim(),
                (basicMultiplierTime / NUMBER_OF_RUNS_TO_AVERAGE)
        ));
    }

    private static void saveResults(ResultsExporter results, int runNumber) {
        boolean successful = true;
        String fileName = results.getAlgorithmName() + runNumber + ".csv";
        try {
            results.saveToCSV(fileName);
        } catch (FileAlreadyExistsException e) {
            saveResults(results, runNumber + 1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            successful = false;
        }
        if (successful) {
            System.out.println("File " + fileName + " saved successfully.");
        }
    }

    private static IntMatrix deepCopyIntMatrix(IntMatrix original) {
        IntMatrix copy = new IntMatrix(original.getDim());
        for (int i = 0; i < original.getDim(); i++) {
            for (int j = 0; j < original.getDim(); j++) {
                copy.set(i, j, original.get(i, j));
            }
        }
        return copy;
    }

    private static long timeBasicMultiplication(IntMatrix a, IntMatrix b) {
        long startTime = System.nanoTime();
        basicMultiplier.multiply(a, b);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }


}
