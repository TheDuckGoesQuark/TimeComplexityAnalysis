package main;

import main.algorithms.BaseMultiplier;
import main.algorithms.BasicMultiplier;
import main.algorithms.IgnoreZeroesBasicMultiplier;
import main.algorithms.StrassenMultiplier;
import main.structures.IntMatrix;
import main.timeTests.ResultsExporter;
import main.timeTests.SampleGenerator;
import main.timeTests.TestDetails;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class Main {

    private static final int NUMBER_OF_RUNS_TO_AVERAGE = 3;
    private static final int NUMBER_OF_TESTS = 100;
    private static final double INCREMENT_DENSITY = 0.2;
    private static final int INCREMEMENT_DIMENSION = 25;


    private static final ResultsExporter resultsExporter = new ResultsExporter();

    private static final BasicMultiplier basicMultiplier = new BasicMultiplier();
    private static final IgnoreZeroesBasicMultiplier ignoreZeroesBasicMultiplier = new IgnoreZeroesBasicMultiplier();
    private static final StrassenMultiplier strassenMultiplier = new StrassenMultiplier();

    public static void main(String[] args) {
        int dimensions = 5;

        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            // Gives an idea of test progress.
            long loopTimer = System.nanoTime();
            System.out.println(i +" / "+NUMBER_OF_TESTS);

            double density = 0.0;
            for (int j = 0; j <= 5; j++) {
                IntMatrix a = SampleGenerator.createSparseMatrixWithDensity(dimensions, density);
                IntMatrix b = SampleGenerator.createSparseMatrixWithDensity(a.getDim(), density);

                System.out.println("Dimensions: "+a.getDim());

                runTests(a, b);
                density += INCREMENT_DENSITY;
            }
            System.out.println("TOTAL TIMES: " + (System.nanoTime() - loopTimer) / 1000000000L + " seconds");
            dimensions += INCREMEMENT_DIMENSION;
        }

        if (args.length > 0) saveResults(args[0], resultsExporter, 0);
        else saveResults("Tests", resultsExporter, 0);

    }

    private static void runTests(IntMatrix a, IntMatrix b) {
        // Runs random tests
        long basicMultiplierTime = 0L;
        long ignoreZerosMultiplierTime = 0L;
        long strassenMultiplierTime = 0L;

        for (int j = 0; j < NUMBER_OF_RUNS_TO_AVERAGE; j++) {
            basicMultiplierTime += timeMultiplier(a, b, basicMultiplier.toString());
            ignoreZerosMultiplierTime += timeMultiplier(a, b, ignoreZeroesBasicMultiplier.toString());
            //strassenMultiplierTime += timeMultiplier(a, b, strassenMultiplier.toString());
        }

        double densityOfA = SampleGenerator.getDensity(a);
        double densityOfB = SampleGenerator.getDensity(b);

        // Creates TestDetails instance for tests
        resultsExporter.addTest(new TestDetails(
                basicMultiplier.toString(),
                a.getDim(),
                SampleGenerator.CONTROLLED_SPARSE,
                densityOfA, densityOfB, (basicMultiplierTime / NUMBER_OF_RUNS_TO_AVERAGE)
        ));
        resultsExporter.addTest(new TestDetails(
                ignoreZeroesBasicMultiplier.toString(),
                a.getDim(),
                SampleGenerator.CONTROLLED_SPARSE,
                densityOfA, densityOfB, (ignoreZerosMultiplierTime / NUMBER_OF_RUNS_TO_AVERAGE)
        ));
/*        resultsExporter.addTest(new TestDetails(
                strassenMultiplier.toString(),
                a.getDim(),
                SampleGenerator.RANDOM_POWER_OF_TWO,
                densityOfA, densityOfB, (strassenMultiplierTime / NUMBER_OF_RUNS_TO_AVERAGE)
        ));*/
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

    private static long timeMultiplier(IntMatrix a, IntMatrix b, String multiplierName) {

        BaseMultiplier<IntMatrix> multiplier;

        if (multiplierName.equals(basicMultiplier.toString())) {
            // BASIC
            multiplier = basicMultiplier;
        } else if (multiplierName.equals(ignoreZeroesBasicMultiplier.toString())) {
            // IGNORE ZEROES
            multiplier = ignoreZeroesBasicMultiplier;
        } else if (multiplierName.equals(strassenMultiplier.toString())) {
            // STRASSEN
            multiplier = strassenMultiplier;
        } else throw new IllegalArgumentException("Must be valid multiplier");

        // perform time test
        long startTime = System.nanoTime();
        multiplier.multiply(a, b);
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
