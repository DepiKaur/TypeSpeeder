package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author  Sofie Van Dingenen
 * @date 2024-02-08
 * @version 1.0
 */
@Component
public class MenuService {
    @Autowired
    Console console;

    String[] optionList = {
            "1. Log in",
            "2. make account",
            "3.exit"
    };

    public void startMenu(){
        //TODO: choose language
        boolean running = true;
        console.print("""
                --------------------------------------------------
                Welcome to TyppeSpeeder!
                """);
        while(running){
            console.print("Choose one of the following options");
            console.print(optionList);
            int chosenInt = ScannerHelper.getInt();
            switch (chosenInt){
                case 1 -> console.print("you are logged in");
                case 2 -> console.print("you are making an account");
                case 3 -> running = false;
            }
        }
    }


}
