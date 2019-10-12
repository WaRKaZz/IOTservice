package kz.epam.iotservice.filter;

import kz.epam.iotservice.dao.UserDAO;
import kz.epam.iotservice.entity.User;
import kz.epam.iotservice.enumeration.Role;
import kz.epam.iotservice.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static kz.epam.iotservice.util.ConstantsForAttributes.GUEST;
import static kz.epam.iotservice.util.ConstantsForAttributes.USER_SESSION_STATEMENT;
import static kz.epam.iotservice.util.ConstantsUri.*;

public class AccessFilter implements Filter {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final String FILTER_EXCEPTION_BY_USING_A_DAO_SERVICES = "Filter Exception by using a dao services";
    private final Map<String, Integer> AUTH_MAP = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        AUTH_MAP.put(LOGIN_URI, Role.GUEST.getId());
        AUTH_MAP.put(LOGOUT_URI, Role.GUEST.getId());
        AUTH_MAP.put(INDEX_URI, Role.GUEST.getId());
        AUTH_MAP.put(REGISTRATION_URI, Role.GUEST.getId());
        AUTH_MAP.put(SUBMIT_REGISTRATION_URI, Role.GUEST.getId());
        AUTH_MAP.put(MAIN_URI, Role.USER.getId());
        AUTH_MAP.put(CHOOSE_HOME_URI, Role.USER.getId());
        AUTH_MAP.put(ADD_NEW_HOME_URI, Role.USER.getId());
        AUTH_MAP.put(ADD_NEW_DEVICE_URI, Role.USER.getId());
        AUTH_MAP.put(SETTINGS_URI, Role.USER.getId());
        AUTH_MAP.put(UPDATE_DEVICE_URI, Role.USER.getId());
        AUTH_MAP.put(UPDATE_HOME_URI, Role.USER.getId());
        AUTH_MAP.put(DEVICES_URI, Role.USER.getId());
        AUTH_MAP.put(CHANGE_DEVICE_URI, Role.USER.getId());
        AUTH_MAP.put(ADMINISTRATION_URI, Role.ADMIN.getId());
        AUTH_MAP.put(PROFILE_URI, Role.USER.getId());
        AUTH_MAP.put(LANGUAGE_URI, Role.GUEST.getId());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = (User) request.getSession().getAttribute(USER_SESSION_STATEMENT);
        UserDAO userDAO = new UserDAO();
        try {
            if (user == null) {
                user = userDAO.getUserByLogin(GUEST);
            }
        } catch (SQLException | ConnectionException e) {
            LOGGER.error(e);
            LOGGER.error(FILTER_EXCEPTION_BY_USING_A_DAO_SERVICES);
        }
        String requestURI = request.getRequestURI();
        Integer accessLevel = AUTH_MAP.get(requestURI);
        if (accessLevel != null) {
            if (accessLevel >= Objects.requireNonNull(user).getUserRole()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {  //Destroy method don't necessary in this Filter

    }
}
