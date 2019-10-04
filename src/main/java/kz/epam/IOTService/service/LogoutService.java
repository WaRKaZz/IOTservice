package kz.epam.IOTService.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.epam.IOTService.util.IOTServiceConstants.EMPTY_URI;

public class LogoutService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        request.getSession().invalidate();
        response.sendRedirect(EMPTY_URI);
    }
}
