package kz.epam.iotservice.service;

import kz.epam.iotservice.util.OtherConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.epam.iotservice.util.JspConstants.MAIN_PAGE_JSP;


public class MainPageService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_PAGE_JSP);
        requestDispatcher.forward(request, response);
    }
}
