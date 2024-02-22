package se.ju23.typespeeder;

import java.time.LocalDateTime;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>NewsLetter</h2>
 * <p>
 *     NewsLetter class contains methods to fulfil the requirements of the third and last update.
 * </p>
 * @date 2024-02-19
 */
public class NewsLetter {
    private String content;
    public LocalDateTime publishDateTime;

    public NewsLetter() {
        this.content = """
                Today's riddle-
                What four-letter word can be written forward, backward or upside down, 
                and can still be read from left to right?
                Answer: NOON. 
                """;
        this.publishDateTime = LocalDateTime.parse("2024-02-22T10:15:15");
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    @Override
    public String toString() {
        return content + "\nPublished: " + publishDateTime.getDayOfMonth() + " " +
                publishDateTime.getMonth().toString().substring(0,1) +
                publishDateTime.getMonth().toString().substring(1).toLowerCase() +
                " " + publishDateTime.getYear();
    }
}
