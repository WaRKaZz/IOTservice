package kz.epam.IOTService.service;

import kz.epam.IOTService.database.LanguageDAO;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTService.util.IOTServiceConstants.*;
import static kz.epam.IOTService.util.ServiceManagement.isApplyPressed;

public class ChangeLanguageService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            changeLocale(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(CHANGE_LANGUAGE_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private void changeLocale(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException{
        LanguageDAO languageDAO = new LanguageDAO();
        Long languageID = Long.parseLong(request.getParameter(LANGUAGE_ID_PARAMETER));
        request.getServletContext().setAttribute(LANG_CONTEXT_STATEMENT, languageDAO.getLanguageByID(languageID));
        response.sendRedirect(LANGUAGE_URI);
    }
}
