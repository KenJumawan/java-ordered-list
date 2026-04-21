package edu.commonwealthu.mrodriguez.cmsc230.listlab.test;

import edu.commonwealthu.mrodriguez.cmsc230.test.TestCase;

/**
 * A workbench for testing ListLab.
 * This file is distributed to students, and students may alter it if they wish,
 * but it will be reverted to its initial state before auto-grading.
 */
public class ListLabTester {
    /**
     * Prints the usage of this program, and exits with the specified exit code.
     *
     * @param exitCode The value to pass to System.exit().
     */
    public static void printUsage(int exitCode) {
        ListTestSuite suite = ListTestSuite.getInstance();

        System.out.println("Usage: ListLabTester [n]");
        System.out.println("  n: the index or name of the test case you want to run.");
        System.out.println("    Valid indices are from 0 to " + (suite.getTestCaseCount() - 1));
        System.out.println("    Valid names are: " + suite.getNames());
        System.exit(exitCode);
    }

    /**
     * Run a specific test.
     *
     * @param testCase The test to run.
     * @return True if test passed; false otherwise.
     */
    public static boolean evaluate(TestCase testCase) {
        try {
            boolean result = testCase.execute();

            if (!result) {
                System.out.println("Test failed due to incorrect result.");
            }

            return result;
        } catch (Exception e) {
            System.out.println("Test failed due to thrown exception: ");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Run all tests, and print the results to the user.
     */
    public static void evaluateAll() {
        ListTestSuite suite = ListTestSuite.getInstance();
        int passed = 0;
        int count = suite.getTestCaseCount();

        for (int i = 0; i < count; ++i) {
            TestCase testCase = suite.getTestCase(i);
            boolean result = evaluate(testCase);
            passed += result ? 1 : 0;
            System.out.println(i + " " + testCase.getName() + ": " + result);
        }

        System.out.println(passed + "/" + count + " tests passed.");
        System.exit(passed == count ? 0 : 1);
    }

    /**
     * Entry point for testing/auto-grading.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // No arguments: run all tests
        if (args.length == 0) evaluateAll();

        // Invalid input: print usage
        if (args.length > 1) printUsage(-1);

        // Otherwise, fetch and evaluate the appropriate test
        try {
            ListTestSuite suite = ListTestSuite.getInstance();
            TestCase testCase = suite.getTestCase(args[0]);
            boolean result = evaluate(testCase);
            System.out.println(suite.getIndex(testCase) + " " + testCase.getName() + ": " + result);
            System.exit(result ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
            printUsage(-1);
        }
    }
}
