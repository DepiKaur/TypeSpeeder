package se.ju23.typespeeder;

import se.ju23.typespeeder.Consle.Console;

import java.util.Locale;
import java.util.Scanner;

public class ScannerHelper {
   private static Console console = new Console();
   private static Scanner scanner = new Scanner(System.in).useLocale(Locale.FRANCE);

    public static int getInt(){
         int number = 0;
         while( number == 0){
             boolean validNumber = false;
             while (! validNumber){
                 try {
                     console.print("> ");
                     number = scanner.nextInt();
                     validNumber = true;
                 } catch (Exception e){
                     console.print("Wrong Value, enter a number: ");
                     scanner.nextLine();
                 }
             }
         }
         return number;
    }

    public static String getStringInputForLogin() {
        String input;

        do {
            input = scanner.nextLine();
            if(input.contains(" ") || input.contains(",")) {
                console.t("---Whitespaces or comma NOT allowed---");
                console.print("Try again: ");
            }
        } while(input.trim().isEmpty() || input.contains(" ") || input.contains(","));

        return input;
    }

    public static String validateStringInputForLogin() {
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.trim().isEmpty());
        return input;
    }

}
