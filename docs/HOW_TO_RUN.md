# How to Run Prithee

## Prerequisites

You need a JDK available that Gradle can use as a Java 21 toolchain. If you
open this project in IntelliJ and it doesn't find one automatically, add one
via `File > Project Structure > Platform Settings > SDKs > + > Download JDK`
(pick version 21). If you're building from the command line and the project
also can't find one there, the `foojay-resolver-convention` plugin
configured in `settings.gradle.kts` will have Gradle download a matching JDK
automatically, as long as the machine has internet access.

## Running the program

From a terminal in the project root:

    ./gradlew run --console=plain -q

(On Windows, use `gradlew.bat run --console=plain -q` instead.) The
`--console=plain` flag matters here -- without it, Gradle's fancy console
output can interfere with typed input reaching the program.

Alternatively, in IntelliJ, open `src/main/java/org/example/Main.java` and
click the green run arrow next to the `main` method.

The program will print the sonnet with one word blanked and prompt:

    Prithee, what is the word?

Type your guess and press Enter. It will tell you whether you were right,
show your running score, and print the sonnet again with a different word
blanked, repeating until you get three right or three wrong.

## Running the tests

    ./gradlew test

Test results are written to `build/reports/tests/test/index.html` if you
want to view them in a browser; a plain pass/fail summary also prints to the
console.
