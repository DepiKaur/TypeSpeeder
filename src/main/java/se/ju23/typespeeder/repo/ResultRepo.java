package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.ju23.typespeeder.entity.Result;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>ResultRepo</h2>
 * <p>
 *     ResultRepo is an interface to fetch result data from the database.
 *     It has only one method to find a player by its id.
 * </p>
 * @date 2024-02-10
 */
public interface ResultRepo extends JpaRepository<Result, Integer> {
    Optional<List<Result>> findByPlayerId(int id);

}
