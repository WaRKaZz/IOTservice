package kz.epam.IOTservice.entity;

public class Language {
    private int languageID;
    private String languageName;
    private String languageLocal;

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageLocal() {
        return languageLocal;
    }

    public void setLanguageLocal(String languageLocal) {
        this.languageLocal = languageLocal;
    }
}
