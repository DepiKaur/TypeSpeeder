package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Consle.Console;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Sofie Van Dingenen
 * @version 1.0
 * @date 2024-02-08
 */
@Component
public interface MenuService {
    public void displayMenu();

    public ArrayList<String> getMenuOptions();

}
