package se.ju23.typespeeder;

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

}
