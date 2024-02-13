package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.repo.GameRepo;
import se.ju23.typespeeder.service.GameService;
import se.ju23.typespeeder.service.GuessEvaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class MyMain implements CommandLineRunner {

    @Autowired
    GameRepo gRepo;

    @Autowired
    GameService gameService;

    @Autowired
    private MenuService menuService;
    @Override
    public void run(String... args) throws Exception {
        //menuService.displayMenu();
/*
        List<Game> allGames = gRepo.findAll();
        for (Game g : allGames) {
            //System.out.println(g);
            if ((g.getDifficultyLevel().equals("Easy")) && (g.getType().equals("Number of appearances"))) {
                System.out.println(g.getId());
            }
        }

 */




        String difficultyLevel = GameDifficultyLevel.EASY.getDifficultyLevel();
        String type = GameType.WRITE_WORDS.getType();
        Optional<Game> opGame = gRepo.findByDifficultyLevelAndType(difficultyLevel, type);
        if (opGame.isEmpty()) {
            System.out.println("<<<<<<<<<<<<<<<   Desired game NOT found  >>>>>>>>>>>>>>>>>");
            return;
        }

        int lengthOfGivenString = opGame.get().getContent().length();
        System.out.println(opGame.get().getContent());
        long startTime = System.currentTimeMillis();
        String userInput = ScannerHelper.getStringInput();
        long stopTime = System.currentTimeMillis();
        GuessEvaluation guessEva = gameService.calculatePointsForWordGame(opGame.get().getContent(), userInput);

        int numOfCorrect = guessEva.numOfCorrect();
        int numOfMostCorrectInOrder = guessEva.numOfMostCorrectInOrder();
        int timeTakenInMilliSec = Math.round(stopTime - startTime);

        System.out.println("Correct: " + numOfCorrect);
        System.out.println("Most correct: " + numOfMostCorrectInOrder);

        /*
        double accuracyForCorrect = (numOfCorrect / lengthOfGivenString) * 100;
        double accuracyForMostCorrectInOrder = (numOfMostCorrectInOrder / lengthOfGivenString) * 100;

        System.out.println("Accuracy for correct: " + accuracyForCorrect);
        System.out.println("Accuracy for most correct in order: " + accuracyForMostCorrectInOrder);
        System.out.println("Time taken: " + timeTakenInMilliSec + " ms");

         */
    }
}
