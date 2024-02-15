package se.ju23.typespeeder.menu;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.GameDifficultyLevel;
import se.ju23.typespeeder.GameType;
import se.ju23.typespeeder.ScannerHelper;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.service.GameService;
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
    private GameService gameService;

    private Optional<Player> currentPlayer = Optional.empty();

    /**
     * This method starts the whole program.
     */
    public void run(PlayerService playerService, GameService gameService) {
        this.gameService = gameService;
        language = setLanguage();
        console = new Console(language);
        playerService.setConsole(console);

        while (running) {
            menuToLogin(playerService, new LoginMenu(console));
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
    private void menuToLogin(PlayerService playerService, MenuService menu) {
        while (running) {
            menu.displayMenu();
            int chosenInt = ScannerHelper.getInt(menu.getMenuOptions().size());
            switch (chosenInt) {
                case 1 -> loggedInMenu(playerService);
                case 2 -> playerService.createAccount();
                case 3 -> {
                    running = false;
                }
            }
        }
        console.printLine("Thank you for using TypeSpeeder!");
    }


    private void loggedInMenu(PlayerService playerService) {
        login(playerService);
        if (currentPlayer.isEmpty()) {
            console.error("Player NOT found!");
            return;
        }
        menu = new Menu(console, currentPlayer.get());
        while (currentPlayer.isPresent()) {
            menu.displayMenu();
            int chosenInt = ScannerHelper.getInt(menu.getMenuOptions().size());
            switch (chosenInt) {
                case 1 -> playerService.updateLoginInfo(currentPlayer.get());
                case 2 -> console.print("printing information such as username, display name, level and points");
                case 3 -> startGame();
                case 4 -> console.print("show the ranking list");
                case 5 -> currentPlayer = Optional.empty();
            }
        }
    }

    public void startGame() {
        console.printDashes();
        GameType gamechoice = ScannerHelper.getGameType(GameType.values());
        GameDifficultyLevel dificultyLevel = ScannerHelper.getDificultyLevel(GameDifficultyLevel.values());
        Optional<Game> currentGame = gameService.getGameByLevelAndType(dificultyLevel, gamechoice);

        String content = currentGame.get().getContent();
        console.printLine(content);
        long startTime = System.currentTimeMillis();
        String userInput = ScannerHelper.getStringInput();
        long stopTime = System.currentTimeMillis();
        int timeTakenInMilliSec = Math.round(stopTime - startTime);

        gameService.calculateResultAndSave(currentPlayer.get(), currentGame.get(), userInput, timeTakenInMilliSec);
    }


    private void login(PlayerService playerService) {
        if (currentPlayer.isEmpty()) {
            currentPlayer = playerService.playerLogin();
        } else {
            console.error("you are already logged in");
        }
    }
}
