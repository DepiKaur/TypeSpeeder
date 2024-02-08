package se.ju23.typespeeder;

import org.apache.logging.log4j.util.EnglishEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author  Sofie Van Dingenen
 * @date 2024-02-08
 * @version 1.0
 */
@Component
public class MenuService {
    @Autowired
    Console console;

    HashMap<String, String> enMap = new HashMap<>();
    HashMap<String, String> svMap = new HashMap<>();

    String[] optionList = {
            "menu.option.login",
            "menu.option.newUser",
            "menu.option.exit"
    };

    public void startMenu(){
        console.print("""
                --------------------------------------
                TypeSpeeder
                
                welcome/ välkommen 
                --------------------------------------
                
                Choose a language to continue/ Välj ett språk för att fortsätta
                
                """);

        HashMap<String, String> languageMap = getLanguage();

        boolean running = true;
        console.print("""
                --------------------------------------------------
                """);
        console.print(languageMap, "menu.welcome.typeSpeeder");
        while(running){
            console.print(languageMap, "menu.option.choose");
            console.print(optionList, languageMap);
            int chosenInt = ScannerHelper.getInt();
            switch (chosenInt){
                case 1 -> console.print("you are logged in");
                case 2 -> console.print("you are making an account");
                case 3 -> running = false;
            }
        }
    }

    private void createEnMap(){
        enMap.put("menu.option.login", "Log in");
        enMap.put("menu.option.newUser", "make an account");
        enMap.put("menu.option.exit", "Exit");
        enMap.put("menu.option.choose", "Choose one of the folowing options");
        enMap.put("menu.welcome.typeSpeeder", "Welcome to TypeSpeeder!");
    }

    private void createsvMap(){
        svMap.put("menu.option.login", "Logga in");
        svMap.put("menu.option.newUser", "Skapa ett konto");
        svMap.put("menu.option.exit", "Avsluta");
        svMap.put("menu.option.choose", "Välj en av nedanstående val");
        svMap.put("menu.welcome.typeSpeeder","Välkommen till TypeSpeeder! ");

    }

    private HashMap<String, String> getLanguage(){
        HashMap langugeMap = null;
        console.print("1. English\n2. Svenska");
        int chosenInt = ScannerHelper.getInt();
        if( chosenInt == 1){
            langugeMap = enMap;
            createEnMap();
        }
        if(chosenInt == 2){
            langugeMap = svMap;
            createsvMap();
        }
        return langugeMap;
    }


}
