package se.ju23.typespeeder.consle;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Sofie Van Dingenen
 * @version 1.0.0
 * since 2024-02-08
 *
 * <h2>Console</h2>
 * <p>
 * the Console class print out all the text in a console. If a language is given the Console will print out in the
 * given language.
 */

@Component
public class Console {
    private HashMap<String, String> languageMap;
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    private final String ANSI_BLUE = "\u001B[34m";

    private final String ANSI_BLACK = "\u001B[30m";


    /**
     * Initilizes a new Console object with a given language. The constructor will set the map of the given language.
     *
     * @param language The language that has been chosen and will be used to print out the texts.
     */
    public Console(Language language) {
        this.languageMap = language.getLanguageMap();
    }

    public Console() {
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void printLine(String message) {
        System.out.println(message);
    }

    /**
     * Prints an Arraylist of Strings with a number starting from 1 until the last item in the list.
     * The list items are direclty translated.
     *
     * @param stringList The list that will be printed
     */
    public void print(ArrayList<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            System.out.print(i + 1 + ".");
            tln(stringList.get(i));
        }
    }

    public void print(String[] stringList){
        for(int i = 0; i< stringList.length; i++){
            print(i +1 +".");
            tln(stringList[i]);
        }
    }

    /**
     * Prints a translated text without adding a new line at the end.
     *
     * @param text The text that will be translated.
     */
    public void t(String text) {
        if (languageMap != null) {
            System.out.print(languageMap.get(text));
        }
        if (languageMap == null) {
            System.out.print(text);
        }
    }

    /**
     * Prints a translated text while adding a new line at the end.
     * @param text The test that will be translated.
     */
    public void tln(String text) {
        if (languageMap != null) {
            System.out.println(languageMap.get(text));
        } else {

            System.out.println(text);
        }
    }

    /**
     * Prints a dashed line.
     */
    public void printDashes() {
        System.out.println("""
                                
                --------------------------------------------------
                """);
    }

    public void error(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }
}
