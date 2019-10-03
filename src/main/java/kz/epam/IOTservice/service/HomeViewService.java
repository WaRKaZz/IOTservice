package kz.epam.IOTservice.service;

import kz.epam.IOTservice.database.HomeDAO;
import kz.epam.IOTservice.entity.Home;
import kz.epam.IOTservice.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class HomeViewService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        HomeDAO homeDAO = new HomeDAO();
        if (request.getParameter("homeID") != null){
            long homeID = Long.parseLong(request.getParameter("homeID"));
            Home home =  homeDAO.getHomeByID(homeID);
            request.getSession().setAttribute("home", home);
        }
        response.sendRedirect("/main");
    }
}
