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
import static kz.epam.IOTService.validation.LanguageValidation.*;

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
        boolean validationException = false;
        Long languageID = (long) 0;
        try {
             languageID = validateID(request.getParameter(LANGUAGE_ID_PARAMETER));
        } catch (ValidationException e){
            validationException = true;
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        if (!validationException){
            request.getServletContext().setAttribute(LANG_CONTEXT_STATEMENT, languageDAO.getLanguageByID(languageID));
            response.sendRedirect(LANGUAGE_URI);
        }
    }
}
