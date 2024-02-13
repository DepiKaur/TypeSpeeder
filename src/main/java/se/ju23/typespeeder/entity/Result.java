package se.ju23.typespeeder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-02-10
 */

@Entity
@Table(name="result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    private int points;

    @Column(name="time_taken_in_sec")
    private int timeTakenInSec;

    public Result() {
    }

    public Result(Player player, Game game, int points, int timeTakenInSec) {
        this.player = player;
        this.game = game;
        this.points = points;
        this.timeTakenInSec = timeTakenInSec;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTimeTakenInSec() {
        return timeTakenInSec;
    }

    public void setTimeTakenInSec(int timeTakenInSec) {
        this.timeTakenInSec = timeTakenInSec;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", player=" + player.getDisplayName() +
                ", game=" + game.getType() +
                ", points=" + points +
                ", timeTakenInSec=" + timeTakenInSec +
                '}';
    }
}
