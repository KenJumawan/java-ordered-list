package edu.commonwealthu.mrodriguez.cmsc230.listlab.test;

import edu.commonwealthu.mrodriguez.cmsc230.test.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a predefined set of test cases.
 * This file is distributed to students, and students may alter it if they wish,
 * but it will be reverted to its initial state before auto-grading.
 */
public class ListTestSuite {
    /**
     * Singleton instance of this class.
     * Constructed and accessed through getInstance().
     */
    private static ListTestSuite instance = null;

    /**
     * The set of predefined test cases.
     * Not meant for external access.
     */
    protected List<TestCase> testCases;

    /**
     * Constructor. Creates the singleton instance.
     * Not meant for external access.
     */
    protected ListTestSuite() {
        testCases = generateTestCases();
    }

    /**
     * Gets the singleton instance of the TestSuite.
     * Constructs it if it doesn't exist yet.
     *
     * @return The test suite.
     */
    public static ListTestSuite getInstance() {
        if (instance == null) instance = new ListTestSuite();
        return instance;
    }

    /**
     * Initialize the set of predefined test cases.
     *
     * @return The set of predefined test cases.
     */
    private static List<TestCase> generateTestCases() {
        List<TestCase> testCases = new ArrayList<>();

        testCases.add(new TestCase("InsertAndGet", ListTestMethods::insertAndGetTest));
        testCases.add(new TestCase("InsertNull", ListTestMethods::insertNullTest));
        testCases.add(new TestCase("SalesReportExample", ListTestMethods::salesReportExampleTest));

        // STUDENTS: If you implement any tests, add them here.

        return testCases;
    }

    /**
     * Get a list of all test cases.
     *
     * @return A list of all test cases.
     */
    public List<? extends TestCase> getTestCases() {
        return testCases;
    }

    /**
     * Get the test case corresponding at index i.
     *
     * @param i The index.
     * @return The test case at index i.
     */
    public TestCase getTestCase(int i) {
        return testCases.get(i);
    }

    /**
     * Get the number of test cases.
     *
     * @return The number of test cases.
     */
    public int getTestCaseCount() {
        return testCases.size();
    }

    /**
     * Get the test case with a name matching the String s.
     * Failing that, parse s as an integer, and fetch the test case at that index.
     *
     * @param s The name/index of the test case.
     * @return The test case matching that name/index.
     */
    public TestCase getTestCase(String s) {
        for (TestCase testCase : getTestCases())
            if (testCase.getName().equals(s)) return testCase;

        return getTestCase(Integer.parseInt(s));
    }

    /**
     * Generate a comma-separated list of the names of all test cases.
     *
     * @return The list of names of all test cases.
     */
    public String getNames() {
        StringBuilder sb = new StringBuilder();
        int count = testCases.size();
        for (int i = 0; i < count; i++) {
            sb.append(testCases.get(i).getName());
            if (i != count - 1) sb.append(", "); // If not last, add comma
        }
        return sb.toString();
    }

    /**
     * Gets the index of the given test case.
     *
     * @param testCase The TestCase to find the index of.
     * @return Its index.
     */
    public int getIndex(TestCase testCase) {
        String name = testCase.getName();

        for (int i = 0; i < testCases.size(); i++)
            if (testCases.get(i).getName().equals(name)) return i;

        throw new IllegalArgumentException("TestCase " + name + " not found.");
    }
}
