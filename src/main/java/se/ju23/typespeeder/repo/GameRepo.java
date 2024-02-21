package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.ju23.typespeeder.entity.Game;

import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>GameRepo</h2>
 * <p>
 *     GameRepo is an interface to fetch game data from the database.
 *     It contains a method to find a game using its difficulty level and type.
 * </p>
 * @date 2024-02-08
 */
public interface GameRepo extends JpaRepository<Game, Integer> {

    Optional<Game> findByDifficultyLevelAndType(String difficultyLevel, String type);
}
