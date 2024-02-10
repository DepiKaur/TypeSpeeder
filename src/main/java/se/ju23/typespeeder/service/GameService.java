package se.ju23.typespeeder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ju23.typespeeder.GameDifficultyLevel;
import se.ju23.typespeeder.GameType;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.repo.GameRepo;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-02-08
 */
@Service
public class GameService {

    private GameRepo gameRepo;

    @Autowired
    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

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

    public Game getGameByLevelAndType(GameDifficultyLevel level, GameType type) {
        return gameRepo.findByDifficultyLevelAndType(level.getDifficultyLevel(), type.getType());
    }

    //TODO calculate points by game, user-input


    public void calculateResult(Player player, Game game, String userInput, int timeTaken){




    }

    private int calculatePoints(Game game, String userInput) {

        switch (GameType.fromType(game.getType())){
            case HIGHLIGHTED_WORDS -> calculatePointsForHighlightedGame(game.getContent(),userInput);
        }



       return 0;
    }

    private int calculatePointsForHighlightedGame(String gameContent, String userInput){
        return 100;
    }

}
