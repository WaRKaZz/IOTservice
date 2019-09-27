package util;

import database.HomeDAO;
import entity.Home;
import exception.ConnectionException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class  HomeManagement {
    public static void updateHome (HttpServletRequest request) throws SQLException, ConnectionException{
        Home home = (Home) request.getSession().getAttribute("home");
        HomeDAO homeDAO = new HomeDAO();
        home = homeDAO.getHomeByID(home.getHomeID());
        if (home.getHomeInstalledDevices().isEmpty()){
            home.setHomeInstalledDevices(null);
        }
        request.getSession().setAttribute("home", home);
    }
}
