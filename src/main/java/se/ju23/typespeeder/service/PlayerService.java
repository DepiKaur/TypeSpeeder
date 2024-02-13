package se.ju23.typespeeder.service;

import org.springframework.stereotype.Service;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.ScannerHelper;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.repo.PlayerRepo;

import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version 1.0
 * <h2>PlayerService</h2>
 * PlayerService class has the following helper methods for the Player class:
 * <ul>
 *     <li>create a new account</li>
 *     <li>update a player's info</li>
 *     <li>validate login info</li>
 * </ul>
 * @date 2024-02-09
 */

@Service
public class PlayerService {

    private Console console;

    private PlayerRepo playerRepo;

    public PlayerService(PlayerRepo playerRepo, Console console) {
        this.playerRepo = playerRepo;
        this.console = console;
    }

    public Player createAccount() {
        String username = getValidUsername();
        String password = ScannerHelper.getStringInputForPassword();
        String displayName = getValidDisplayName();
        Player newPlayer = new Player(username, password, displayName);
        return playerRepo.save(newPlayer);
    }

    private String getValidUsername() {
        String username = null;
        boolean usernameIsValid = false;

        while (!usernameIsValid) {
            console.print("Enter username: ");
            username = ScannerHelper.getStringInputForUsernameOrDisplayName();
            if (checkIfUsernameAlreadyExists(username)) {
                console.error("Oops! Desired username is not available. Try another one!");
            } else {
                usernameIsValid = true;
            }
        }
        return username;
    }

    private String getValidDisplayName() {
        console.print("Enter display name: ");
        return ScannerHelper.getStringInputForUsernameOrDisplayName();
    }

    public boolean checkIfUsernameAlreadyExists(String username) {
        Optional<Player> optionalPlayer = playerRepo.findByUsername(username);
        return optionalPlayer.isPresent();
    }

    public boolean validatePlayerLogin() {
        console.print("Enter username: ");
        String username = ScannerHelper.validateStringInputForLogin();
        console.print("Enter password: ");
        String password = ScannerHelper.validateStringInputForLogin();

        Optional<Player> optionalPlayer = playerRepo.findByUsernameAndPassword(username, password);
        return optionalPlayer.isPresent();
    }

    public void updateLoginInfo(Player player) {
        console.t("Choose one of the following: ");
        String[] optionList = {"Update username", "Update password", "Update Display Name", "Exit"};
        console.print(optionList);
        int userChoice = ScannerHelper.getInt(optionList.length);

        switch (userChoice) {
            case 1 -> updateUsername(player);
            case 2 -> updatePassword(player);
            case 3 -> updateDisplayName(player);
            case 4 -> { }                    //TODO return to main menu
        }
    }

    private void updateUsername(Player player) {
        console.t("Current username: " + player.getUsername());
        String updatedUsername = getValidUsername();
        player.setUsername(updatedUsername);
        playerRepo.save(player);
        console.t("Username has been successfully updated!");
    }

    private void updatePassword(Player player) {
        console.t("Current password: " + player.getPassword());
        String updatedPassword = ScannerHelper.getStringInputForPassword();
        player.setPassword(updatedPassword);
        playerRepo.save(player);
        console.t("Password has been successfully updated!");
    }

    private void updateDisplayName(Player player) {
        console.t("Current display name: " + player.getDisplayName());
        String updatedDisplayName = getValidDisplayName();
        player.setDisplayName(updatedDisplayName);
        playerRepo.save(player);
        console.t("Display name has been successfully updated!");
    }

}
