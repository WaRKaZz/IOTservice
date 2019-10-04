package kz.epam.IOTService.service;

import kz.epam.IOTService.util.IOTServiceConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainPageService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(IOTServiceConstants.MAIN_PAGE_JSP);
        requestDispatcher.forward(request, response);
    }
}
