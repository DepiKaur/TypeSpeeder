package se.ju23.typespeeder;

import se.ju23.typespeeder.DifficultyLevel;

/**
 * @author Depinder Kaur
 * since 2024-02-08
 * version 1.0
 * <h2>DifficultyLevelImpl</h2>
 * DifficultyLevelImpl is an enum which implements the interface <i>DifficultyLevel</i>.
 * In terms of difficulty levels, a particular game can be- easy, medium or hard.
 */

public enum DifficultyLevelImpl implements DifficultyLevel {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private String difficultyLevel;

    DifficultyLevelImpl(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
}
