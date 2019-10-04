package kz.epam.IOTService.util;

import kz.epam.IOTService.database.HomeDAO;
import kz.epam.IOTService.entity.Home;
import kz.epam.IOTService.entity.User;
import kz.epam.IOTService.exception.ConnectionException;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static kz.epam.IOTService.util.IOTServiceConstants.HOME_SESSION_STATEMENT;
import static kz.epam.IOTService.util.IOTServiceConstants.TRUE;

public class ServiceManagement {

    private static final String APPLY = "apply";
    private static final int LOG_ROUNDS = 12;

    public static void updateHomeInSession (HttpServletRequest request) throws SQLException, ConnectionException{
        Home home = (Home) request.getSession().getAttribute(HOME_SESSION_STATEMENT);
        HomeDAO homeDAO = new HomeDAO();
        home = homeDAO.getHomeByID(home.getHomeID());
        if (home.getHomeInstalledDevices().isEmpty()){
            home.setHomeInstalledDevices(null);
        }
        request.getSession().setAttribute(HOME_SESSION_STATEMENT, home);
    }

    public static boolean isApplyPressed(HttpServletRequest request){
        if (request.getParameter(APPLY) != null){
            return  request.getParameter(APPLY).equals(TRUE);
        } else {
            return false;
        }
    }

    public static String encryptPassword(String password){
        String salt = BCrypt.gensalt(LOG_ROUNDS);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean isUserCorrect (User user, String password){
        boolean isCorrect = false;
        if (user.getUserPassword() != null){
            String userPassword = user.getUserPassword();
            isCorrect = BCrypt.checkpw(password, userPassword);
        }
        return isCorrect;
    }
}
