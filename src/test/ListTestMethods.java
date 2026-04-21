package edu.commonwealthu.mrodriguez.cmsc230.listlab.test;

import edu.commonwealthu.mrodriguez.cmsc230.listlab.OrderedList;
import edu.commonwealthu.mrodriguez.cmsc230.listlab.SalesReporter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static edu.commonwealthu.mrodriguez.cmsc230.test.Assert.assertEquals;
import static edu.commonwealthu.mrodriguez.cmsc230.test.Assert.assertThrows;

/**
 * A class containing unit test methods for List.
 * Only contains a few, to serve as examples.
 */
class ListTestMethods {

    /**
     * A generic test method for evaluating SalesReporter.
     * This method is used by the auto-grader.
     *
     * @param k             The number of sales to report per salesperson.
     * @param expectedLines An array of strings representing the expected output lines.
     */
    public static void salesReportTest(String filename, int k, String[] expectedLines) {
        // Save a reference to the normal System.out
        PrintStream oldOut = System.out;

        // Create an output stream to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Wrap a UTF-8 PrintStream around that byte stream
        try (PrintStream ps = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8)) {
            // Redirect System.out to our byte array
            System.setOut(ps);

            // Invoke the SalesReporter with the specified filename and k
            String[] args = {filename, "" + k};
            SalesReporter.main(args);

            // Restore System.out to the way it was before
            System.setOut(oldOut);

            // Convert the byte array to a String--this lets us capture what SalesReporter printed to System.out
            String result = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

            // Convert that String into an array of lines by splitting on newlines
            String[] actualLines = result.split(System.lineSeparator());

            // Verify that the actual lines match the expected lines
            verifyLines(expectedLines, actualLines);
        } catch (Exception e) {
            // Fail the test if an exception is thrown
            e.printStackTrace();
            throw new AssertionError(e);
        } finally {
            // Restore System.out to the way it was before
            System.setOut(oldOut);
        }
    }

    /**
     * A method to verify the correctness of a sales report.
     * Tries, within reason, to ignore meaningless formatting differences.
     *
     * @param expectedLines The expected (correct) sales report, as an array of lines.
     * @param actualLines   The actual sales report, as an array of lines.
     */
    private static void verifyLines(String[] expectedLines, String[] actualLines) {
        System.out.println("Generated sales report was: " + Arrays.toString(actualLines));

        // Copy the actual lines to an ArrayList.
        ArrayList<String> actualLinesCleaned = new ArrayList<>();
        for (String actualLine : actualLines) {
            String line = actualLine.trim(); // Trim whitespace on either end of the line.
            if (!line.isEmpty()) // Skip blank lines.
                actualLinesCleaned.add(line);
        }

        // Make sure the number of non-empty lines is the same.
        assertEquals(expectedLines.length, actualLines.length);

        // Create a natural, ascending sort of both arrays.
        Collections.sort(actualLinesCleaned);
        Arrays.sort(expectedLines);

        // Compare the actual lines and make sure they're equal.
        for (int i = 0; i < expectedLines.length; ++i) {
            String actual = actualLinesCleaned.get(i);
            String expected = expectedLines[i];
            assertEquals(expected, actual);
        }
    }

    /**
     * An example test method.
     * Constructs an OrderedList, inserts an integer into it, and makes sure it's there.
     */
    static void insertAndGetTest() {
        OrderedList<Integer> list = new OrderedList<>();
        list.insert(6);
        assertEquals(6, list.get(0));
    }

    /**
     * An example test method.
     * Constructs an OrderedList, tries to insert null into it, and makes sure it throws a NullPointerException.
     */
    static void insertNullTest() {
        OrderedList<Integer> list = new OrderedList<>();
        assertThrows(NullPointerException.class, () -> list.insert(null));
    }

    /**
     * An example test to evaluate SalesReporter.
     * Demonstrates the use of salesReportTest.
     */
    static void salesReportExampleTest() {
        String[] expectedLines = {
                "438 William Loman $3841.48 $718.71 $486.68",
                "348 George Arronow $843.64 $843.48 $643.73",
                "468 Dave Moss $843.64 $378.15 $48.54"
        };
        salesReportTest("input/example.txt", 3, expectedLines);
    }

    // STUDENTS: I suggest writing more test methods here, then adding them to TestSuite.
}
