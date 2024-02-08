package se.ju23.typespeeder;

/**
 * @author Depinder Kaur
 * since 2024-02-08
 * version 1.0
 * <h2>GameDifficultyLevel</h2>
 * GameDifficultyLevel is an enum which implements the interface <i>TypeOrDifficultyLevel</i>.
 * In terms of difficulty levels, a particular game can be- easy, medium or hard.
 */

public enum GameDifficultyLevel implements TypeOrDifficultyLevel {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private String difficultyLevel;

    GameDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String getTypeOrDifficultyLevel() {
        return difficultyLevel;
    }
}
