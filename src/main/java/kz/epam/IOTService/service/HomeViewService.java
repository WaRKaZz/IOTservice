package kz.epam.IOTService.service;

import kz.epam.IOTService.database.HomeDAO;
import kz.epam.IOTService.entity.Home;
import kz.epam.IOTService.exception.ConnectionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static kz.epam.IOTService.util.IOTServiceConstants.*;

public class HomeViewService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, SQLException, ConnectionException {
        HomeDAO homeDAO = new HomeDAO();
        if (request.getParameter(HOME_ID_PARAMETER) != null){
            long homeID = Long.parseLong(request.getParameter(HOME_ID_PARAMETER));
            Home home =  homeDAO.getHomeByID(homeID);
            request.getSession().setAttribute(HOME_SESSION_STATEMENT, home);
        }
        response.sendRedirect(MAIN_URI);
    }
}
