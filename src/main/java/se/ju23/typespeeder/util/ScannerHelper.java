package se.ju23.typespeeder.util;

import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.game.GameDifficultyLevel;
import se.ju23.typespeeder.game.GameType;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Sofie Van Dingenen, Depinder Kaur
 * @date 2024-02-08
 * @version 1.0.0
 *
 * <H2>ScannerHelper</H2>
 * <p>
 * ScannerHelper is a class that gets the input from a user.
 */

public class ScannerHelper {
   private static final Console console = new Console();
   private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.FRANCE);

    /**
     * Returns an int that the users chooses between 1 and the max value.
     * @param maxValue the highest number the user can enter.
     * @return int
     */
    public static int getInt(int maxValue) {
        int number = 0;
        while (number < 1 || number > maxValue) {
            try {
                console.print("> ");
                number = scanner.nextInt();
                if (number < 1 || number > maxValue) {
                    console.error("Not a valid number try again with a number from the list!");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                console.error("Not a number try again with a number!");
            }
        }
        return number;
    }

    public static String getStringInputForUsernameOrDisplayName() {
        String input;

        do {
            input = scanner.nextLine();
            if (input.contains(" ") || input.contains(",")) {
                console.error("---Whitespaces or comma NOT allowed---");
                console.printLine("Try again: ");
            }
        } while (input.trim().isEmpty() || input.contains(" ") || input.contains(","));

        return input;
    }

    public static String getStringInputForPassword() {
        String input = null;
        boolean match = false;

        while (!match) {
            input = scanner.nextLine();
            match = input.matches("^(?=.*\\d{2,})(?=.*[a-z])(?=.*[A-Z]).{6,}$");
            if (!match) {
                console.error("Try again: ");
            }
        }
        return input;
    }
    public static GameType getGameType(GameType[] options){
        int userchoice = getInt(options.length);
        return options[userchoice-1];
    }
    public static GameDifficultyLevel getDificultyLevel(GameDifficultyLevel[] options){
        int userchoice = getInt(options.length);
        return options[userchoice-1];
    }

    public static String validateStringInputForLogin() {
        String input;
        do {
            input = scanner.nextLine();
        } while (input.trim().isEmpty());
        return input;
    }

    public static String getStringInput() {               //to handle invalid user-input instead of a String
        String input;
        do {
            input = scanner.nextLine();
        } while (input.trim().isEmpty());
        return input;
    }
}
