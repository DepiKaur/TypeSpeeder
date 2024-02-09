package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.ju23.typespeeder.entity.Game;

/**
 * @author Depinder Kaur
 * since 2024-02-08
 */
public interface GameRepo extends JpaRepository<Game, Integer> {

    Game findByDifficultyLevelAndType(String difficultyLevel, String type);
}
