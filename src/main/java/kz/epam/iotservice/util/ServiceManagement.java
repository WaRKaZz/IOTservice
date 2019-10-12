package kz.epam.iotservice.util;

import kz.epam.iotservice.dao.DeviceDAO;
import kz.epam.iotservice.dao.FunctionDAO;
import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.entity.User;
import kz.epam.iotservice.exception.ConnectionException;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.ConstantsForAttributes.HOME_SESSION_STATEMENT;
import static kz.epam.iotservice.util.OtherConstants.TRUE;

public final class ServiceManagement {

    private static final String APPLY = "apply";
    private static final int LOG_ROUNDS = 12;

    private ServiceManagement() {
    }

    public static void updateHomeInSession(HttpServletRequest request) throws SQLException, ConnectionException {
        Home home = (Home) request.getSession().getAttribute(HOME_SESSION_STATEMENT);
        HomeDAO homeDAO = new HomeDAO();
        FunctionDAO functionDAO = new FunctionDAO();
        DeviceDAO deviceDAO = new DeviceDAO();
        home = homeDAO.getHomeByID(home.getHomeID());
        home.setHomeInstalledDevices(deviceDAO.getDevicesList(home));
        List<Device> homeInstalledDevices = new ArrayList<>();
        for (Device device: home.getHomeInstalledDevices()) {
            device.setFunctions(functionDAO.getFunctionsList(device));
            homeInstalledDevices.add(device);
        }
        home.setHomeInstalledDevices(homeInstalledDevices);
        if (home.getHomeInstalledDevices().isEmpty()) {
            home.setHomeInstalledDevices(null);
        }
        request.getSession().setAttribute(HOME_SESSION_STATEMENT, home);
    }

    public static boolean isApplyPressed(HttpServletRequest request) {
        if (request.getParameter(APPLY) != null) {
            return request.getParameter(APPLY).equals(TRUE);
        } else {
            return false;
        }
    }

    public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt(LOG_ROUNDS);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean isUserCorrect(User user, String password) {
        boolean isCorrect = false;
        if (user.getUserPassword() != null) {
            String userPassword = user.getUserPassword();
            isCorrect = BCrypt.checkpw(password, userPassword);
        }
        return isCorrect;
    }
}
