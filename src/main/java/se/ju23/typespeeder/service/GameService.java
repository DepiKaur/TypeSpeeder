package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.GameDifficultyLevel;
import se.ju23.typespeeder.GameType;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.repo.GameRepo;
import se.ju23.typespeeder.repo.ResultRepo;

import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>GameService</h2>
 * <p>
 *     GameService is a helper class that contains methods for the game logic.
 *     This includes calculating accuracy of player's input, assigning points on the basis of accuracy
 *     and finally assigning level on the basis of achieved points.
 * @date 2024-02-10
 */
@Component
public class GameService {

    private GameRepo gameRepo;
    private ResultRepo resultRepo;

    @Autowired
    public GameService(GameRepo gameRepo, ResultRepo resultRepo) {
        this.gameRepo = gameRepo;
        this.resultRepo = resultRepo;
    }

/*
    public void loadSampleGames(boolean enabled) {

        Game game1 = new Game(GameDifficultyLevel.EASY.getDifficultyLevel(),
                GameType.WRITE_SENTENCE.getType(),
                "Heidi soon came to her, and was delighted to have the beautiful picture books to look at.");

        Game game2 = new Game(GameDifficultyLevel.MEDIUM.getDifficultyLevel(),
                GameType.WRITE_SENTENCE.getType(),
                "Punctually at nine o'clock, he left the room and anxiously went to find Miss Sesemann in the forest.");

        Game game3 = new Game(GameDifficultyLevel.HARD.getDifficultyLevel(),
                GameType.WRITE_SENTENCE.getType(),
                "Sebastian and Tinette sped to the dining-room, all rather dishevelled to find Miss Rottenmeier as cheerful as usual.");

        if (enabled) {
            gameRepo.save(game1);
            gameRepo.save(game2);
            gameRepo.save(game3);
        }
    }

 */

    public Optional<Game> getGameByLevelAndType(GameDifficultyLevel level, GameType type) {
        return gameRepo.findByDifficultyLevelAndType(level.getDifficultyLevel(), type.getType());
    }

    public void calculateResultAndSave(Player player, Game game, String userInput, int timeInMillis) {

        Evaluation evaluation = evaluateUserInput(game, userInput);
        int numOfCorrect = evaluation.numOfCorrect();
        int numOfMostCorrectInOrder = evaluation.numOfMostCorrectInOrder();
        int totalNumInGivenString = evaluation.totalNumInGivenString();

        double accuracyForCorrect = getAccuracyRoundedToTwoDigits(numOfCorrect, totalNumInGivenString);
        double accuracyForMostCorrectInOrder = getAccuracyRoundedToTwoDigits(numOfMostCorrectInOrder, totalNumInGivenString);

        int pointsForCorrect = calculatePointsFromAccuracy(accuracyForCorrect);
        int pointsForMostCorrectInOrder = calculatePointsFromAccuracy(accuracyForMostCorrectInOrder);

        Result result = new Result(player, game, pointsForCorrect, pointsForMostCorrectInOrder, timeInMillis);
        resultRepo.save(result);

        int timeInSec = Math.round((float)timeInMillis / 1000);
        System.out.println("** Result **\nCorrect: " + pointsForCorrect + " points\nCorrect in order: " +
                                pointsForMostCorrectInOrder + " points\nTime taken: " + timeInSec + " sec");
    }

    private Evaluation evaluateUserInput(Game game, String userInput) {
        Evaluation evaluation = null;

        switch (GameType.fromType(game.getType())){
            case WRITE_WORDS, CASE_SENSITIVE, SPECIAL_CHARACTERS, WRITE_SENTENCE -> {
                evaluation = evaluateInputForWordGame(game.getContent(), userInput);
            }
            case COUNT_NUMBER -> evaluation = evaluateInputForCountGame(game.getContent(), userInput);
        }
        return evaluation;
    }

    private Evaluation evaluateInputForWordGame(String gameContent, String userInput) {
        int correct = calculateNumOfCorrect(gameContent, userInput);
        int mostCorrectInOrder = calculateNumOfMostCorrectInOrder(gameContent, userInput);
        return new Evaluation(correct, mostCorrectInOrder, gameContent.length());
    }

    private Evaluation evaluateInputForCountGame(String gameContent, String userInput) {
        int numOfCorrect = Integer.parseInt(userInput);
        int numOfMostCorrectInOrder = Integer.parseInt(userInput);
        int numOfSpecialChar = calculateNumOfSpecialChars(gameContent);
        return new Evaluation(numOfCorrect, numOfMostCorrectInOrder, numOfSpecialChar);
    }

    private int calculateNumOfCorrect(String gameContent, String userInput) {
        int correct = 0;
        int index = Math.min(gameContent.length(), userInput.length());

        for (int i = 0; i < index; i++) {
            if (gameContent.charAt(i) == userInput.charAt(i)) {
                correct++;
            }
        }
        return correct;
    }

    private int calculateNumOfMostCorrectInOrder(String gameContent, String userInput) {
        int mostCorrect = 0;
        int index = Math.min(gameContent.length(), userInput.length());

        for (int i = 0; i < index; i++) {
            if (gameContent.charAt(i) == userInput.charAt(i)) {
                mostCorrect++;
            } else {
                break;
            }
        }
        return mostCorrect;
    }

    private int calculateNumOfSpecialChars(String gameContent) {
        int actualNumOfSpecialChar = 0;

        String[] parts = gameContent.split("\n");
        for (String s : parts) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '?') {                 // to calculate the no. of "?" in the given text
                    actualNumOfSpecialChar++;
                }
            }
        }

        return actualNumOfSpecialChar;
    }

    private double getAccuracyRoundedToTwoDigits(int num, int total) {
        double accuracy = ((double)num / total) * 100;
        return (Math.round(accuracy * 100.0))/100.0;
    }

    private int calculatePointsFromAccuracy(double accuracy) {
        if (accuracy > 0 && accuracy <= 20) {
            return 1;
        } else if (accuracy >= 21 && accuracy <= 40) {
            return 2;
        } else if (accuracy >= 41 && accuracy <= 60) {
            return 3;
        } else if (accuracy >= 61 && accuracy <= 85) {
            return 4;
        } else if (accuracy >= 26 && accuracy <= 99) {
            return 5;
        } else if (accuracy == 100) {
            return 10;
        } else {
            return 0;
        }
    }

}
