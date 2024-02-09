package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.ju23.typespeeder.entity.Player;

import java.util.Optional;

/**
 * @author Depinder Kaur
 * since 2024-02-08
 */
public interface PlayerRepo extends JpaRepository<Player, Integer> {

    Player findByUsername(String username);
}
