package se.ju23.typespeeder;

import java.time.LocalDateTime;

/**
 * @author Depinder Kaur
 * @version 0.1.0
 * <h2>Patch</h2>
 * <p>
 *     Patch class contains methods to fulfil the requirements of the third and last update.
 * </p>
 * @date 2024-02-19
 */
public class Patch {

    private String patchVersion;
    public LocalDateTime realeaseDateTime;

    public Patch() {
        this.realeaseDateTime = LocalDateTime.parse("2024-02-20T10:25:15");
    }

    public void setPatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }

    public void setRealeaseDateTime(LocalDateTime realeaseDateTime) {
        this.realeaseDateTime = realeaseDateTime;
    }

    public LocalDateTime getRealeaseDateTime() {
        return realeaseDateTime;
    }

    public String getPatchVersion(){
        return patchVersion;
    }
    @Override
    public String toString() {
        return patchVersion +" at: " + realeaseDateTime ;
    }
}
