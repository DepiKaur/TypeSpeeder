package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.ju23.typespeeder.entity.Result;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-02-10
 */
public interface ResultRepo extends JpaRepository<Result, Integer> {
    Optional<List<Result>> findByPlayerId(int id);

}
