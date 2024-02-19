package se.ju23.typespeeder.consle;

import se.ju23.typespeeder.util.FileUtil;

import java.util.HashMap;

/**
 * @author Sofie Van Dingenen
 * @version 1.0.0
 * Since 2024-02-12
 *
 * <h2>Language</h2>
 * Language class is a java POJO class with the following fields: languageMap and name.
 * <ul>
 *     <li>languageMap: type Hashmap with String as key and value</li>
 *     <li>name: type String</li>
 * </ul>
 * along with a constructor as well as getter and setters.
 */

public class Language {
    private HashMap<String, String> languageMap;
    private String name;

    /**
     * Initilizes a new object with the given language and creates it's languageMap.
     *
     * @param name the abbreviation of the language.
     */
    public Language(String name) {
        languageMap = FileUtil.readLanguageFile(name);
        this.name = name;
    }

    public Language() {
        languageMap = FileUtil.readLanguageFile("eng");
        this.name = "eng";
    }

    public HashMap<String, String> getLanguageMap() {
        return languageMap;
    }

    public void setLanguageMap(HashMap<String, String> languageMap) {
        this.languageMap = languageMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
