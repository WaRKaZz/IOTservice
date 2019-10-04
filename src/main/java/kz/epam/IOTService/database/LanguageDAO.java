package kz.epam.IOTService.database;

import kz.epam.IOTService.entity.Language;
import kz.epam.IOTService.exception.ConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final String GEL_LANGUAGE_LIST_SQL = "SELECT * FROM IOT_DATABASE.LANGUAGE";
    private static final String GEL_LANGUAGE_BY_ID_SQL = "SELECT * FROM IOT_DATABASE.LANGUAGE " +
            "WHERE LANGUAGE_ID = ?";


    public List<Language> getListLanguages() throws SQLException, ConnectionException {
        List<Language> languageList = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GEL_LANGUAGE_LIST_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Language language = configureLanguageObject(resultSet);
                languageList.add(language);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return languageList;
    }

    public Language getLanguageByID(Long languageID) throws SQLException, ConnectionException {
        Language language = new Language();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GEL_LANGUAGE_BY_ID_SQL)){
            preparedStatement.setLong(1, languageID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                language = configureLanguageObject(resultSet);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return language;
    }

    private Language configureLanguageObject(ResultSet resultSet) throws SQLException{
        Language language = new Language();
        language.setLanguageID(resultSet.getLong("LANGUAGE_ID"));
        language.setLanguageName(resultSet.getString("LANGUAGE_NAME"));
        language.setLanguageLocale(resultSet.getString("LANGUAGE_LOCALE"));
        return language;
    }
}
