package se.ju23.typespeeder.Consle;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;

@Component
public class Console {
    private HashMap<String, String> language;
    private final  String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";

    private final String ANSI_BLUE = "\u001B[34m";

    private final String ANSI_BLACK = "\u001B[30m";


    public void setLanguage(HashMap<String, String> langaugeMap){
        language = langaugeMap;
    }
    public void print(String message){
        System.out.print(message);
    }
    public void printLine(String message){
        System.out.println(message);
    }
    public void print(String[] stringList){
        for(int i = 0; i < stringList.length; i++){
            print(i +1 +". ");
            t(stringList[i]);
        }
    }

    public void t(String text) {
        if(language != null){
            System.out.println(language.get(text));
        }
        if(language == null){
            System.out.println(text);
        }
    }
    public void error(String message){
        System.out.println(ANSI_RED+ message + ANSI_RESET);
    }
}
