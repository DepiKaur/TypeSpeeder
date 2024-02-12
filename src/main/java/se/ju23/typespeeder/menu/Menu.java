package se.ju23.typespeeder.menu;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.service.MenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sofie Van Dingenen
 * @version 1.0
 * since 2024-02-10
 * <p>
 * <H2>Menu</H2>
 *
 * <p>
 * Menu implements  all the methods in the <i>MenuService </i> interFace.
 */

@Component
public class Menu implements MenuService {

    private Console console;


    /**
     * Initilizes a new created Menu obejct.
     * @param language Language is used to get the Console to print out the text in the correct language. Default language is English.
     */
    public Menu(Language language) {
        console = new Console(language);
    }

    public Menu() {
    }

    public void displayMenu() {
        console.printDashes();
        console.tln("menu.option.chooseOption");
        console.print(getMenuOptions());
    }

    /**
     * Returns a lsit with itmes to be shown in the menu display.
      * @return ArrayList of Strings
     */
    public ArrayList<String> getMenuOptions() {
        ArrayList<String> optionsList = new ArrayList<>();
        optionsList.add("menu.option.changeUserInfo");
        optionsList.add("menu.option.showUserInfo");
        optionsList.add("menu.option.chooseGame");
        optionsList.add("menu.option.showRankingList");
        optionsList.add("menu.option.exit");

        return optionsList;
    }

    @Override
    public int getUserChoice() {
        return 0;
    }
}
