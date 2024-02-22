package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.game.GameDifficultyLevel;
import se.ju23.typespeeder.game.GameType;
import se.ju23.typespeeder.repo.GameRepo;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.repo.ResultRepo;
import se.ju23.typespeeder.util.PointsEvaluation;
import se.ju23.typespeeder.util.ResultUtil;
import se.ju23.typespeeder.util.ScannerHelper;
import se.ju23.typespeeder.util.UserInputEvaluation;

import java.util.List;
import java.util.Optional;

import static se.ju23.typespeeder.util.ResultUtil.*;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>GameService</h2>
 * <p>
 * GameService is a helper class that contains methods for the game logic.
 * This class contains methods for calculating a game's result, printing it
 * in the terminal as well as saving it in the database.
 * <p>
 * It even contains methods to evaluate user input, depending on the type
 * of game played by the user.
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

    public void setConsole(Console console) {
        this.console = console;
    }

    /**
     * This method returns optional game using its difficulty level and type.
     *
     * @param level This is of type GameDifficultyLevel which is an enum.
     * @param type  This is of type GameType which is an enum.
     * @return Optional game
     */
    public Optional<Game> getGameByLevelAndType(GameDifficultyLevel level, GameType type) {
        return gameRepo.findByDifficultyLevelAndType(level.getDifficultyLevel(), type.getType());
    }

    /**
     * This method calculates the result of a game played by the user and saves it in the database.
     *
     * @param player      The user which plays a game.
     * @param game        The game played by the user.
     * @param userInput   The input given by the user.
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

    /**
     * This method evaluates points earned by user to find the bonus points
     * (if the user performs well for 3rd time in a row) or points that need to be deducted
     * (if the user performs bad for 3rd time in a row, and the user hasn't yet reached the
     * minimum number of points for current level).
     *
     * @param player           The user who is currently logged in the system.
     * @param pointsForCorrect The points earned by the user for input.
     * @return A new object of type PointsEvaluation record to save both bonus and deducted points.
     */
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

    /**
     * This method checks two recent results of the current user in the database.
     * If the user got maximum points in both, he gets eligible to get a bonus.
     *
     * @param player The user who is currently logged in the system.
     * @return Returns true if the user is eligible for bonus and false otherwise.
     */
    private boolean isEligibleForBonus(Player player) {
        List<Result> listOfLastTwoResultsOfPlayer = resultRepo.listOfTwoMostRecentResultsOfPlayer(player.getId());

        if (listOfLastTwoResultsOfPlayer.size() == 2) {
            int result1 = listOfLastTwoResultsOfPlayer.get(0).getPointsForCorrect();
            int result2 = listOfLastTwoResultsOfPlayer.get(1).getPointsForCorrect();
            return result1 == 10 && result2 == 10;
        }
        return false;
    }

    /**
     * This method calculates the bonus points awarded to the user to go to the next level.
     *
     * @param player The user who is currently logged in the system.
     * @return The bonus points awarded to the current user.
     */
    private int calculateBonusPoints(Player player) {
        int pointsForCorrect = 10;
        int totalPointsOfPlayer = getTotalPointsOfPlayer(player) + pointsForCorrect;
        int nextLevel = player.getLevel() + 1;
        int minPointsToGoToNextLevel = ResultUtil.getMinimumPointsForLevel(nextLevel);
        return (minPointsToGoToNextLevel - totalPointsOfPlayer);
    }

    /**
     * This method checks if the user is eligible for deduction of points.
     * It makes sure that the user does not drop game-level, even though he loses points for
     * performing poorly three times in a row.
     *
     * @param player The user who is currently logged in the system.
     * @return True if it is allowed to deduct points, and false otherwise.
     */
    private boolean isEligibleForDeduction(Player player) {
        List<Result> listOfTwoRecentResultsForDeduction = resultRepo.listOfTwoMostRecentResultsOfPlayer(player.getId());

        if (listOfTwoRecentResultsForDeduction.size() == 2) {
            int result1 = listOfTwoRecentResultsForDeduction.get(0).getPointsForCorrect();
            int result2 = listOfTwoRecentResultsForDeduction.get(1).getPointsForCorrect();
            return result1 == 0 && result2 == 0;
        }
        return false;
    }

    /**
     * This method checks the total points earned by the user and makes sure the value does not
     * go below the minimum value needed to remain at the current level.
     *
     * @param player The user who is currently logged in the system.
     * @return True if the total points of the user is more than the minimum points at the current level,
     * and otherwise false.
     */
    private boolean isPointDeductionAllowed(Player player) {
        int currentLevel = player.getLevel();
        int minPointsForCurrentLevel = ResultUtil.getMinimumPointsForLevel(currentLevel);
        int currentPoints = getTotalPointsOfPlayer(player);
        if (currentPoints == 0) {
            return false;
        }
        return currentPoints > minPointsForCurrentLevel;
    }

    /**
     * This method calculates the total points earned by the player in all the games played.
     *
     * @param player The user who is currently logged in the system.
     * @return The total number of points including bonus and deductions.
     */
    public int getTotalPointsOfPlayer(Player player) {
        Optional<List<Result>> resultList = resultRepo.findByPlayerId(player.getId());
        if (resultList.isEmpty() || resultList.get().isEmpty()) {
            return 0;
        }
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
     *     <li>player's current level</li>
     *     <li>total points earned</li>
     *     <li>bonus/deducted points</li>
     *     <li>minimum points needed to go to next level</li>
     * </ul>
     *
     * @param result This is used to print the result in the terminal.
     */
    public void printResult(Result result) {
        Player player = result.getPlayer();
        int pointsForCorrect = result.getPointsForCorrect();
        int pointsForMostCorrectInOrder = result.getPointsForCorrectInOrder();
        int timeInMilli = result.getTimeTakenInMilliSec();
        int timeInSec = Math.round((float) timeInMilli / 1000);

        console.printLine("");
        console.tln("result.title");
        console.tln("result.show");
        console.printf("%4d p %20d p %20d s%n%n", pointsForCorrect, pointsForMostCorrectInOrder, timeInSec);

        int currentLevel = player.getLevel();

        if (result.getBonusPoints() != 0) {
            console.t("BONUS");
            console.printLine(result.getBonusPoints() + "P");
            player.setLevel(currentLevel + 1);
            currentLevel = player.getLevel();
        } else if (result.getDeductedPoints() != 0) {
            console.error("Deducted points: " + result.getDeductedPoints() + " p");
        }

        console.t("current.level");
        console.printLine("" + currentLevel);
        int totalPoints = getTotalPointsOfPlayer(player);
        console.t("total.points");
        console.printLine("" + totalPoints);

        int nextLevel = currentLevel + 1;
        int pointsNeededToGoToNextLevel = ResultUtil.getMinimumPointsForLevel(nextLevel) - totalPoints;
        console.t("points.to.next.level");
        console.printLine(nextLevel + ": " + pointsNeededToGoToNextLevel);
    }

    /**
     * This method informs the player before the BONUS game and even warns
     * if the player needs to perform well to avoid points deduction.
     *
     * @param player The player who is currently logged in the application.
     */
    public void printWarnings(Player player) {
        if (isEligibleForBonus(player)) {
            console.printDashes();
            console.error("IMPORTANT: Try to score 10 points in the next game for added BONUS !!");
        } else if (isEligibleForDeduction(player)) {
            console.printDashes();
            console.error("WARNING: Try to score at least 1 point in the next game to AVOID POINTS DEDUCTION !!");
        }
        console.printDashes();
    }

    /**
     * This method creates an evaluation of the user input depending on the type of game that was played.
     *
     * @param game      This is used to get the type of game that was played by the user.
     * @param userInput This is the user input.
     * @return An object of type <i>UserInputEvaluation</i> which is a record.
     */
    private UserInputEvaluation evaluateUserInput(Game game, String userInput) {
        UserInputEvaluation userInputEvaluation = null;

        switch (GameType.fromType(game.getType())) {
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

    /**
     * This method evaluates the user input for games which require a number input.
     *
     * @param gameContent The content of the game chosen by the user.
     * @param userInput   The input given by user.
     * @return An object of type UserInputEvaluation record to save the number of correct,
     * number of most correct in order and number of special characters respectively.
     */
    private UserInputEvaluation evaluateInputForCountGame(String gameContent, String userInput) {

        int numOfCorrect = 0;
        int numOfMostCorrectInOrder = 0;
        try {
            numOfCorrect = Integer.parseInt(userInput);
            numOfMostCorrectInOrder = Integer.parseInt(userInput);
        } catch (NumberFormatException ignored) {

        }
        int numOfSpecialChar = calculateNumOfQuestionMarks(gameContent);
        return new UserInputEvaluation(numOfCorrect, numOfMostCorrectInOrder, numOfSpecialChar);
    }

    public void startGame(Player player) {
        console.printDashes();
        GameType gameChoice = getGameType(GameType.values());
        GameDifficultyLevel difficultyLevel = getDificultyLevel(GameDifficultyLevel.values());
        Optional<Game> optionalGame = getGameByLevelAndType(difficultyLevel, gameChoice);

        if (optionalGame.isEmpty()) {
            console.error("Desired game NOT found!");
            return;
        }
        printWarnings(player);
        console.tln(getInstuctions(gameChoice));
        String content = optionalGame.get().getContent();
        console.tln("time starts");
        console.printLine(content);

        long startTime = System.currentTimeMillis();
        String userInput = ScannerHelper.getStringInput();
        long stopTime = System.currentTimeMillis();
        int timeTakenInMilliSec = Math.round(stopTime - startTime);

        calculateAndSaveResult(player, optionalGame.get(), userInput, timeTakenInMilliSec);
    }

    private GameType getGameType(GameType[] options) {
        console.print(options);
        return ScannerHelper.getGameType(options);
    }

    private GameDifficultyLevel getDificultyLevel(GameDifficultyLevel[] options) {
        console.print(options);
        return ScannerHelper.getDificultyLevel(options);
    }

    private String getInstuctions(GameType gameType) {
        String instruction = "";
        switch (gameType) {
            case WRITE_WORDS -> instruction = "instruction.write.words";
            case WRITE_SENTENCE -> instruction = "instruction.write.sentence";
            case COUNT_NUMBER -> instruction = "instruction.count.number";
            case CASE_SENSITIVE -> instruction = "instruction.case.sensitive";
            case SPECIAL_CHARACTERS -> instruction = "instruction.special.characters";
        }
        return instruction;
    }

}
