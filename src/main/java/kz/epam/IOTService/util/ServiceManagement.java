package kz.epam.IOTService.util;

import kz.epam.IOTService.database.HomeDAO;
import kz.epam.IOTService.entity.Home;
import kz.epam.IOTService.entity.User;
import kz.epam.IOTService.exception.ConnectionException;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class ServiceManagement {
    public static void updateHomeInSession (HttpServletRequest request) throws SQLException, ConnectionException{
        Home home = (Home) request.getSession().getAttribute("home");
        HomeDAO homeDAO = new HomeDAO();
        home = homeDAO.getHomeByID(home.getHomeID());
        if (home.getHomeInstalledDevices().isEmpty()){
            home.setHomeInstalledDevices(null);
        }
        request.getSession().setAttribute("home", home);
    }

    public static boolean isApplyPressed(HttpServletRequest request){
        if (request.getParameter("apply") != null){
            return  request.getParameter("apply").equals("true");
        } else {
            return false;
        }
    }

    public static String encryptPassword(String password){
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean isUserCorrect (User user, String password){
        boolean isCorrect = false;
        if (user.getUserPassword().equals(password)){
            String userPassword = user.getUserPassword();
            isCorrect = BCrypt.checkpw(password, userPassword);
        }
        return isCorrect;
    }
}
