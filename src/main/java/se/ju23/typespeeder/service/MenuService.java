package se.ju23.typespeeder.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Sofie Van Dingenen
 * @version 2.0
 * Since 2024-02-08
 *
 * <h2>MenuService</h2>
 *
 * MenuSrvice is an Interface which provide certain methods all Menu's should have.
 */
@Component
public interface MenuService {
     void displayMenu();

     ArrayList<String> getMenuOptions();




}
