package se.ju23.typespeeder.util;

import se.ju23.typespeeder.consle.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Sofie Van Dingenen
 * @version 1.0.0
 * since 2024-02-08
 *
 * <h2>FileUtil</h2>
 * <p>
 * FilUtil class handles reading and writing to files.
 */

public class FileUtil {
    static Console console = new Console();

    /**
     * Returns an Arralist of Strings of the files lines.
     *
     * @param fileName The name of the file.
     * @return ArrayList
     */

    private static ArrayList<String> readFile(String fileName) {
        try {
            ArrayList<String> fileInformation = new ArrayList<>();

            File file = new File(fileName);
            Scanner textFromFile = new Scanner(file);
            while (textFromFile.hasNextLine()) {
                fileInformation.add(textFromFile.nextLine());
            }
            return fileInformation;
        } catch (FileNotFoundException e) {
            console.error("File Not found!");
        }
        return null;
    }


    /**
     * returns a HashMap of the ArrayList of Strings that has been split where there is an equalsign.
     * The key of the HashMap is a String that is used in the code. The value of the HashMap is the text that will
     * be shown.
     *
     * @param lang the abbreviation of the language, which is used to get the right file.
     * @return HashMap with String as key and String as value.
     */

    public static HashMap<String, String> readLanguageFile(String lang) {
        HashMap<String, String> languageMap = new HashMap<>();
        ArrayList<String> languageInfoFromFile = readFile("src/main/resources/" + lang + ".txt");

        for (String languageInfo : languageInfoFromFile) {
            String[] languageInfoString = languageInfo.split(" = ");
            languageMap.put(languageInfoString[0], languageInfoString[1]);
        }
        return languageMap;
    }
}
