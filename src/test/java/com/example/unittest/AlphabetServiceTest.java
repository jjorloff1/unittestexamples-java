package com.example.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * These tests are meant to demonstrate some basic mocking concepts to use in unit testing.
 */
@RunWith(MockitoJUnitRunner.class)
public class AlphabetServiceTest {

    /*
     * With Mockito you can use @Mock as a shortcut for initializing a mock object.
     */
    @Mock
    private AlphabetDao mockDao;

    /*
     * With Mockito you can use @InjectMocks as a shortcut to inject all @Mock objects into your
     * Object Under Test.
     *
     * Both @Mock and @InjectMocks depends on the test class being annotated with @RunWith(MockitoJUnitRunner.class)
     */
    @InjectMocks
    private AlphabetService service = new AlphabetServiceImpl();

    /**
     * This test demonstrates the typical mocking scenario.
     *
     * This test also uses "Triangulation":  Instead of simple testing one input (i.e. 0) it tests two or more.
     * This is valuable, because if you are only testing one input on your happy path, then a minimum test driven
     * implementation might involve hard coding the return statement.  Adding a second input forces your code to
     * account for multiple scenarios and make it more likely that the simplest implementation is more flexible.
     * Its still possible that the simplest implementation could be a switch on inputs to provide specific outputs,
     * and if that is a legitimate concern add more tests.  Most developers will see the move toward ugly and
     * unnecessary switch or if statements and cut those out during the refactor step.
     */
    @Test
    public void testGetUppercaseLetterByIndex() {
        /* Given
         *  Set up inputs and mock all interactions (expectations) with outside classes that you expect to occur.
         *
         *  In general, you do not want to mock your inputs and your outputs, because those are the things that
         *  you are expecting your Method Under Test to actually be testing.  You want to ensure the behavior
         *  and data applied to those objects is a real as possible.
         *
         *  In general, you want to mock any downstream interaction with another class.  You want to isolate the
         *  Method Under Test from all of its dependencies to test it as an independent unit.  It is important to
         *  remember that you are NOT testing the mocked class.  You are assuming it will function as expected.  You
         *  should have other tests to test that unit, and you will have integration tests to test the whole.
         *
         *  See below for a BAD test example that illustrates my warning above.
         */
        when(mockDao.getLowercaseLetterForIndex(0)).thenReturn('a');
        when(mockDao.getLowercaseLetterForIndex(25)).thenReturn('z');

        // When
        char first = service.getUppercaseLetterByIndex(0);
        char last = service.getUppercaseLetterByIndex(25);

        // Then
        assertEquals('A', first);
        assertEquals('Z', last);

        // In practice I would condense the previous 4 lines into, but left in the expanded form to teach BDD.
        // assertEquals('A', service.getUppercaseLetterByIndex(0));
        // assertEquals('Z', service.getUppercaseLetterByIndex(25));

        /* Then (cont.)
         *
         * Because mockito returns null for any non-mocked method, you will need to verify that the methods you
         * thought were going to be called actually were.  Using verify like this is usually sufficient, but if it
         * is important to your test that no other interactions happened to the mock, you can use:
         * verifyNoMoreInteractions(mockDao).  Mockito does not recommended to use this in every test, because it
         * leads to over specified tests that can be fragile.
         *
         * If you are using another framework like JMock you will automatically getLowercaseLetterForIndex the verifyNoMoreInteractions()
         * behavior because it expects you to define all your mock interactions (expectations).
         */
        verify(mockDao).getLowercaseLetterForIndex(0);  // Ensure that the methods you expected to be executed were actually executed
        verify(mockDao).getLowercaseLetterForIndex(25);
    }

    /**
     * This test was written to demonstrate what NOT to mock.
     */
    @Test
    public void testUpdateAlphabetSong() {
        // Given
        AlphabetSong existingSong = new AlphabetSong();
        existingSong.setId(0);
        existingSong.setLengthInMinutes(1);

        AlphabetSong updatedSong = new AlphabetSong();
        updatedSong.setId(0);
        updatedSong.setLengthInMinutes(2);

        when(mockDao.updateAndReturn(existingSong)).thenReturn(updatedSong);

        // When
        AlphabetSong returnedSong = service.updateAlphabetSong(existingSong);

        // Then

        /* This is a worthless check... You shouldn't be testing that the dao method does what you expect, because
         * it should have its own test verifying its behavior.
         */
        assertEquals(2, returnedSong.getLengthInMinutes());

        /* Instead just verify that the downstream method executed as you thought it should and that the
         * service method returned the object you expected.
         */
        assertSame(updatedSong, returnedSong);
        verify(mockDao).updateAndReturn(existingSong);
    }

    /**
     * This test demonstrates how you can define an expectation for a mock method to throw a certain exception.
     *
     * This is useful in exception tests, or for ensuring that your unit has the appropriate exception handling
     * (or lack thereof).
     */
    @Test(expected = NullPointerException.class) // Then
    public void testNullSongResultsInUncaughtNullPointerException() {
        // Given
        when(mockDao.updateAndReturn(null)).thenThrow(new NullPointerException());

        // When
        service.updateAlphabetSong(null);

        // Then is defined by the expectation of an exception on the annotation
    }

}