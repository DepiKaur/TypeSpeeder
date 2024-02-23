package se.ju23.typespeeder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.ju23.typespeeder.consle.Console;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.game.GameDifficultyLevel;
import se.ju23.typespeeder.game.GameType;
import se.ju23.typespeeder.repo.GameRepo;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.repo.ResultRepo;
import se.ju23.typespeeder.service.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
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
    @Mock
    private PlayerRepo playerRepo;
    @Mock
    private Console console;

    @BeforeEach
    public void setup() {
        gameService = new GameService(gameRepo, resultRepo, playerRepo, console);
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
        Player player = new Player("Username", "Password12", "DisplayName");
        Game game = new Game("Level", "Write case-sensitive text", "TestContent");
        String userInput = "TestContest";
        int timeInMilli = 15000;

        Result result = new Result(player, game, 8, 6, 15000, 0, 0);

        when(resultRepo.save(any(Result.class))).thenReturn(result);
        List<Result> resultList = new ArrayList<>();
        resultList.add(result);

        when(resultRepo.findByPlayerId(anyInt())).thenReturn(Optional.of(resultList));
        when(resultRepo.sumOfBonusPointsOfPlayer(anyInt())).thenReturn(6);
        when(resultRepo.sumOfPointsOfAPlayer(anyInt())).thenReturn(20);
        when(resultRepo.sumOfDeductedPointsOfPlayer(anyInt())).thenReturn(-2);

        when(playerRepo.save(any(Player.class))).thenReturn(player);

        gameService.calculateAndSaveResult(player, game, userInput, timeInMilli);
        verify(resultRepo, times(1)).save(any(Result.class));
        verify(resultRepo, atLeastOnce()).sumOfBonusPointsOfPlayer(anyInt());
        verify(playerRepo, atMostOnce()).save(any(Player.class));
        assertNotNull(player);
    }

    @Test
    public void testGetTotalPointsOfPlayer() {
        Player player = new Player("Username", "Password12", "DisplayName");
        player.setLevel(2);
        Game game = new Game("Level", "Write case-sensitive text", "TestContent");

        Result result = new Result(player, game, 8, 6, 25000);
        List<Result> resultList = new ArrayList<>();
        resultList.add(result);

        when(resultRepo.findByPlayerId(anyInt())).thenReturn(Optional.of(resultList));
        when(resultRepo.sumOfBonusPointsOfPlayer(anyInt())).thenReturn(6);
        when(resultRepo.sumOfPointsOfAPlayer(anyInt())).thenReturn(20);
        when(resultRepo.sumOfDeductedPointsOfPlayer(anyInt())).thenReturn(-2);

        int totalPointsOfPlayer = gameService.getTotalPointsOfPlayer(player);
        assertEquals(24, totalPointsOfPlayer);
        assertNotEquals(20, totalPointsOfPlayer);
        assertEquals(2, player.getLevel());
        verify(resultRepo, atMostOnce()).sumOfPointsOfAPlayer(anyInt());
    }
}
