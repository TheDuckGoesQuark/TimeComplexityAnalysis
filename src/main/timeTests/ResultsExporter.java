package main.timeTests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;

/**
 * Created by USER on 07/11/2016.
 * This class contains methods for exporting test results to persistent files, such as csv
 */
public class ResultsExporter {
    private static final String[] CSVHEADINGS = {"Algorithm Name", "Dimensions", "Total Time Elapsed"};
    private String algorithmName;
    private ArrayList<TestDetails> listOfTests = new ArrayList<>();

    public ResultsExporter(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void addTest(TestDetails test) {
        listOfTests.add(test);
    }

    public void saveToCSV(String resultFileName) throws IOException {
        File resultsFile = new File(resultFileName);
        if (resultsFile.exists()) {
            throw new FileAlreadyExistsException("Filename " + resultFileName + " already being used in local directory.");
        }

        FileWriter writer = null;
        try {
            resultsFile.createNewFile();

            writer = new FileWriter(resultsFile);

            StringBuilder sb = new StringBuilder();

            // Writer column headers
            for (int i = 0; i < CSVHEADINGS.length - 1; i++) {
                sb.append(CSVHEADINGS[i]);
                sb.append(",");
            }
            sb.append(CSVHEADINGS[CSVHEADINGS.length - 1]);
            sb.append("\n");
            writer.write(sb.toString());

            for (TestDetails test : listOfTests) {
                sb = new StringBuilder();
                sb.append(test.getAlgorithmUsed());
                sb.append(",");
                sb.append(test.getDimensions());
                sb.append(",");
                sb.append(test.getTotalTime());
                sb.append("\n");
                writer.write(sb.toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed when writing to file.");
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    public String getAlgorithmName() {
        return algorithmName;
    }
}
