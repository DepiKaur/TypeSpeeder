package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ju23.typespeeder.Consle.Console;
import se.ju23.typespeeder.ScannerHelper;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.repo.PlayerRepo;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 *
 * @date 2024-02-09
 */

@Service
public class PlayerService {

    @Autowired
    private Console console;

    @Autowired
    private PlayerRepo playerRepo;

    public void createAccount() {
        String username = null;
        boolean isValid = false;

        while (!isValid) {
            console.print("Enter username: ");
             username = ScannerHelper.getStringInputForLogin();
            if (isUsernameAlreadyPresent(username)) {
                console.error("Oops! Desired username is not available. Try another one!");
            } else {
                isValid = true;
            }
        }

        console.print("Enter password: ");
        String password = ScannerHelper.getStringInputForLogin();

        console.print("Enter display name: ");
        String displayName = ScannerHelper.getStringInputForLogin();

        Player newPlayer = new Player(username, password, displayName);
        playerRepo.save(newPlayer);
    }

    private boolean isUsernameAlreadyPresent(String username) {
        Player player = playerRepo.findByUsername(username);
        if (player == null) {
            return false;
        }
        return true;
    }

    public void validatePlayerLogin() {
        console.print("Enter username: ");
        String username = ScannerHelper.validateStringInputForLogin();
        String validPassword = null;
        if (isUsernameAlreadyPresent(username)) {
            Player player = playerRepo.findByUsername(username);
            validPassword = player.getPassword();
        }

        console.print("Enter password: ");
        String password = ScannerHelper.validateStringInputForLogin();
        if (!password.equals(validPassword)) {
            console.error("Username or Password does not match. Access denied !");
            return;  //TODO return to main menu
        }
        //TODO show player menu
    }

    public void updateLoginInfo() {
        console.t("Update one of the following: ");
        String[] optionList = {"Username", "Password", "Display Name", "Exit"};
        console.print(optionList);

        int userChoice = ScannerHelper.getInt();
        switch (userChoice) {
            case 1 -> updateUsername();
            case 2 -> updatePassword();
            case 3 -> updateDisplayName();
            case 4 -> { }                    //TODO return to main menu
        }
    }

    private void updateUsername() {

    }

    private void updatePassword() {

    }

    private void updateDisplayName() {

    }

}
