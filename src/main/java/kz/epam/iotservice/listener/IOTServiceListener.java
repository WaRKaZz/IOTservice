package kz.epam.iotservice.listener;

import kz.epam.iotservice.dao.LanguageDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.Language;
import kz.epam.iotservice.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.constants.Attributes.LANGUAGES_LIST_CONTEXT_STATEMENT;
import static kz.epam.iotservice.constants.Attributes.LANG_CONTEXT_STATEMENT;

public class IOTServiceListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final long DEFAULT_LANGUAGE_PARAMETER = (long) 1;
    private static final String DRIVER_IS_BROKEN = "JDBC Driver is broken!";
    private static final String CANNOT_REALIZE_LANGUAGES = "Cannot realize languages";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LanguageDAO languageDAO = new LanguageDAO();
        Language lang = new Language();
        List languages = new ArrayList();
        ServletContext context = servletContextEvent.getServletContext();
        Connection connection = ConnectionPool.getInstance().retrieve();
        try {
            lang = languageDAO.getLanguageByID(DEFAULT_LANGUAGE_PARAMETER, connection);
            languages = languageDAO.getListLanguages(connection);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_REALIZE_LANGUAGES, e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error(DRIVER_IS_BROKEN, e);
            }
        } finally {
            try {
                ConnectionPool.getInstance().putBack(connection);
            } catch (ConnectionException e) {
                LOGGER.error(DRIVER_IS_BROKEN, e);
            }
        }
        context.setAttribute(LANG_CONTEXT_STATEMENT, lang);
        context.setAttribute(LANGUAGES_LIST_CONTEXT_STATEMENT, languages);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {   //Destroy method don't necessary in this Listener

    }
}
