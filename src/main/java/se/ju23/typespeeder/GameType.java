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
    WRITE_WORDS("Write words"),
    CASE_SENSITIVE("Case-sensitive"),
    SPECIAL_CHARACTERS("Special characters"),
    COUNT_NUMBER("Number of appearances"),
    WRITE_SENTENCE("Write complete sentences"),
    SHORTER_PARAGRAPHS("Shorter paragraphs");

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
