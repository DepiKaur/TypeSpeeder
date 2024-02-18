package se.ju23.typespeeder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.ju23.typespeeder.entity.Result;

import java.util.List;
import java.util.Optional;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>ResultRepo</h2>
 * <p>
 *     ResultRepo is an interface to fetch result data of players from the database.
 * </p>
 * @date 2024-02-10
 */
public interface ResultRepo extends JpaRepository<Result, Integer> {
    Optional<List<Result>> findByPlayerId(int id);

    @Query(nativeQuery = true,
            value = "select sum(points_for_correct) from result where player_id=:playerId")
    int sumOfPointsOfAPlayer(int playerId);

    @Query(nativeQuery = true,
            value = "select sum(bonus_points) from result where player_id=:playerId")
    int sumOfBonusPointsOfAPlayer(int playerId);

    @Query(nativeQuery = true,
            value = "select * from result " +
                    "where points_for_correct = 10 and player_id=:playerId " +
                    "order by id desc limit 2")
    List<Result> listOfTwoMostRecentResultsOfPlayer(int playerId);
}
