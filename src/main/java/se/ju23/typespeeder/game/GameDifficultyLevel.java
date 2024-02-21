package se.ju23.typespeeder.game;

/**
 * @author Depinder Kaur
 * since 2024-02-08
 * version 0.1.0
 * <h2>GameDifficultyLevel</h2>
 * GameDifficultyLevel is an enum.
 * In terms of difficulty levels, a particular game can be- easy, medium or hard.
 */
public enum GameDifficultyLevel {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private String difficultyLevel;

    GameDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
}
