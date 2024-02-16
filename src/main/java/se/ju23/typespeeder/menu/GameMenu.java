package se.ju23.typespeeder.menu;

import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.service.MenuService;

import java.util.ArrayList;

/**
 * @author Sofie Van Dingenen
 * @version 1.0
 * since 2024-02-16
 * <p>
 * <H2>GameMenu</H2>
 *
 * <p>
 * Menu implements  all the methods in the <i>MenuService </i> interFace.
 */
public class GameMenu implements MenuService {

    private Console console;

    public GameMenu(Console console){
        this.console = console;
    }
    @Override
    public void displayMenu() {
        console.printDashes();
        console.tln("menu.option.chooseOption");
        console.print(getMenuOptions());
    }

    /**
     * Returns a lsit with itmes to be shown in the menu display.
     *
     * @return ArrayList of Strings
     */
    @Override
    public ArrayList<String> getMenuOptions() {
        ArrayList<String> optionsList = new ArrayList<>();
        optionsList.add("menu.option.changeUserInfo");
        optionsList.add("menu.option.showUserInfo");
        optionsList.add("menu.option.chooseGame");
        optionsList.add("menu.option.showRankingList");
        optionsList.add("menu.option.logout");

        return optionsList;
    }

}
