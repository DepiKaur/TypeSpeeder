package se.ju23.typespeeder;

import se.ju23.typespeeder.consle.Console;

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

}
