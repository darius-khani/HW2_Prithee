# Prithee -- Design Documentation

## Overview

Prithee is a console program that reprints Shakespeare's Sonnet 18 with one
word replaced by underscores and asks the player to supply the missing word.
The player keeps playing rounds, each blanking a different word, until they
have three correct guesses or three incorrect guesses, at which point the
program prints a result message and exits.

## Architecture

The program has two classes. `Main` is the entry point: it prints the
opening instructions, creates a `SonnetGame`, opens a single `Scanner` on
`System.in`, and loops calling `playRound` until the game reports it is over,
then prints the final result. It contains no game logic of its own.

`SonnetGame` holds all of the actual behavior. The sonnet is stored as an
array of line strings, which the constructor splits on whitespace into a
`String[][]` indexed by line and word position. Alongside that, the
constructor builds a list of every `(line, word)` position in the poem and
shuffles it once with `java.util.Collections.shuffle`. Each round pops one
position off the end of that shuffled list, which both picks a random word
and guarantees the same word is never blanked twice across a single game,
without any extra tracking. A game only ever needs at most five rounds to
resolve (worst case, the score is tied 2-2 and the fifth round decides it),
and the sonnet has well over a hundred words, so the list can never run out.

Two methods carry the interesting logic and are deliberately written as
static methods that take plain strings in and return a plain value out, with
no dependency on `System.in`, `System.out`, or any game state: `blank(word)`
returns a string of underscores the same length as the word, and
`isCorrectGuess(guess, actualWord)` decides whether a typed answer matches.
Keeping I/O out of these two methods is what makes them unit-testable
directly, and it's also what makes the rest of the class (`playRound`)
simple: it just prints, reads one line, and calls these two methods.

## Handling the sonnet's punctuation and apostrophes

Every word in the sonnet as printed carries whatever punctuation follows it
in the original text (for example "day?" or "temperate:"), and several words
use a typographic apostrophe (U+2019, "'") rather than the plain ASCII
apostrophe a keyboard normally types (for example "dimm'd" vs "dimm'd").
Comparing the player's raw input against the raw word would fail on nearly
every contraction and every line-ending word for reasons that have nothing
to do with whether the player actually knew the word. `isCorrectGuess`
normalizes both sides the same way before comparing: it trims surrounding
whitespace, lowercases, converts a curly apostrophe to a straight one, and
strips one or more trailing punctuation characters (`. , ; : ? !`) from the
end of the word. This means a player who types `dimm'd` is credited for the
word printed as `dimm'd;` in the poem.

## Java version note

The assignment specifies Java 26. As of this project, Gradle's own daemon is
not yet certified to run on JDK 26 (JDK 26 shipped in March 2026, and Gradle
support for a brand-new JDK release typically lags by a version or two), so
this project targets Java 21 (the current LTS release) via a Gradle
toolchain declaration in `build.gradle.kts`, and `settings.gradle.kts`
includes the `foojay-resolver-convention` plugin so Gradle can automatically
download a matching JDK if one isn't already installed. Nothing else about
the assignment's requirements is affected by this substitution.

## Known limitations

Because every word position is eligible, a round can occasionally blank a
very short word (like "I" or "of"), which is easy to guess but still counts
as a normal round. This was a deliberate simplification rather than an
oversight -- filtering out short words was judged not worth the added
complexity for this exercise. The random word order is not deterministic in
normal play (it uses `java.util.Random` seeded from system entropy); the
tests instead construct `SonnetGame` with an explicit `Random` seed where
determinism is needed.
