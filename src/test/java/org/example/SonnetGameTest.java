package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SonnetGameTest {

    @Test
    void blankReturnsUnderscoresMatchingWordLength() {
        assertEquals("_____", SonnetGame.blank("thee?"));
        assertEquals("________", SonnetGame.blank("summer's"));
        assertEquals("_", SonnetGame.blank("I"));
    }

    @Test
    void isCorrectGuessIgnoresCase() {
        assertTrue(SonnetGame.isCorrectGuess("SUMMER", "summer"));
    }

    @Test
    void isCorrectGuessIgnoresTrailingPunctuation() {
        assertTrue(SonnetGame.isCorrectGuess("day", "day?"));
        assertTrue(SonnetGame.isCorrectGuess("temperate", "temperate:"));
    }

    @Test
    void isCorrectGuessTreatsStraightAndCurlyApostrophesTheSame() {
        assertTrue(SonnetGame.isCorrectGuess("dimm'd", "dimm’d;"));
        assertTrue(SonnetGame.isCorrectGuess("dimm’d", "dimm'd"));
    }

    @Test
    void isCorrectGuessRejectsWrongWord() {
        assertFalse(SonnetGame.isCorrectGuess("winter", "summer’s"));
    }

    @Test
    void isCorrectGuessTrimsWhitespace() {
        assertTrue(SonnetGame.isCorrectGuess("  day  ", "day?"));
    }
}
