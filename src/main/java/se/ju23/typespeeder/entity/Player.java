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
 * <h2>Player</h2>
 * Player class is a Java POJO class with the following fields: id, username, password and displayName
 * <ul>
 *     <li>id: type integer</li>
 *     <li>username: type String</li>
 *     <li>password: type String</li>
 *     <li>displayName: type String</li>
 * </ul>
 * along with constructors as well as getters and setters.
 */
@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @Column(name="display_name")
    private String displayName;

    public Player() {
    }

    public Player(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", displayName='" + getDisplayName() + '\'' +
                '}';
    }
}
