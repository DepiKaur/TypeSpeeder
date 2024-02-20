import java.time.LocalDateTime;

/**
 * @author Depinder Kaur
 * @version <h2></h2>
 * @date 2024-02-19
 */
public class Patch {

    private String patchVersion;
    public LocalDateTime realeaseDateTime;

    public Patch() {
        this.realeaseDateTime = LocalDateTime.parse("2024-02-20T10:25:15");
    }

    public LocalDateTime getRealeaseDateTime() {
        return realeaseDateTime;
    }

    @Override
    public String toString() {
        return "Patch{" +
                "patchVersion='" + patchVersion + '\'' +
                ", realeaseDateTime=" + realeaseDateTime +
                '}';
    }
}
