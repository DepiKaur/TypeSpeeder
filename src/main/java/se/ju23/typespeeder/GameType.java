package se.ju23.typespeeder;

/**
 * @author Depinder Kaur
 * @date 2024-02-08
 * @version 1.0
 * <h2>GameType</h2>
 * GameType is an enum which implements the interface TypeOrDifficultyLevel.
 * Its purpose is to store different types of typing games.
 */
public enum GameType implements TypeOrDifficultyLevel {
    HIGHLIGHTED_WORDS("Highlighted words or alphabets"),
    CASE_SENSITIVE("Case-sensitive"),
    SPECIAL_CHARACTERS("Special characters"),
    COUNT_NUMBER("Number of appearances of a specific word/character"),
    WRITE_SENTENCE("Write complete sentences"),
    SHORTER_PARAGRAPHS("Shorter paragraphs");

    private String type;

    GameType(String type) {
        this.type = type;
    }

    @Override
    public String getTypeOrDifficultyLevel() {
        return type;
    }
}
