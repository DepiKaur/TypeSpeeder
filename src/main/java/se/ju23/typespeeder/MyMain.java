package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.menu.MenuHandler;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.service.GameService;
import se.ju23.typespeeder.service.PlayerService;

import java.util.Optional;

@Component
public class MyMain implements CommandLineRunner {

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private GameService gameService;
    private PlayerService playerService;

    private MenuHandler menu ;

    @Autowired
    public MyMain(PlayerService playerService, MenuHandler menu) {
        this.playerService = playerService;
        this.menu = menu;
    }

    @Override
    public void run(String... args) throws Exception {
        menu.run(playerService);

        /*

        Optional<Player> p1 = playerRepo.findByUsername("David");
        if (p1.isEmpty()) {
            System.out.println("<<<<<<<<<<<<<< Player NOT found >>>>>>>>>>>>>>>");
            return;
        }
        Optional<Game> opGame = gameService.getGameByLevelAndType(GameDifficultyLevel.EASY, GameType.WRITE_WORDS);
        if (opGame.isEmpty()) {
            System.out.println("<<<<<<<<<<<<<<<   Desired game NOT found  >>>>>>>>>>>>>>>>>");
            return;
        }

        //TODO this part should be removed to that place where the user starts playing a game (to calculate time-taken)
        System.out.println(opGame.get().getContent());
        long startTime = System.currentTimeMillis();
        String userInput = ScannerHelper.getStringInput();
        long stopTime = System.currentTimeMillis();

        int timeTakenInMilliSec = Math.round(stopTime - startTime);

        gameService.calculateResultAndSave(p1.get(),opGame.get(), userInput, timeTakenInMilliSec);

         */

    }

}
