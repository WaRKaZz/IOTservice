package kz.epam.iotservice.service;

import kz.epam.iotservice.dao.DeviceDAO;
import kz.epam.iotservice.dao.FunctionDAO;
import kz.epam.iotservice.dao.HomeDAO;
import kz.epam.iotservice.database.ConnectionPool;
import kz.epam.iotservice.entity.Device;
import kz.epam.iotservice.entity.Home;
import kz.epam.iotservice.entity.User;
import kz.epam.iotservice.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.iotservice.util.ConstantsForAttributes.*;
import static kz.epam.iotservice.util.ConstantsForAttributes.HOME_USER_LIST_SESSION_STATEMENT;
import static kz.epam.iotservice.util.ConstantsUri.MAIN_URI;

public class HomeViewService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        if (request.getParameter(HOME_ID_PARAMETER) != null) {
            long homeID = Long.parseLong(request.getParameter(HOME_ID_PARAMETER));
            request.getSession().setAttribute(CURRENT_USER_HOME_ID_PARAMETER, homeID);
        }
        response.sendRedirect(MAIN_URI);
    }


}
