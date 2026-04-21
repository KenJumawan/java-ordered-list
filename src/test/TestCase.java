package edu.commonwealthu.mrodriguez.cmsc230.test;

import java.util.concurrent.Callable;

/**
 * A class representing a generic test case.
 * Associates a lambda that executes a test to a name.
 * This file is distributed to students, and students may alter it if they wish,
 * but it will be reverted to its initial state before auto-grading.
 */
public class TestCase {
    /**
     * The name of the test.
     */
    private final String name;

    /**
     * A lambda that takes no arguments, runs a test,
     * and supplies (returns) a Boolean indicating the outcome of that test.
     */
    private final Executable lambda;

    /**
     * Construct a new test case with the specified values (no point value).
     * Intended for use by students writing their own tests.
     *
     * @param name   The name of the test.
     * @param lambda The lambda that executes the test.
     */
    public TestCase(String name, Executable lambda) {
        this.name = name;
        this.lambda = lambda;
    }

    /**
     * Get the name of the test.
     *
     * @return The name of the test.
     */
    public String getName() {
        return name;
    }

    /**
     * Executes the test by invoking the lambda.
     *
     * @return The outcome of the test. True if passed; false if failed.
     */
    public boolean execute() throws Exception {
        try {
            lambda.execute();
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }
}