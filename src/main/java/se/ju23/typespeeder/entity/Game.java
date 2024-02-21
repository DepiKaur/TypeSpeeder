package se.ju23.typespeeder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Depinder Kaur
 * @date 2024-02-08
 * @version 1.0
 * <h2>Game</h2>
 * Game is a <i>Java POJO class</i> with following fields:
 * <ul>
 *     <li>id: type integer</li>
 *     <li>difficultyLevel: type String</li>
 *     <li>type: type String</li>
 *     <li>content: type String</li>
 * </ul>
 * along with the usual constructors as well as getters.
 */

@Entity
@Table(name="game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="difficulty_level")
    private String difficultyLevel;


    @Column(name="type")
    private String type;

    @Column(name="content")
    private String content;

    public Game() {
    }

    public Game(String difficultyLevel, String type, String content) {
        this.difficultyLevel = difficultyLevel;
        this.type = type;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + getId() +
                ", difficultyLevel=" + getDifficultyLevel() +
                ", type=" + getType() +
                ", content='" + getContent() + '\'' +
                '}';
    }
}
