package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Core game logic for "Prithee": prints Shakespeare's Sonnet 18 with one word
 * blanked out, prompts the player for the missing word, and tracks correct
 * and incorrect guesses until the player gets three right or three wrong.
 */
public class SonnetGame {

    private static final String[] LINES = {
        "Shall I compare thee to a summer’s day?",
        "Thou art more lovely and more temperate:",
        "Rough winds do shake the darling buds of May,",
        "And summer’s lease hath all too short a date;",
        "Sometime too hot the eye of heaven shines,",
        "And often is his gold complexion dimm’d;",
        "And every fair from fair sometime declines,",
        "By chance or nature’s changing course untrimm'd;",
        "But thy eternal summer shall not fade,",
        "Nor lose possession of that fair thou ow’st;",
        "Nor shall death brag thou wander’st in his shade,",
        "When in eternal lines to time thou grow’st:",
        "So long as men can breathe or eyes can see,",
        "So long lives this, and this gives life to thee."
    };

    private final String[][] words;
    private final List<int[]> unusedPositions = new ArrayList<>();

    private int correctCount = 0;
    private int incorrectCount = 0;

    public SonnetGame() {
        this(new Random());
    }

    /** Package-private constructor so tests can pass a seeded Random for determinism. */
    SonnetGame(Random random) {
        words = new String[LINES.length][];
        for (int i = 0; i < LINES.length; i++) {
            words[i] = LINES[i].split("\\s+");
            for (int j = 0; j < words[i].length; j++) {
                unusedPositions.add(new int[]{i, j});
            }
        }
        Collections.shuffle(unusedPositions, random);
    }

    /** Returns a string of underscores the same length as word. */
    static String blank(String word) {
        return "_".repeat(word.length());
    }

    /**
     * Compares guess to the actual word, ignoring case, leading/trailing
     * whitespace, trailing punctuation, and the difference between a
     * straight apostrophe (') and a curly one (’).
     */
    static boolean isCorrectGuess(String guess, String actualWord) {
        return normalize(guess).equals(normalize(actualWord));
    }

    private static String normalize(String s) {
        return s.trim()
                .toLowerCase()
                .replace('’', '\'')
                .replaceAll("[.,;:?!]+$", "");
    }

    /**
     * Plays one round: blanks a not-yet-used word, prints the whole sonnet
     * with that word hidden, reads a guess from scanner, reports whether it
     * was correct, and updates the running tally.
     */
    void playRound(Scanner scanner) {
        int[] pos = unusedPositions.remove(unusedPositions.size() - 1);
        int lineIndex = pos[0];
        int wordIndex = pos[1];
        String actualWord = words[lineIndex][wordIndex];

        for (int i = 0; i < words.length; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < words[i].length; j++) {
                String display = (i == lineIndex && j == wordIndex)
                        ? blank(words[i][j])
                        : words[i][j];
                line.append(display);
                if (j < words[i].length - 1) {
                    line.append(' ');
                }
            }
            System.out.println(line);
        }

        System.out.print("Prithee, what is the word? ");
        String guess = scanner.nextLine();

        if (isCorrectGuess(guess, actualWord)) {
            correctCount++;
            System.out.println("Correct!");
        } else {
            incorrectCount++;
            System.out.println("Error! The word was \"" + actualWord + "\".");
        }
        System.out.println("(Score so far: " + correctCount + " correct, " + incorrectCount + " incorrect)");
    }

    boolean isOver() {
        return correctCount >= 3 || incorrectCount >= 3;
    }

    String resultMessage() {
        if (correctCount >= 3) {
            return "You got three right! Well remembered, player.";
        } else {
            return "Three wrong -- better study the script before the next preview.";
        }
    }

    int getCorrectCount() {
        return correctCount;
    }

    int getIncorrectCount() {
        return incorrectCount;
    }
}
