package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Consle.Console;

import java.util.HashMap;

/**
 * @author Sofie Van Dingenen
 * @version 1.0
 * @date 2024-02-08
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
    String[] languageOptions = {
            "English", "Svenska"
    };

    public void startMenu() {
        console.print("""
                --------------------------------------
                TypeSpeeder
                                
                welcome/ välkommen 
                --------------------------------------
                                
                Choose a language to continue/ Välj ett språk för att fortsätta
                                
                """);

        setLanguage();

        boolean running = true;
        console.print("""
                --------------------------------------------------
                """);
        console.t("menu.welcome.typeSpeeder");
        while (running) {
            console.t("menu.option.choose");
            console.print(optionList);
            int chosenInt = ScannerHelper.getInt();
            switch (chosenInt) {
                case 1 -> console.print("you are logged in");
                case 2 -> console.print("you are making an account");
                case 3 -> running = false;
            }
        }
    }

    private void setLanguage() {
        HashMap langugeMap = null;
        String language = chooseLanguage();
        try {
            langugeMap = FileUtil.readLanguageFile(language);
        } catch (Exception e){
            console.error("file.read.error" + e);
        }
        console.setLanguage(langugeMap);
    }

    private String chooseLanguage(){
        console.print(languageOptions);
        int chosenInt = ScannerHelper.getInt();
        if (chosenInt == 1) {
            return "eng";
        }
        if (chosenInt == 2) {
            return "sv";
        }
        return "";
    }
}
