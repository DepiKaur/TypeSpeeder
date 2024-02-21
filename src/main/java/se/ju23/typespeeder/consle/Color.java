package se.ju23.typespeeder.consle;

public enum Color {

    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    WHITE("\u001B[0m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
