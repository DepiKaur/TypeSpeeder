package se.ju23.typespeeder.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.game.GameDifficultyLevel;
import se.ju23.typespeeder.game.GameType;
import se.ju23.typespeeder.util.ScannerHelper;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.service.GameService;
import se.ju23.typespeeder.service.MenuService;
import se.ju23.typespeeder.service.PlayerService;
import se.ju23.typespeeder.service.ResultService;

import java.util.*;

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
    private ResultService resultService;
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
        menu = new GameMenu(console);
        while (currentPlayer.isPresent()) {
            menu.displayMenu();
            int chosenInt = ScannerHelper.getInt(menu.getMenuOptions().size());
            switch (chosenInt) {
                case 1 -> playerService.updateLoginInfo(currentPlayer.get());
                case 2 -> console.print("printing information such as username, display name, level and points");
                case 3 -> startGame(currentPlayer.get());
                case 4 -> showRankingList();
                case 5 -> currentPlayer = Optional.empty();
            }
        }
    }

    public void startGame(Player player) {
        console.printDashes();
        GameType gameChoice = ScannerHelper.getGameType(GameType.values());
        GameDifficultyLevel difficultyLevel = ScannerHelper.getDificultyLevel(GameDifficultyLevel.values());
        Optional<Game> optionalGame = gameService.getGameByLevelAndType(difficultyLevel, gameChoice);

        if (optionalGame.isEmpty()) {
            console.error("Desired game NOT found!");
            return;
        }
        console.printDashes();
        gameService.printWarnings(player);
        console.printDashes();
        String content = optionalGame.get().getContent();
        console.printLine(content);

        long startTime = System.currentTimeMillis();
        String userInput = ScannerHelper.getStringInput();
        long stopTime = System.currentTimeMillis();
        int timeTakenInMilliSec = Math.round(stopTime - startTime);

        gameService.calculateAndSaveResult(player, optionalGame.get(), userInput, timeTakenInMilliSec);
    }

    private void login(PlayerService playerService) {
        if (currentPlayer.isEmpty()) {
            currentPlayer = playerService.playerLogin();
        } else {
            console.error("you are already logged in");
        }
    }

    private void showRankingList() {
        console.printDashes();
        console.tln("rankinglist.title");

        console.printDashes();
        console.tln("rankinglist.show");
        console.printLine("----------------------------------------------------------------");

       console.printList(calculateRank());

        }


    private ArrayList<String> calculateRank(){
        ArrayList<String> rankingList;
        List<String> mostCorrect = resultService.sortRank(resultService.getPlayerAverageMostCorrectPoints());
        List< String> fastest = resultService.sortRank(resultService.getPlayerAverageTime());
        List< String> mostCorrectInOrder = resultService.sortRank(resultService.getPlayerAverageMostCorrectPointsInOrder());

        ArrayList<String> templist =combine(fastest, mostCorrect);
        rankingList = combine(templist, mostCorrectInOrder);

        return rankingList;
    }
    /*private List<String> sortRank(HashMap<String, Integer> rankingList) {
        ArrayList<String> sortedList = new ArrayList<>();
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(rankingList.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

               return o1.getValue().compareTo(o2.getValue());
            }
        });

        for(Map.Entry<String, Integer> entry: entries){
            sortedList.add(entry.getKey() +": "+ entry.getValue());
        }

                return sortedList;
    }*/

    /**
     * Returns a combined ArrayList of two lists.
     * @param list1 List that will be combined, must be of the same length as the other list
     * @param list2 second list that will be combined, must have the same length as the other list
     * @return A combined ArrayList looking like: display name: score | display name: score
     */
    private ArrayList<String> combine(List<String> list1, List<String> list2){
        ArrayList<String> newList = new ArrayList<>();
        for(int i=0; i <list1.size(); i++){
            newList.add(list1.get(i)+"    |   " + list2.get(i));
        }
        return newList;
    }
}
