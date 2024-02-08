package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.ju23.typespeeder.entity.GameContent;

/**
 * @author Depinder Kaur
 * since 2024-02-08
 */
public interface GameContentRepo extends JpaRepository<GameContent, Integer> {
}
