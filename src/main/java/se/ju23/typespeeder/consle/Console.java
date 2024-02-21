package se.ju23.typespeeder.consle;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.game.GameDifficultyLevel;
import se.ju23.typespeeder.game.GameType;

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
    private Color color;

    /**
     * Initilizes a new Console object with a given language. The constructor will set the map of the given language.
     *
     * @param language The language that has been chosen and will be used to print out the texts.
     */
    public Console(Language language) {
        this.languageMap = language.getLanguageMap();
        this.color= Color.WHITE;
    }

    public Console() {
    }

    public void setColor(Color color) {
        this.color = color;
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
    public void printList(ArrayList<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            System.out.print(i + 1 + ".");
            System.out.println(stringList.get(i));
        }
    }

    public void print(String[] stringList){
        for(int i = 0; i< stringList.length; i++){
            print(i +1 +".");
            tln(stringList[i]);
        }
    }

    public void printLine(String message, Color color){
        System.out.println(color.getCode()+ message+ Color.WHITE.getCode());
    }
    public void print(String message, Color color){
        System.out.print(color.getCode() + message+ Color.WHITE.getCode());
    }

    public void printf(String format, Object ... args){
        System.out.printf(format, args);
    }
    public void print(GameType[] list){
        int choicenumber = 1;
        for (GameType menuChoice : list) {
            print(choicenumber + ". " );
            tln(menuChoice.getType());
            choicenumber++;
        }
    }

    public void print(GameDifficultyLevel[] list){
        int choicenumber = 1;
        for (GameDifficultyLevel menuChoice : list) {
            print(choicenumber + ". " );
            tln(menuChoice.getDifficultyLevel());
            choicenumber++;
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
    public void tf(String format, Object... args){
        if(languageMap!= null){
            System.out.printf(format,languageMap.get(args));
        }
        else {
            System.out.printf(format, args);
        }
    }

    /**
     * Prints a translated text without adding a new line at the end.
     *
     * @param text The text that will be translated.
     */
    public void t(String text, Color color) {
        if (languageMap != null) {
            print( languageMap.get(text), color);
        }
        if (languageMap == null) {
            print(text, color);
        }
    }

    /**
     * Prints a translated text while adding a new line at the end.
     * @param text The test that will be translated.
     */
    public void tln(String text, Color color) {
        if (languageMap != null) {
            printLine(languageMap.get(text), color);
        } else {

            printLine(text, color);
        }
    }


    /**
     * Prints a dashed line.
     */
    public void printDashes() {
        System.out.print("""
                
                --------------------------------------------------------------------------
                """);
    }
    /**
     * Prints a dashed line.
     */
    public void printDashes(Color color) {
        System.out.print(color.getCode()+ """ 
                
                --------------------------------------------------------------------------
                """+ Color.WHITE.getCode());
    }

    public void error(String message) {
        System.out.println(Color.RED.getCode() + message+ Color.WHITE.getCode());
    }
}
