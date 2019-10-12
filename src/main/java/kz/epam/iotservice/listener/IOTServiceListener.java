package kz.epam.iotservice.listener;

import kz.epam.iotservice.dao.LanguageDAO;
import kz.epam.iotservice.entity.Language;
import kz.epam.iotservice.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.ConstantsForAttributes.LANGUAGES_LIST_CONTEXT_STATEMENT;
import static kz.epam.iotservice.util.ConstantsForAttributes.LANG_CONTEXT_STATEMENT;

public class IOTServiceListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final long DEFAULT_LANGUAGE_PARAMETER = (long) 1;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LanguageDAO languageDAO = new LanguageDAO();
        Language lang = new Language();
        List languages = new ArrayList();
        ServletContext context = servletContextEvent.getServletContext();
        try {
            lang = languageDAO.getLanguageByID(DEFAULT_LANGUAGE_PARAMETER);
            languages = languageDAO.getListLanguages();
        } catch (SQLException | ConnectionException e) {
            LOGGER.error(e);
        }
        context.setAttribute(LANG_CONTEXT_STATEMENT, lang);
        context.setAttribute(LANGUAGES_LIST_CONTEXT_STATEMENT, languages);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {   //Destroy method don't necessary in this Listener

    }
}
