package edu.commonwealthu.mrodriguez.cmsc230.test;

/**
 * My reduced copy of JUnit's Assert class, containing only what I need.
 * Contains methods for asserting conditions during tests,
 * which throw AssertionError if those asserted conditions prove false.
 */
public class Assert {

    /**
     * Asserts that the result of a computation matches the expected value.
     * If the expectation is violated, throws AssertionError.
     *
     * @param expected The expected value.
     * @param actual   The actual result of the computation.
     * @param <T>      The type of both arguments.
     */
    public static <T> void assertEquals(T expected, T actual) {
        if (!expected.equals(actual))
            throw new AssertionError("Failed assertion: " + expected + "==" + actual);
    }

    /**
     * Asserts that the result of a computation does NOT match a specific unexpected value.
     * If the expectation is violated, throws AssertionError.
     *
     * @param unexpected The forbidden value.
     * @param actual     The actual result of the computation.
     * @param <T>        The type of both arguments.
     */
    public static <T> void assertNotEquals(T unexpected, T actual) {
        if (unexpected.equals(actual))
            throw new AssertionError("Failed assertion: " + unexpected + "!=" + actual);
    }

    /**
     * Executes a given lambda, and asserts that it throws a specific expected Throwable.
     *
     * @param expectedThrow The type the lambda should throw.
     * @param lambda        The lambda that is expected to throw.
     */
    public static void assertThrows(Class<? extends Throwable> expectedThrow, Executable lambda) {
        try {
            lambda.execute();
        } catch (Throwable e) {
            if (e.getClass() == expectedThrow)
                return;
            throw new AssertionError(e);
        }

        throw new AssertionError("Expected " + lambda + " to throw " + expectedThrow + ", but nothing was thrown.");
    }

    /**
     * Asserts that a boolean condition is true.
     *
     * @param b The boolean expected to be true.
     */
    public static void assertTrue(boolean b) {
        if (!b)
            throw new AssertionError("Expected condition to be true, but was false.");
    }

    /**
     * Asserts that a boolean condition is false.
     *
     * @param b The boolean expected to be false.
     */
    public static void assertFalse(boolean b) {
        if (b)
            throw new AssertionError("Expected condition to be false, but was true.");
    }
}
