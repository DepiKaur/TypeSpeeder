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

    @Column(name="points_for_correct")
    private int pointsForCorrect;

    @Column(name="points_for_correct_in_order")
    private int pointsForCorrectInOrder;

    @Column(name="time_taken_in_ms")
    private int timeTakenInMilliSec;

    public Result() {
    }

    public Result(Player player, Game game, int pointsForCorrect, int pointsForCorrectInOrder, int timeTakenInMilliSec) {
        this.player = player;
        this.game = game;
        this.pointsForCorrect = pointsForCorrect;
        this.pointsForCorrectInOrder = pointsForCorrectInOrder;
        this.timeTakenInMilliSec = timeTakenInMilliSec;
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

    public int getPointsForCorrect() {
        return pointsForCorrect;
    }

    public void setPointsForCorrect(int pointsForCorrect) {
        this.pointsForCorrect = pointsForCorrect;
    }

    public int getPointsForCorrectInOrder() {
        return pointsForCorrectInOrder;
    }

    public void setPointsForCorrectInOrder(int pointsForCorrectInOrder) {
        this.pointsForCorrectInOrder = pointsForCorrectInOrder;
    }

    public int getTimeTakenInMilliSec() {
        return timeTakenInMilliSec;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", player=" + player.getDisplayName() +
                ", game=" + game.getType() + "(level:" + game.getDifficultyLevel() + ")" +
                ", pointsForCorrect=" + pointsForCorrect +
                ", pointsForCorrectInOrder=" + pointsForCorrectInOrder +
                ", timeTakenInMilliSec=" + timeTakenInMilliSec +
                '}';
    }
}
