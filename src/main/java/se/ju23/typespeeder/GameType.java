package se.ju23.typespeeder;

/**
 * @author Depinder Kaur
 * @date 2024-02-08
 * @version 1.0
 * <h2>GameType</h2>
 * GameType is an enum.
 * Its purpose is to store different types of typing games.
 */
public enum GameType {
    WRITE_WORDS("Write words- only small letters"),
    CASE_SENSITIVE("Write case-sensitive text"),
    SPECIAL_CHARACTERS("Write text with special characters eg. @, £, €"),
    COUNT_NUMBER("Count number of '?' in given text"),
    WRITE_SENTENCE("Write a complete sentence"),
    SHORTER_PARAGRAPHS("Write a short paragraph");

    private String type;

    GameType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static GameType fromType(String type){

        for(GameType gameType : values()){
            if(gameType.getType().equals(type)){
                return gameType;
            }
        }
        return null;
    }
}
