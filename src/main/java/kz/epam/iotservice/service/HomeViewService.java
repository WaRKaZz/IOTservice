package kz.epam.iotservice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.epam.iotservice.constants.Attributes.CURRENT_USER_HOME_ID_PARAMETER;
import static kz.epam.iotservice.constants.Attributes.HOME_ID_PARAMETER;
import static kz.epam.iotservice.constants.Uri.MAIN_URI;

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
