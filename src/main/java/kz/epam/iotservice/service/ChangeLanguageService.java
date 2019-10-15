package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.LanguageDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static kz.epam.iotservice.util.ConstantsForAttributes.LANGUAGE_ID_PARAMETER;
import static kz.epam.iotservice.util.ConstantsForAttributes.LANG_CONTEXT_STATEMENT;
import static kz.epam.iotservice.util.ConstantsUri.LANGUAGE_URI;
import static kz.epam.iotservice.util.JspConstants.CHANGE_LANGUAGE_JSP;
import static kz.epam.iotservice.util.ServiceManagement.isApplyPressed;

public class ChangeLanguageService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String CANNOT_DOWNLOAD_LANGUAGE_LIST = "Cannot download language list";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)) {
            changeLocale(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(CHANGE_LANGUAGE_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private void changeLocale(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException {
        LanguageDAO languageDAO = new LanguageDAO();
        Connection connection = ConnectionPool.getInstance().retrieve();
        Long languageID = Long.parseLong(request.getParameter(LANGUAGE_ID_PARAMETER));
        try {
            request.getServletContext().setAttribute(LANG_CONTEXT_STATEMENT, languageDAO.getLanguageByID(languageID, connection));
            connection.commit();
        } catch (SQLException e){
            LOGGER.error(CANNOT_DOWNLOAD_LANGUAGE_LIST, e);
            connection.rollback();
        } finally {
            ConnectionPool.getInstance().putBack(connection);
        }
        response.sendRedirect(LANGUAGE_URI);
    }
}
