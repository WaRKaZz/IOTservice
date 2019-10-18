package kz.epam.iotservice.dao;

import kz.epam.iotservice.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Database.*;

public class LanguageDAO {
    private static final String GEL_LANGUAGE_LIST_SQL = "SELECT * FROM IOT_DATABASE.LANGUAGE";
    private static final String GEL_LANGUAGE_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.LANGUAGE " +
            "WHERE LANGUAGE_ID = ?";

    public List<Language> getListLanguages(Connection connection) throws SQLException {
        List<Language> languageList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GEL_LANGUAGE_LIST_SQL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Language language = configureLanguageObject(resultSet);
                    languageList.add(language);
                }
            }
        }
        return languageList;
    }

    public Language getLanguageByID(Long languageID, Connection connection) throws SQLException {
        Language language = new Language();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GEL_LANGUAGE_BY_ID_SQL)) {
            preparedStatement.setLong(1, languageID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    language = configureLanguageObject(resultSet);
                }
            }
        }
        return language;
    }

    private Language configureLanguageObject(ResultSet resultSet) throws SQLException {
        Language language = new Language();
        language.setLanguageID(resultSet.getLong(LANGUAGE_ID));
        language.setLanguageName(resultSet.getString(LANGUAGE_NAME));
        language.setLanguageLocale(resultSet.getString(LANGUAGE_LOCALE));
        return language;
    }
}
