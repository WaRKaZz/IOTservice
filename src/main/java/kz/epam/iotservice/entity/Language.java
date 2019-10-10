package kz.epam.iotservice.entity;

public class Language {
    private long languageID;
    private String languageName;
    private String languageLocale;

    public long getLanguageID() {
        return languageID;
    }

    public void setLanguageID(long languageID) {
        this.languageID = languageID;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageLocale() {
        return languageLocale;
    }

    public void setLanguageLocale(String languageLocale) {
        this.languageLocale = languageLocale;
    }
}
