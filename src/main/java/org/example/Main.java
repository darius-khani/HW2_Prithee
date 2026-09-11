package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Prithee ===");
        System.out.println("The Blackfriar's players need your help remembering the next line.");
        System.out.println("Type the missing word when prompted. Get three right or three wrong to end the show.");

        SonnetGame game = new SonnetGame();
        Scanner scanner = new Scanner(System.in);

        while (!game.isOver()) {
            System.out.println();
            game.playRound(scanner);
        }

        System.out.println();
        System.out.println(game.resultMessage());
        scanner.close();
    }
}
