package se.ju23.typespeeder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.repo.GameRepo;
import se.ju23.typespeeder.repo.ResultRepo;
import se.ju23.typespeeder.service.GameService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    private GameService gameService;
    @Mock
    private GameRepo gameRepo;
    @Mock
    private ResultRepo resultRepo;

    @BeforeEach
    public void setup() {
        gameService = new GameService(gameRepo, resultRepo);
    }

    @Test
    public void testGetGameByDifficultyLevelAndType() {
        String level = "Easy";
        String type = "Write words- only small letters";
        String content = "asd asd ghf lkj lkj fkl dkg fal dhg has sak";
        Game game = new Game(level, type, content);

        when(gameRepo.findByDifficultyLevelAndType(level, type)).thenReturn(Optional.of(game));

        gameService.getGameByLevelAndType(GameDifficultyLevel.EASY, GameType.WRITE_WORDS);

        assertEquals(game, Optional.of(game).get());
        verify(gameRepo, atMostOnce()).findByDifficultyLevelAndType(level, type);
        verify(gameRepo, times(1)).findByDifficultyLevelAndType(level, type);
    }

    @Test
    public void testCalculateResultAndSave() {
        Player player = new Player("TestUsername", "TestPassword", "TestDisplayName");
        Game game = new Game("TestLevel", "Write words- only small letters", "TestContent");
        String userInput = "TestContest";
        int timeInMilli = 15000;

        Result result = new Result(player, game, 8, 6, 15000);

        when(resultRepo.save(any(Result.class))).thenReturn(result);

        Result savedResult = gameService.calculateResultAndSave(player, game, userInput, timeInMilli);
        assertEquals(result.getTimeTakenInMilliSec(), savedResult.getTimeTakenInMilliSec());
        assertEquals(result.getPlayer().getUsername(), savedResult.getPlayer().getUsername());
        verify(resultRepo, times(1)).save(any(Result.class));
        assertNotNull(player);
    }
}
