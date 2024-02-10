package se.ju23.typespeeder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.ju23.typespeeder.Consle.Console;
import se.ju23.typespeeder.entity.Player;
import se.ju23.typespeeder.repo.PlayerRepo;
import se.ju23.typespeeder.service.PlayerService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>PlayerServiceTest</h2>
 * @date 2024-02-09
 */
@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    private PlayerService playerService;

    @Mock
    private Console console;

    @Mock
    private PlayerRepo repo;

    @BeforeEach
    public void setup() {
        playerService = new PlayerService(repo, console);
    }

    @Test
    public void testIfUsernameAlreadyExists() {
        String username1 = "Alfred";
        String username2 = "Bob";

        Player p1 = new Player("Alfred", "password", "freddy");

        when(repo.findByUsername(username1)).thenReturn(Optional.of(p1));
        assertTrue(playerService.ifUsernameAlreadyExists(username1));

        when(repo.findByUsername(username2)).thenReturn(Optional.empty());
        assertFalse(playerService.ifUsernameAlreadyExists(username2));
    }

}
