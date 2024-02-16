package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.GameDifficultyLevel;
import se.ju23.typespeeder.GameType;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.repo.GameRepo;
import se.ju23.typespeeder.repo.ResultRepo;

import java.util.Optional;

import static se.ju23.typespeeder.service.ResultUtil.calculateNumOfCorrect;
import static se.ju23.typespeeder.service.ResultUtil.calculateNumOfMostCorrectInOrder;
import static se.ju23.typespeeder.service.ResultUtil.calculateNumOfQuestionMarks;
import static se.ju23.typespeeder.service.ResultUtil.calculatePointsFromAccuracy;
import static se.ju23.typespeeder.service.ResultUtil.getAccuracyRoundedToTwoDigits;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>GameService</h2>
 * <p>
 *  GameService is a helper class that contains methods for the game logic.
 *  This class contains method for calculating a game's result, printing it
 *  in the terminal as well as saving it in the database.
 * <p>
 *    It even contains methods to evaluate user input, depending on the type
 *    of game played by the user.
 * </p>
 * @date 2024-02-10
 */
@Component
public class GameService {

    private Console console;
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

    /**
     * This method returns optional game using its difficulty level and type.
     * @param level This is of type GameDifficultyLevel which is an enum.
     * @param type This is of type GameType which is an enum.
     * @return Optional game
     */

    public Optional<Game> getGameByLevelAndType(GameDifficultyLevel level, GameType type) {
        return gameRepo.findByDifficultyLevelAndType(level.getDifficultyLevel(), type.getType());
    }

    /**
     * This method calculates the result of a game played by the user and saves it in the database.
     * @param player The user which plays a game.
     * @param game The game played by the user.
     * @param userInput The input given by the user.
     * @param timeInMilli The time (in milliseconds) user takes to write input.
     * @return The result of the game played by the user.
     */
    public Result calculateResultAndSave(Player player, Game game, String userInput, int timeInMilli) {

        Evaluation evaluation = evaluateUserInput(game, userInput);
        int numOfCorrect = evaluation.numOfCorrect();
        int numOfMostCorrectInOrder = evaluation.numOfMostCorrectInOrder();
        int totalNumInGivenString = evaluation.totalNumInGivenString();

        double accuracyForCorrect = getAccuracyRoundedToTwoDigits(numOfCorrect, totalNumInGivenString);
        double accuracyForMostCorrectInOrder = getAccuracyRoundedToTwoDigits(numOfMostCorrectInOrder, totalNumInGivenString);

        int pointsForCorrect = calculatePointsFromAccuracy(accuracyForCorrect);
        int pointsForMostCorrectInOrder = calculatePointsFromAccuracy(accuracyForMostCorrectInOrder);

        Result result = new Result(player, game, pointsForCorrect, pointsForMostCorrectInOrder, timeInMilli);
        return resultRepo.save(result);
    }

    /**
     * This method will print the result of a game which includes
     * <ul>
     *     <li>points for correct</li>
     *     <li>points for most correct in order</li>
     *     <li>time taken (in seconds)</li>
     * </ul>
     * @param result This is used to print the result in the terminal.
     */
    public void printResult(Result result) {
        int pointsForCorrect = result.getPointsForCorrect();;
        int pointsForMostCorrectInOrder = result.getPointsForCorrectInOrder();
        int timeInMilli = result.getTimeTakenInMilliSec();

        int timeInSec = Math.round((float)timeInMilli / 1000);
        console.tln("** Result **\nCorrect: " + pointsForCorrect + " points\nCorrect in order: " +
                pointsForMostCorrectInOrder + " points\nTime taken: " + timeInSec + " sec");
    }

    /**
     * This method creates an evaluation of the user input depending on the type of game that was played.
     * @param game This is used to get the type of game that was played by the user.
     * @param userInput This is the user input.
     * @return An object of type <i>Evaluation</i> which is a record.
     */
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
        int numOfSpecialChar = calculateNumOfQuestionMarks(gameContent);
        return new Evaluation(numOfCorrect, numOfMostCorrectInOrder, numOfSpecialChar);
    }

}
