package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.LanguageDAO;
import kz.epam.iotservice.exception.ConnectionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.iotservice.util.ConstantsForAttributes.LANGUAGE_ID_PARAMETER;
import static kz.epam.iotservice.util.ConstantsForAttributes.LANG_CONTEXT_STATEMENT;
import static kz.epam.iotservice.util.ConstantsUri.LANGUAGE_URI;
import static kz.epam.iotservice.util.JspConstants.CHANGE_LANGUAGE_JSP;
import static kz.epam.iotservice.util.ServiceManagement.isApplyPressed;

public class ChangeLanguageService implements Service {

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
        Long languageID = Long.parseLong(request.getParameter(LANGUAGE_ID_PARAMETER));
        request.getServletContext().setAttribute(LANG_CONTEXT_STATEMENT, languageDAO.getLanguageByID(languageID));
        response.sendRedirect(LANGUAGE_URI);
    }
}
