package com.example.unittest;

/**
 * This interface is the bare bones minimum required to pass the AlphabetServiceTests.
 *
 * This shows that when this interface is mocked, it doesn't matter what the actual implementation or return values
 * of these methods are.  That allows you to truly isolate AlphabetService from the implementation of its members.
 *
 * In practice the implementation for this interface should have its own unit tests verifying that it has the correct
 * behavior, but I omitted the implementation to demonstrate that mocking overrides implemented behavior by serving
 * as a proxy for that object.
 */
public interface AlphabetDao {
    char getLowercaseLetterForIndex(int i);
    AlphabetSong updateAndReturn(AlphabetSong existingSong);
}
