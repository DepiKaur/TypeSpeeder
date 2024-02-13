package se.ju23.typespeeder.menu;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.ScannerHelper;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.service.MenuService;

/**
 * @author Sofie Van Dingenen
 * @version 1.0
 * Since 2024-02-12
 *
 * <h2>MenuHandler</h2>
 *
 * MenuHandler handles all menu actions, it uses the different Menu classes, sush as getting lanugage, logging in,
 * starting a game.
 *
 *
 */
@Component
public class MenuHandler {
    private  Console console;
    private MenuService menu;
    private boolean running = true;
    private static Language language;


    /**
     * This method starts the whole program.
     */
    public void run() {
        language = setLanguage();
        while(running){
            loginMenu();
        }

    }

    /**
     * This method returns the chosen language that the users chooses.
     * If no language is chosen the default language(English) will be used.
     * @return The chosen language from a list.
     */
    private Language setLanguage() {
        LanguageMenu languageMenu = new LanguageMenu();
        languageMenu.dsiplayMenu();
        return languageMenu.setLanguage();
    }


    /**
     * This method starts the login process.
     */
    public void loginMenu() {
        menu = new LoginMenu(language);
        while (running) {
            menu.displayMenu();
            int maxValue = menu.getMenuOptions().size();
            int chosenInt = ScannerHelper.getInt(maxValue);
            switch (chosenInt) {
                case 1 -> console.print("you are logged in");
                case 2 -> console.print("you are making a new account");
                case 3 -> running = false;

            }
        }
    }


}
