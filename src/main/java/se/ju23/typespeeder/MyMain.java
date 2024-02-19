package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.menu.MenuHandler;
import se.ju23.typespeeder.service.GameService;
import se.ju23.typespeeder.service.PlayerService;

@Component
public class MyMain implements CommandLineRunner {

    @Autowired
    private GameService gameService;
    private PlayerService playerService;

    private MenuHandler menu;
    private Console console = new Console();

    @Autowired
    public MyMain(PlayerService playerService, MenuHandler menu) {
        this.playerService = playerService;
        this.menu = menu;
    }

    @Override
    public void run(String... args) {
        menu.run(playerService, gameService);
    }

}
