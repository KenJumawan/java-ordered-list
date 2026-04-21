package edu.commonwealthu.mrodriguez.cmsc230.test;

/**
 * A functional interface for a lambda that takes no arguments, returns no arguments, and may throw an exception.
 */
@FunctionalInterface
public interface Executable {
    /**
     * Executes the lambda.
     *
     * @throws Exception Contained lambda may throw any Throwable.
     */
    void execute() throws Throwable;
}
