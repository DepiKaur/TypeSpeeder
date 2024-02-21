package se.ju23.typespeeder.menu;

import org.springframework.stereotype.Component;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.service.MenuService;

import java.util.ArrayList;

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




    public Menu() {
    }

    public void displayMenu() {
        System.out.println("------------------------");
        System.out.println("menu.option.chooseOption");
        for(String s: getMenuOptions()){
            System.out.println(s);
        }
    }

    /**
     * Returns a lsit with itmes to be shown in the menu display.
     *
     * @return ArrayList of Strings
     */
    public ArrayList<String> getMenuOptions() {
        ArrayList<String> optionsList = new ArrayList<>();
        optionsList.add("Välj språk (svenska/engelska):");
        optionsList.add("Svenska valt.");
        optionsList.add("menu.option.chooseGame");
        optionsList.add("menu.option.showRankingList");
        optionsList.add("menu.option.logout");

        return optionsList;
    }
}
