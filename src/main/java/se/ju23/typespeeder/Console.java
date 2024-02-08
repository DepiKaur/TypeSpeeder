package se.ju23.typespeeder;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Console {
    public void print(String message){
        System.out.println(message);
    }
    public void print(String[] stringList, HashMap<String, String> languageMap){
        for(int i = 0; i < stringList.length; i++){
            print(i+1 + ". " + languageMap.get(stringList[i]));
        }
    }

    public void print(HashMap<String, String> languageMap, String text) {
        System.out.println(languageMap.get(text));
    }
}
