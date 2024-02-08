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
 * <h2>GameContent</h2>
 * GameContent is a Java POJO class with following fields:
 * <ul>
 *     <li>id: type integer</li>
 *     <li>difficultyLevel: type String</li>
 *     <li>type: type String</li>
 *     <li>content: type String</li>
 * </ul>
 * along with the usual constructors as well as getters and setters.
 */

@Entity
@Table(name="game_content")
public class GameContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="difficulty_level")
    private String difficultyLevel;


    @Column(name="gameType")
    private String type;

    @Column(name="content")
    private String content;

    public GameContent() {
    }

    public GameContent(String difficultyLevel, String type, String content) {
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

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "GameContent{" +
                "id=" + getId() +
                ", difficultyLevel=" + getDifficultyLevel() +
                ", type=" + getType() +
                ", content='" + getContent() + '\'' +
                '}';
    }
}
