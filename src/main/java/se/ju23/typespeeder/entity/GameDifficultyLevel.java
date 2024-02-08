package se.ju23.typespeeder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import se.ju23.typespeeder.DifficultyLevelImpl;

/**
 * @author Depinder Kaur
 * since 2024-02-08
 * version 1.0
 * <h2>GameDifficultyLevel</h2>
 * GameDifficultyLevel is a Java POJO with two fields: id (of type int) and
 * difficultyLevel (of type enum DifficultyLevelImpl).
 */

@Entity
@Table(name="game_difficulty_level")
public class GameDifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.ORDINAL)
    private DifficultyLevelImpl difficultyLevel;

    public GameDifficultyLevel() {
    }

    public int getId() {
        return id;
    }

    public DifficultyLevelImpl getDifficultyLevel() {
        return difficultyLevel;
    }

    @Override
    public String toString() {
        return "GameDifficultyLevel{" +
                "id=" + getId() +
                ", difficultyLevel=" + getDifficultyLevel() +
                '}';
    }
}
