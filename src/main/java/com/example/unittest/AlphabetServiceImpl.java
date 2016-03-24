package com.example.unittest;

/**
 * This is an incredibly simple service that has a single dao object to retrieve its data.  Its purpose
 * is to help teach mocking concepts.
 */
public class AlphabetServiceImpl implements AlphabetService {
    private AlphabetDao dao;

    public char getUppercaseLetterByIndex(int i) {
        return Character.toUpperCase(dao.getLowercaseLetterForIndex(i));
    }

    public AlphabetSong updateAlphabetSong(AlphabetSong song) {
        return dao.updateAndReturn(song);
    }
}
