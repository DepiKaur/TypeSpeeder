package se.ju23.typespeeder.menu;

import se.ju23.typespeeder.Patch;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.consle.Language;
import se.ju23.typespeeder.util.ScannerHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Sofie Van Dingenen
 * @version 1.0.0
 * Since 2024-02-08
 *
 * <h2>LanguageMenu</h2>
 * <p>
 * LanguageMenu class has methods to show and choose a language.
 */
public class LanguageMenu {
    private Console console = new Console();

    public void displayMenu() {
        console.printDashes();
        console.print("TypeSpeeder");
        showVersion();
        console.print("""
                                    
                welcome/ välkommen 
                --------------------------------------------------------------------------
                                    
                Choose a language to continue/ Välj ett språk för att fortsätta
                     
                """);

    }

    private ArrayList<String> getMenuOptions() {
        ArrayList<String> list = new ArrayList<>();
        list.add("English");
        list.add("Svenska");
        return list;
    }

    /**
     * Returns a language in a list chosen by the user.
     *
     * @return the language chosen by the user.
     */
    public Language setLanguage() {
        console.print(getMenuOptions());
        int chosenInt = ScannerHelper.getInt(getMenuOptions().size());
        if (chosenInt == 1) {
            console.printLine("English is chosen");
            return new Language("eng");
        }
        if (chosenInt == 2) {
            System.out.println("Svenska valt");
            return new Language("sv");
        }
        return new Language();
    }
    private void showVersion(){
        Patch patch = new Patch();
        patch.setPatchVersion("1.1.2");
        console.printLine(" Version: " +patch.getPatchVersion());

    }
}
