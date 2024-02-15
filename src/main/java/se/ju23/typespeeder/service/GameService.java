package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
 * @version <h2></h2>
 * @date 2024-02-10
 */
@Service
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
                GameType.COUNT_NUMBER.getType(),
                """
                        I hope children will forgive ? me if I dedicate
                        book to ? grown-up. I have a serious excuse: this
                        grown-up is the best friend I have in the world. I
                        have another excuse: this ? grown-up understands
                        ? everything, even books for children.""");

        Game game2 = new Game(GameDifficultyLevel.MEDIUM.getDifficultyLevel(),
                GameType.COUNT_NUMBER.getType(),
                """
                        I hope children will forgive ? me if I ded??cate
                        book to ? grown-up. I have a serious excuse: this
                        grown-up ?? the best friend I have ? the world. I
                        have another excuse: this ? grown-up understands
                        ? everything, e?en books for ? children.""");

        Game game3 = new Game(GameDifficultyLevel.HARD.getDifficultyLevel(),
                GameType.COUNT_NUMBER.getType(),
                """
                        I hope childr@n will forgive ? me if I ded??cate
                        book to ? grown-up. I have @ serious excuse: this
                        grown-up ?? the best friend # have ? the world. I
                        have @nother exc#se: this ? grown#up underst@nds
                        ? everything, e?en b@@ks for ? children.""");

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

    public void calculateResultAndSave(Player player, Game game, String userInput, int timeTaken) {

        Evaluation evaluation = evaluateUserInput(game, userInput);
        int numOfCorrect = evaluation.numOfCorrect();
        int numOfMostCorrectInOrder = evaluation.numOfMostCorrectInOrder();
        int totalNumInGivenString = evaluation.totalNumInGivenString();

        double accuracyForCorrect = getAccuracyRoundedToTwoDigits(numOfCorrect, totalNumInGivenString);
        double accuracyForMostCorrectInOrder = getAccuracyRoundedToTwoDigits(numOfMostCorrectInOrder, totalNumInGivenString);

        int pointsForCorrect = calculatePointsFromAccuracy(accuracyForCorrect);
        int pointsForMostCorrectInOrder = calculatePointsFromAccuracy(accuracyForMostCorrectInOrder);

        Result result = new Result(player, game, pointsForCorrect, pointsForMostCorrectInOrder, timeTaken);
        System.out.println(result);
    }

    private Evaluation evaluateUserInput(Game game, String userInput) {
        Evaluation evaluation = null;

        switch (GameType.fromType(game.getType())){
            case WRITE_WORDS, CASE_SENSITIVE -> evaluation = evaluateInputForWordGame(game.getContent(), userInput);
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
