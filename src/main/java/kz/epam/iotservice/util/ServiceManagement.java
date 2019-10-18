package kz.epam.iotservice.util;

import kz.epam.iotservice.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;

import static kz.epam.iotservice.constants.Other.TRUE;

public final class ServiceManagement {

    private static final String APPLY = "apply";
    private static final int LOG_ROUNDS = 12;

    private ServiceManagement() {
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
