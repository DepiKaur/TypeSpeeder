package se.ju23.typespeeder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.ju23.typespeeder.entity.Game;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.entity.Result;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.repo.ResultRepo;
import se.ju23.typespeeder.util.RankUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * @date 2024-02-21
 */
@ExtendWith(MockitoExtension.class)
public class RankUtilTest {

    private RankUtil rankUtil;
    @Mock
    private PlayerRepo playerRepo;
    @Mock
    private ResultRepo resultRepo;
    private static final int MAX_EXECUTION_TIME = 200;
    private static final int MILLISECONDS_CONVERSION = 1_000_000;
    List<Player> allPlayers = new ArrayList<>();
    List<Result> allResults = new ArrayList<>();
    List<Result> resultListP1 = new ArrayList<>();
    List<Result> resultListP2 = new ArrayList<>();
    List<Result> resultListP3 = new ArrayList<>();
    List<Result> resultListP4 = new ArrayList<>();
    List<Result> resultListP5 = new ArrayList<>();
    List<Result> resultListP6 = new ArrayList<>();
    List<Result> resultListP7 = new ArrayList<>();
    List<Result> resultListP8 = new ArrayList<>();

    @BeforeEach
    public void setup() {
        rankUtil = new RankUtil(playerRepo, resultRepo);
        Player p1 = new Player(1, "Anne", "Anne12", "Anne");
        Player p2 = new Player(2, "Barney", "Barney12", "Barney");
        Player p3 = new Player(3, "Charlie", "Charlie12", "Charlie");
        Player p4 = new Player(4, "David", "David12", "David");
        Player p5 = new Player(5, "Dave", "Dave12", "David");
        Player p6 = new Player(6, "Bob", "Bob1234", "Bob");
        Player p7 = new Player(7, "Christina", "Christina12", "Christina");
        Player p8 = new Player(8, "Ross", "Ross12", "Ross");
        Player p9 = new Player(9, "Anna", "Anna12", "Anna");
        Player p10 = new Player(10,"Marie", "Marie12", "Marie");

        Game g1 = new Game("Easy", "Write case-sensitive text", "TestContent for First game.");
        Game g2 = new Game("Medium", "Write case-sensitive text", "TestContent for Second game.");
        Game g3 = new Game("Hard", "Write case-sensitive text", "TestContent for Third game.");

        Result r1 = new Result(p1, g1, 8, 6, 25000);
        Result r2 = new Result(p2, g3, 5, 3, 10000);
        Result r3 = new Result(p3, g1, 2, 2, 15000);
        Result r4 = new Result(p4, g2, 7, 5, 35000);
        Result r5 = new Result(p3, g2, 1, 0, 20000);
        Result r6 = new Result(p3, g3, 0, 0, 25000);
        Result r7 = new Result(p1, g2, 4, 3, 40000);
        Result r8 = new Result(p2, g2, 8, 8, 16000);
        Result r9 = new Result(p4, g1, 7, 7, 14000);
        Result r10 = new Result(p5, g3, 5, 3, 29000);
        Result r11 = new Result(p8, g1, 6, 6, 17000);
        Result r12 = new Result(p7, g2, 7, 6, 18000);
        Result r13 = new Result(p8, g1, 10, 9, 26000);
        Result r14 = new Result(p3, g3, 6, 5, 15000);
        Result r15 = new Result(p6, g2, 3, 2, 23000);

        Collections.addAll(allPlayers, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        Collections.addAll(allResults, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);
        Collections.addAll(resultListP1, r1, r7);
        Collections.addAll(resultListP2, r2, r8);
        Collections.addAll(resultListP3, r3, r5, r6, r14);
        Collections.addAll(resultListP4, r4, r9);
        Collections.addAll(resultListP5, r10);
        Collections.addAll(resultListP6, r15);
        Collections.addAll(resultListP7, r12);
        Collections.addAll(resultListP8, r11, r13);
    }

    @Test
    public void testCalculateRank() {
        long startTime = System.nanoTime();
        when(playerRepo.findAll()).thenReturn(allPlayers);
        when(resultRepo.findByPlayerId(anyInt())).thenAnswer(invocationOnMock -> {
            int argument = invocationOnMock.getArgument(0);
            Optional<List<Result>> resultList = switch (argument) {
                case 1 -> Optional.of(resultListP1);
                case 2 -> Optional.of(resultListP2);
                case 3 -> Optional.of(resultListP3);
                case 4 -> Optional.of(resultListP4);
                case 5 -> Optional.of(resultListP5);
                case 6 -> Optional.of(resultListP6);
                case 7 -> Optional.of(resultListP7);
                case 8 -> Optional.of(resultListP8);
                default -> Optional.of(Collections.emptyList());
            };
            return resultList;
        });

        ArrayList<String> rankList = rankUtil.calculateRank();
        rankList.forEach(result -> System.out.println(result));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;
        System.out.println("Time taken to execute this test: " + duration + "ms");
        assertTrue(duration <= MAX_EXECUTION_TIME, "Starting a challenge took too long. Execution time: " + duration + " ms.");
    }
}
