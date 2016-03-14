package com.example.unittest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This test is purposefully over abstracted for the purpose of demonstrating some testing concepts.
 *
 * Class Under Test: AdderImpl
 *
 * As a reminder the Test Driven Development (TDD) mantra is "red-green-refactor".  The process is as follows:
 * 1. (Red) Write a test.
 *    * Write the test as if you could choose the simplest way to invoke each method in the Class Under Test (note:
 *      these purposefully do not do this so that they could demonstrate the Given ... When ... Then approach)
 * 2. (Green) Write the code in your Object Under Test to pass the test.
 *    * Write just enough code to pass the test in the quickest way possible, committing whatever coding sins
 *      necessary to get it to pass as quickly as possible.
 * 3. (Refactor) Clean up, remove duplication, inefficiencies, and waste in your code.  Abstract where necessary.
 *    Add documentation and bring code into compliance with code style standards.  Very simple code may already
 *    be in its refactored state, but most code can use a little tidying.
 *    * DON'T SKIP THIS STEP
 *    * SERIOUSLY DON'T
 *
 * Sources:
 *   * Test Driven Development: By Example, Kent Beck, Addison-Wesley Longman, 2002, ISBN 0-321-14653-0, ISBN 978-0321146533
 *   * Behavior Driven Development, https://en.wikipedia.org/wiki/Behavior-driven_development
 */
public class AdderImplTest {

    /**
     * A Positive test tests the happy path of your method.
     *
     * A good approach to test writing is the Behavioral Driven Development (BDD) approach, which is to divide the
     * test into 3 sections:
     * 1. Given ... initialize expected state of object and inputs prior to execution
     * 2. When ... execute your method "given" the prior setup
     * 3. Then ... verify your results through assertions
     */
    @Test
    public void testPositiveNormalAddition() {
        // Given
        Adder adder = new AdderImpl(1, 1);

        // When
        int result = adder.add();

        // Then
        assertEquals(2, result);
    }

    /**
     * A triangulation test helps you to take a test drive a simple method into the correct implementation
     * by forcing your code to avoid hard coded values.
     */
    @Test
    public void testTriangulateNormalAddition() {
        // Given
        Adder adder = new AdderImpl(1, 50);

        // When
        int result = adder.add();

        // Then
        assertEquals(51, result);
    }

    /**
     * A Boundary test tests your code at the boundaries of your input, or with input values that are close
     * to causing exceptions
     */
    @Test
    public void testPositiveBoundary() {
        assertEquals(0, (new AdderImpl(0, 0)).add());
        assertEquals(1, (new AdderImpl(0, 1)).add());
        assertEquals(100, (new AdderImpl(99, 1)).add());
    }

    /**
     * An Exception test, in this case a Negative Boundary test, tests the expected behavior of your method during
     * exceptional cases.
     */
    @Test(expected = UnderLimitResultException.class)
    public void testNegativeResultUnder0ThrowsUnderLimitResultException() {
        Adder adder = new AdderImpl(0, -1);
        adder.add();
    }

    /**
     * Another Boundary Exception test at the upper boundary.
     */
    @Test(expected = OverLimitResultException.class)
    public void testNegativeResultOver100ThrowsOverLimitResultException() {
        Adder adder = new AdderImpl(100, 1);
        adder.add();
    }

}
