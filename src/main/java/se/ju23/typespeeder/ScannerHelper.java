package se.ju23.typespeeder;

import se.ju23.typespeeder.Consle.Console;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Sofie Van Dingenen
 * since 2024-02-08
 * @version 1.0.0
 *
 * <H2>ScannerHelper</H2>
 * <p>
 * ScannerHelper is a class that gets the input from a user.
 */

public class ScannerHelper {
   private static Console console = new Console();
   private static Scanner scanner = new Scanner(System.in).useLocale(Locale.FRANCE);


    /**
     * Returns an int that the users chooses between 1 and the max value.
     * @param maxValue the highest number the user can enter.
     * @return int
     */
    public static int getInt(int maxValue) {
        int number = 0;
        while (number < 1 || number > maxValue) {
            try {
                console.printLine("> ");
                number = scanner.nextInt();
                if (number < 1 || number > maxValue) {
                    console.tln("input.wrong.number");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                console.error("input.notNumber");
            }
        }
        return number;
    }

    public static String getStringInputForUsernameOrDisplayName() {
        String input;

        do {
            input = scanner.nextLine();
            if (input.contains(" ") || input.contains(",")) {
                console.t("---Whitespaces or comma NOT allowed---");
                console.print("Try again: ");
            }
        } while (input.trim().isEmpty() || input.contains(" ") || input.contains(","));

        return input;
    }

    public static String getStringInputForPassword() {
        scanner.nextLine();

        console.t("NOTE: A password must contain atleast 1 capital letter, " +
                "\n1 small letter, 2 digits and be 6 characters long.");
        console.print("Enter password: ");

        String input = null;
        boolean match = false;

        while (!match) {

            input = scanner.nextLine();
            match = input.matches("^(?=.*\\d{2,})(?=.*[a-z])(?=.*[A-Z]).{6,}$");
            if (!match) {
                console.print("Try again: ");
            }
        }

        return input;

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
            input = scanner.nextLine().toLowerCase().trim();
        } while (input.trim().isEmpty());
        return input;
    }

}
