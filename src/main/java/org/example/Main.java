package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[][] bricksPosition = {
                {1, 1, 1}, {1, 2, 1}, {1, 3, 1}, {1, 4, 1},
                {2, 1, 1}, {2, 2, 2}, {2, 3, 1}, {2, 4, 1},
                {3, 1, 1}, {3, 2, 1}, {3, 3, 1}, {3, 4, 1}
        };
        int ballLife = 50;
        BrickBreaker game = new BrickBreaker(bricksPosition, ballLife, false, 1.2F);
        game.printBoard();
        while (true){
            System.out.print("Enter direction: ");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equals("exit")) {
                break;
            }
            game.controlBall(input);
        }
    }
}