package se.ju23.typespeeder.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.NewsLetter;
import se.ju23.typespeeder.Patch;
import se.ju23.typespeeder.consle.Color;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.service.GameService;
import se.ju23.typespeeder.service.MenuService;
import se.ju23.typespeeder.service.PlayerService;
import se.ju23.typespeeder.util.RankUtil;
import se.ju23.typespeeder.util.ScannerHelper;

import java.util.ArrayList;
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
    @Autowired
    private RankUtil rankUtil;
    private Console console;
    private MenuService menu;
    private boolean languageChose = false;
    private boolean running = true;
    private static Language language;
    private GameService gameService;
    private NewsLetter newsLetter = new NewsLetter();

    private Optional<Player> currentPlayer = Optional.empty();

    /**
     * This method starts the whole program.
     */
    public void run(PlayerService playerService, GameService gameService) {
        this.gameService = gameService;
        language = setLanguage();
        console = new Console(language);
        showVersion();
        playerService.setConsole(console);
        gameService.setConsole(console);

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
                case 3 -> running = false;
            }
        }
        console.tln("menu.thankyou");
    }

    private void loggedInMenu(PlayerService playerService) {
        login(playerService);
        if (currentPlayer.isEmpty()) {
            console.error("Incorrect Username or Password!");
            return;
        }
        menu = new GameMenu(console, currentPlayer.get(), gameService);
        showNewsletter();
        while (currentPlayer.isPresent()) {
            menu.displayMenu();
            int chosenInt = ScannerHelper.getInt(menu.getMenuOptions().size());
            switch (chosenInt) {
                case 1 -> playerService.updateLoginInfo(currentPlayer.get());
                case 2 -> console.print("printing information such as username, display name, level and points");
                case 3 -> gameService.startGame(currentPlayer.get());
                case 4 -> showRankingList();
                case 5 -> showNewsletter();
                case 6 -> currentPlayer = Optional.empty();
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

    public void showRankingList() {
        console.printDashes(Color.BLUE);
        console.tln("rankinglist.title", Color.BLUE);

        console.printDashes(Color.BLUE);
        console.tln("rankinglist.show", Color.BLUE);
        console.printLine("--------------------------------------------------------------------------", Color.BLUE);

        printList(rankUtil.calculateRank());

    }

    public void printList(ArrayList<String> stringList) {
        for (int i = 0; i < stringList.size(); i++) {
            console.print(i + 1 + ".", Color.BLUE);
            console.printLine(stringList.get(i), Color.BLUE);
        }
    }
    private void showNewsletter(){
        console.printDashes();
        console.t("newsletter");
        console.printLine(newsLetter.toString());
    }

    private void showVersion(){
        Patch patch = new Patch();
        patch.setPatchVersion("1.1.2");
        console.printDashes();
        console.printLine("Version: " +patch.getPatchVersion());

    }
}
