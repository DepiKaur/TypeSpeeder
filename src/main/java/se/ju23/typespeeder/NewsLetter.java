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
                As each day passed I would learn, in our talk, something about the little prince's planet, 
                his departure from it, his journey.""";
        this.publishDateTime = LocalDateTime.parse("2024-02-20T10:25:15");
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    @Override
    public String toString() {
        return content + publishDateTime ;
    }
}
