package se.ju23.typespeeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileUtil {

    private static ArrayList<String> readFile(String fileName) throws FileNotFoundException {
        ArrayList<String> fileInformation = new ArrayList<>();

        File file = new File(fileName);
        Scanner textFromFile = new Scanner(file);
        while(textFromFile.hasNextLine()){
            fileInformation.add(textFromFile.nextLine());
        }
        return fileInformation;
    }

    public static HashMap<String, String> readLanguageFile(String lang) throws FileNotFoundException {
        HashMap<String, String> languageMap = new HashMap<>();
        ArrayList<String> languageInfoFromFile = readFile("src/main/resources/" +lang + ".txt");

        for (String languageInfo : languageInfoFromFile) {
            String[] languageInfoString = languageInfo.split(" = ");
            languageMap.put(languageInfoString[0], languageInfoString[1]);
        }
        return languageMap;
    }
}
