package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.entity.Player;

import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>PlayerRepo</h2>
 * <p>
 *     PlayerRepo is an interface to fetch player data from the database.
 *     It has methods to find a player by:
 *     <ul>
 *         <li>Username</li>
 *         <li>Username and password</li>
 *     </ul>
 * </p>
 * @date 2024-02-08
 */
@Repository
public interface PlayerRepo extends JpaRepository<Player, Integer> {

    Optional<Player> findByUsername(String username);

    Optional<Player> findByUsernameAndPassword(String username, String password);
}
