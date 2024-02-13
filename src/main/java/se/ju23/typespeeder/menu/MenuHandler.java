package se.ju23.typespeeder.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.ScannerHelper;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.service.MenuService;
import se.ju23.typespeeder.service.PlayerService;

import java.util.Optional;

/**
 * @author Sofie Van Dingenen
 * @version 1.0
 * Since 2024-02-12
 *
 * <h2>MenuHandler</h2>
 * <p>
 * MenuHandler handles all menu actions, it uses the different Menu classes, sush as getting lanugage, logging in,
 * starting a game.
 */
@Component
public class MenuHandler {
    private Console console;
    private MenuService menu;
    private boolean languageChose = false;
    private boolean running = true;
    private static Language language;

    private Optional<Player> currentPlayer = Optional.empty();

    /**
     * This method starts the whole program.
     */
    public void run(PlayerService playerService) {
        language = setLanguage();
        languageChose = true;
        console= new Console(language);
        playerService.setConsole(console);

        while (languageChose) {
            menuToLogin(playerService);
        }

    }

    /**
     * This method returns the chosen language that the users chooses.
     * If no language is chosen the default language(English) will be used.
     *
     * @return The chosen language from a list.
     */
    private Language setLanguage() {
        LanguageMenu languageMenu = new LanguageMenu();
        languageMenu.displayMenu();
        return languageMenu.setLanguage();
    }

    /**
     * This method starts the login process.
     */
    private void menuToLogin(PlayerService playerService) {
        menu = new LoginMenu(language);
        while (running) {
            menu.displayMenu();
            int chosenInt = ScannerHelper.getInt(menu.getMenuOptions().size());
            switch (chosenInt) {
                case 1 -> loggedInMenu(playerService);
                case 2 -> playerService.createAccount();
                case 3 -> {languageChose = false; running = false;}
            }
        }
        console.printLine("Thank you for using TypeSpeeder!");
    }


    private void loggedInMenu(PlayerService playerService){
        login(playerService);
        if (currentPlayer.isEmpty()) {
            console.error("Player NOT found!");
            return;
        }
        menu = new Menu(console, currentPlayer.get());
        boolean isLoggedIn = true;
        while(isLoggedIn){
            menu.displayMenu();
            int chosenInt = ScannerHelper.getInt(menu.getMenuOptions().size());
            switch (chosenInt){
                case 1 -> playerService.updateLoginInfo(currentPlayer.get());
                case 2 -> console.print("printing information such as username, display name, level and points");
                case 3 -> console.print("choose a game");
                case 4 -> console.print("show the ranking list");
                case 5 -> isLoggedIn = false;
            }
        }
    }

    private void login(PlayerService playerService) {
        if (currentPlayer.isEmpty()) {
            currentPlayer = playerService.playerLogin();
        } else {
            console.error("you are already logged in");
        }
    }
}
