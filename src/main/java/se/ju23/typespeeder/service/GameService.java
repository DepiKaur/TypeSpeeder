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
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.repo.ResultRepo;

import java.util.List;
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
    private PlayerRepo playerRepo;

    @Autowired
    public GameService(GameRepo gameRepo, ResultRepo resultRepo, PlayerRepo playerRepo, Console console) {
        this.gameRepo = gameRepo;
        this.resultRepo = resultRepo;
        this.playerRepo = playerRepo;
        this.console = console;
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
    public void calculateAndSaveResult(Player player, Game game, String userInput, int timeInMilli) {

        UserInputEvaluation userInputEvaluation = evaluateUserInput(game, userInput);
        int numOfCorrect = userInputEvaluation.numOfCorrect();
        int numOfMostCorrectInOrder = userInputEvaluation.numOfMostCorrectInOrder();
        int totalNumInGivenString = userInputEvaluation.totalNumInGivenString();

        double accuracyForCorrect = getAccuracyRoundedToTwoDigits(numOfCorrect, totalNumInGivenString);
        double accuracyForMostCorrectInOrder = getAccuracyRoundedToTwoDigits(numOfMostCorrectInOrder, totalNumInGivenString);

        int pointsForCorrect = calculatePointsFromAccuracy(accuracyForCorrect);
        int pointsForMostCorrectInOrder = calculatePointsFromAccuracy(accuracyForMostCorrectInOrder);

        PointsEvaluation pointsEvaluation = evaluatePointsForBonusOrDeduction(player, pointsForCorrect);
        int bonusPoints = pointsEvaluation.bonusPoints();
        int deductedPoints = pointsEvaluation.pointsToBeDeducted();

        Result result = new Result(player, game, pointsForCorrect, pointsForMostCorrectInOrder, timeInMilli, bonusPoints, deductedPoints);
        resultRepo.save(result);
        printResult(result);
        int updatedLevel = ResultUtil.getLevelFromPoints(getTotalPointsOfPlayer(player));
        player.setLevel(updatedLevel);
        playerRepo.save(player);
    }

    private PointsEvaluation evaluatePointsForBonusOrDeduction(Player player, int pointsForCorrect) {
        int bonusPoints = 0, deductedPoints = 0;

        if (pointsForCorrect == 10 && isEligibleForBonus(player)) {
            bonusPoints = calculateBonusPoints(player);
        } else if (pointsForCorrect == 0 && isEligibleForDeduction(player)) {
            if (isPointDeductionAllowed(player)) {
                deductedPoints = -1;
            }
        }
        return new PointsEvaluation(bonusPoints, deductedPoints);
    }

    private boolean isEligibleForBonus(Player player) {
        List<Result> listOfLastTwoResultsOfPlayer = resultRepo.listOfTwoMostRecentResultsOfPlayer(player.getId());

        if (listOfLastTwoResultsOfPlayer.size() == 2) {
            int result1 = listOfLastTwoResultsOfPlayer.get(0).getPointsForCorrect();
            int result2 = listOfLastTwoResultsOfPlayer.get(1).getPointsForCorrect();
            return result1 == 10 && result2 == 10;
        }
        return false;
    }

    private int calculateBonusPoints(Player player) {
        int pointsForCorrect = 10;
        int totalPointsOfPlayer = getTotalPointsOfPlayer(player) + pointsForCorrect;
        int nextLevel = player.getLevel() + 1;
        int minPointsToGoToNextLevel = ResultUtil.getMinimumPointsForLevel(nextLevel);
        return (minPointsToGoToNextLevel - totalPointsOfPlayer);
    }

    private boolean isEligibleForDeduction(Player player) {
        List<Result> listOfTwoRecentResultsForDeduction = resultRepo.listOfResultsForDeduction(player.getId());

        if (listOfTwoRecentResultsForDeduction.size() == 2) {
            int result1 = listOfTwoRecentResultsForDeduction.get(0).getPointsForCorrect();
            int result2 = listOfTwoRecentResultsForDeduction.get(1).getPointsForCorrect();
            return result1 == 0 && result2 == 0;
        }
        return false;
    }

    private boolean isPointDeductionAllowed(Player player) {
        int currentLevel = player.getLevel();
        int minPointsForCurrentLevel = ResultUtil.getMinimumPointsForLevel(currentLevel);
        int currentPoints = getTotalPointsOfPlayer(player);
        if (currentPoints == 0) {
            return false;
        }
        return currentPoints > minPointsForCurrentLevel;
    }

    private int getTotalPointsOfPlayer(Player player) {
        int playerId = player.getId();
        int sumOfBonusPoints = resultRepo.sumOfBonusPointsOfPlayer(playerId);
        int sumOfPointsForCorrect = resultRepo.sumOfPointsOfAPlayer(playerId);
        int sumOfDeductedPoints = resultRepo.sumOfDeductedPointsOfPlayer(playerId);
        return sumOfPointsForCorrect + sumOfBonusPoints + sumOfDeductedPoints;
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
        Player player = result.getPlayer();
        int pointsForCorrect = result.getPointsForCorrect();
        int pointsForMostCorrectInOrder = result.getPointsForCorrectInOrder();
        int timeInMilli = result.getTimeTakenInMilliSec();
        int timeInSec = Math.round((float)timeInMilli / 1000);

        console.tln("\n            ********   RESULT   ********");
        System.out.printf("%n%s \t\t%20s \t%15s%n", "Correct", "Most Correct in order", "Time taken");
        System.out.printf("%4d p %20d p %20d s%n%n", pointsForCorrect, pointsForMostCorrectInOrder, timeInSec);

        int currentLevel = player.getLevel();

        console.tln("Current Level: " + currentLevel);
        int totalPoints = getTotalPointsOfPlayer(player);
        console.tln("Total points: " + totalPoints);

        if (result.getBonusPoints() != 0) {
            console.tln("BONUS: " + result.getBonusPoints() + " p");
            player.setLevel(currentLevel + 1);
            currentLevel = player.getLevel();
        } else if (result.getDeductedPoints() != 0) {
            console.error("Deducted points: " + result.getDeductedPoints() + " p");
        }

        int nextLevel = currentLevel + 1;
        int pointsNeededToGoToNextLevel = ResultUtil.getMinimumPointsForLevel(nextLevel) - totalPoints;
        console.tln("Points needed to go to Level " + nextLevel + ": " + pointsNeededToGoToNextLevel);
    }

    public void printWarnings(Player player) {
        if (isEligibleForBonus(player)) {
            console.error("IMPORTANT: Try to get 10 points in the next game for added BONUS !!");
        } else if (isEligibleForDeduction(player)) {
            console.error("WARNING: Try to get some points in the next game to AVOID POINTS DEDUCTION !!");
        }
    }

    /**
     * This method creates an evaluation of the user input depending on the type of game that was played.
     * @param game This is used to get the type of game that was played by the user.
     * @param userInput This is the user input.
     * @return An object of type <i>UserInputEvaluation</i> which is a record.
     */
    private UserInputEvaluation evaluateUserInput(Game game, String userInput) {
        UserInputEvaluation userInputEvaluation = null;

        switch (GameType.fromType(game.getType())){
            case WRITE_WORDS, CASE_SENSITIVE, SPECIAL_CHARACTERS, WRITE_SENTENCE -> {
                userInputEvaluation = evaluateInputForWordGame(game.getContent(), userInput);
            }
            case COUNT_NUMBER -> userInputEvaluation = evaluateInputForCountGame(game.getContent(), userInput);
        }
        return userInputEvaluation;
    }

    private UserInputEvaluation evaluateInputForWordGame(String gameContent, String userInput) {
        int correct = calculateNumOfCorrect(gameContent, userInput);
        int mostCorrectInOrder = calculateNumOfMostCorrectInOrder(gameContent, userInput);
        return new UserInputEvaluation(correct, mostCorrectInOrder, gameContent.length());
    }

    private UserInputEvaluation evaluateInputForCountGame(String gameContent, String userInput) {

        int numOfCorrect = 0;
        int numOfMostCorrectInOrder = 0;
        try {
            numOfCorrect = Integer.parseInt(userInput);
            numOfMostCorrectInOrder = Integer.parseInt(userInput);
        } catch(NumberFormatException ignored) {

        }
        int numOfSpecialChar = calculateNumOfQuestionMarks(gameContent);
        return new UserInputEvaluation(numOfCorrect, numOfMostCorrectInOrder, numOfSpecialChar);
    }

}
