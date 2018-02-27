package main;

import main.algorithms.BasicMultiplier;
import main.algorithms.IgnoreZeroesBasicMultiplier;
import main.structures.IntMatrix;
import main.timeTests.ResultsExporter;
import main.timeTests.SampleGenerator;
import main.timeTests.TestDetails;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class Main {

    private static final int NUMBER_OF_RUNS_TO_AVERAGE = 3;
    private static final int NUMBER_OF_TESTS = 10;
    private static final double DECREMENT_DENSITY = 0.1;

    private static final ResultsExporter resultsExporter = new ResultsExporter();
    private static final BasicMultiplier basicMultiplier = new BasicMultiplier();
    private static final IgnoreZeroesBasicMultiplier ignoreZeroesBasicMultiplier = new IgnoreZeroesBasicMultiplier();

    public static void main(String[] args) {
        double density = 1.0;
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            // Gives an idea of test progress.
            long loopTimer = System.nanoTime();
            System.out.println(i +" / "+NUMBER_OF_TESTS);
            IntMatrix a500 = SampleGenerator.createSparseMatrixWithDensity(500, density);
            IntMatrix a1000 = SampleGenerator.createSparseMatrixWithDensity(1000, density);
            IntMatrix a1500 = SampleGenerator.createSparseMatrixWithDensity(1500, density);

            IntMatrix b500 = SampleGenerator.createSparseMatrixWithDensity(a500.getDim(), density);
            IntMatrix b1000 = SampleGenerator.createSparseMatrixWithDensity(a1000.getDim(), density);
            IntMatrix b1500 = SampleGenerator.createSparseMatrixWithDensity(a1500.getDim(), density);

            density -= DECREMENT_DENSITY;
            //System.out.println("Dimensions: "+a.getDim());
            //runTests(a, b);
            runTests(a500, b500);
            runTests(a1000, b1000);
            runTests(a1500, b1500);

            System.out.println("TOTAL TIMES: " + (System.nanoTime() - loopTimer) / 1000000000L + " seconds");
        }

        if (args.length > 0) saveResults(args[0], resultsExporter, 0);
        else saveResults("Tests", resultsExporter, 0);

    }

    private static void runTests(IntMatrix a, IntMatrix b) {
        // Runs random tests
        long basicMultiplierTime = 0L;
        long ignoreZerosMultiplierTime = 0L;

        for (int j = 0; j < NUMBER_OF_RUNS_TO_AVERAGE; j++) {
            //basicMultiplierTime += timeBasicMultiplication(a, b);
            ignoreZerosMultiplierTime += timeIgnoreZeroesBasicMultiplication(a,b);

        }

        double densityOfA = SampleGenerator.getDensity(a);
        double densityOfB = SampleGenerator.getDensity(b);

        // Creates TestDetails instance for tests
/*        resultsExporter.addTest(new TestDetails(
                basicMultiplier.toString(),
                a.getDim(),
                SampleGenerator.RANDOM_SPARSE,
                densityOfA, densityOfB, (basicMultiplierTime / NUMBER_OF_RUNS_TO_AVERAGE)
        ));*/
        resultsExporter.addTest(new TestDetails(
                ignoreZeroesBasicMultiplier.toString(),
                a.getDim(),
                SampleGenerator.RANDOM_SPARSE,
                densityOfA, densityOfB, (ignoreZerosMultiplierTime / NUMBER_OF_RUNS_TO_AVERAGE)
        ));
    }

    private static void saveResults(String filename, ResultsExporter results, int runNumber) {
        boolean successful = false;
        String fileNameExt = filename + runNumber + ".csv";
        try {
            results.saveToCSV(fileNameExt);
        } catch (FileAlreadyExistsException e) {
            saveResults(filename, results, runNumber + 1);
            successful = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (successful) {
            System.out.println("File " + fileNameExt + " saved successfully.");
        }
    }

    private static long timeBasicMultiplication(IntMatrix a, IntMatrix b) {
        long startTime = System.nanoTime();
        basicMultiplier.multiply(a, b);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private static long timeIgnoreZeroesBasicMultiplication(IntMatrix a, IntMatrix b) {
        long startTime = System.nanoTime();
        ignoreZeroesBasicMultiplier.multiply(a, b);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
