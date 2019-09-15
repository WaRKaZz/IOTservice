package service;

import database.HomeDAO;
import entity.Home;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class HomeViewService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
                                                                    IOException, ServletException, SQLException {
        HomeDAO homeDAO = new HomeDAO();
        if (request.getParameter("homeID") != null){
            long homeID = Long.parseLong(request.getParameter("homeID"));
            Home home =  homeDAO.getHomeByID(homeID);
            request.getSession().setAttribute("home", home);
        }
        response.sendRedirect("/main");
    }
}
