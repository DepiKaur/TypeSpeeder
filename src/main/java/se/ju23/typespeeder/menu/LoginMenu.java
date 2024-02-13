package se.ju23.typespeeder.menu;

import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.service.MenuService;

import java.util.ArrayList;

/**
 * @author Sofie Van Dingene
 * @version 1.0.0
 * Since 2024-02-12
 *
 * <h2>LoginMenu</h2>
 * <p>
 * LoginMenu implements all the methods in the <i>MenuService</i> interface.
 */

public class LoginMenu implements MenuService {
    private final Console console;

    private ArrayList<String> list;

    public LoginMenu(Language language) {
        console = new Console(language);
    }

    @Override
    public void displayMenu() {
        console.printDashes();

        console.tln("menu.welcome.typeSpeeder");
        console.tln("menu.option.chooseOption");
        console.print(getMenuOptions());
    }

    @Override
    public ArrayList<String> getMenuOptions() {
        list = new ArrayList<>();
        list.add("menu.option.login");
        list.add("menu.option.newUser");
        list.add("menu.option.exit");

        return list;
    }

    @Override
    public int getUserChoice() {
        return 0;
    }

}
