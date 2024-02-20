import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
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
        return "NewsLetter{" +
                "content='" + content + '\'' +
                ", publishDateTime=" + publishDateTime +
                '}';
    }
}
