package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ju23.typespeeder.GameDifficultyLevel;
import se.ju23.typespeeder.GameType;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.repo.GameRepo;

import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-02-10
 */
@Service
public class GameService {

    private GameRepo gameRepo;

    @Autowired
    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
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

    public int calculateResultForMostCorrect(Player player, Game game, String userInput, int timeTaken){
        GuessEvaluation guessEvaluation = calculatePoints(game, userInput);
        return guessEvaluation.numOfCorrect();
    }

    public int calculateResultForMostCorrectInOrder(Player player, Game game, String userInput, int timeTaken) {
        GuessEvaluation guessEvaluation = calculatePoints(game, userInput);
        return guessEvaluation.numOfMostCorrectInOrder();
    }

    private GuessEvaluation calculatePoints(Game game, String userInput) {
        GuessEvaluation guessEvaluation = null;

        switch (GameType.fromType(game.getType())){
            case WRITE_WORDS, CASE_SENSITIVE -> guessEvaluation = calculatePointsForWordGame(game.getContent(), userInput);
            case COUNT_NUMBER -> guessEvaluation = calculatePointsForCountNumberGame(game.getContent(), Integer.parseInt(userInput));
        }

        return guessEvaluation;
    }

    public GuessEvaluation calculatePointsForWordGame(String gameContent, String userInput) {
        int correct = calculateNumOfCorrect(gameContent, userInput);
        int mostCorrectInOrder = calculateNumOfMostCorrectInOrder(gameContent, userInput);
        return new GuessEvaluation(correct, mostCorrectInOrder);
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

    public GuessEvaluation calculatePointsForCountNumberGame(String gameContent, int userInput) {
        int correct = 0;        // this changes to 1 if user counts the exact no. of "?" in the given string
        int actualNumOfQuestionMarks = 0;

        String[] parts = gameContent.split("\n");
        for (String s : parts) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '?') {                 // to calculate the no. of "?" in the given text
                    actualNumOfQuestionMarks++;
                }
            }
        }

        if (userInput == actualNumOfQuestionMarks) {
            correct = 1;                               // user gets 1 point for correct answer, otherwise it remains 0
        }
        return new GuessEvaluation(correct, 0);
    }


}
