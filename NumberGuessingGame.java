package NumberGuessingGame;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        
        while (playAgain) {
            int min = 1;
            int max = 100;
            int numberToGuess = random.nextInt(max - min + 1) + min;
            int numberOfAttempts = 5;
            int attemptsLeft = numberOfAttempts;
            boolean hasGuessedCorrectly = false;
            
            while (attemptsLeft > 0 && !hasGuessedCorrectly) {
                System.out.println("Guess any number between " + min + " and " + max + ":");
                
                try {
                    int playerGuess = Integer.parseInt(scanner.nextLine());
                    
                    if (playerGuess < min || playerGuess > max) {
                        System.out.println("Oops!Try a number within the range.");
                        continue;
                    }

                    if (playerGuess < numberToGuess) {
                        System.out.println("Too low!");
                    } else if (playerGuess > numberToGuess) {
                        System.out.println("Too high!");
                    } else {
                        hasGuessedCorrectly = true;
                        System.out.println("Hurray! You guessed the correct number.");
                    }

                    attemptsLeft--;
                    System.out.println("You only have " + attemptsLeft + " attempts left.\n");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
            
            if (!hasGuessedCorrectly) {
                System.out.println("Ohh no!, you've used all your attempts. The correct number was " + numberToGuess + ".");
            }
            
            System.out.println("Would you like to play again? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("yes");
        }
        
        System.out.println("Thanks for playing the game!");
        scanner.close();
    }
}
