package kz.epam.iotservice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.epam.iotservice.constants.Uri.EMPTY_URI;

public class LogoutService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(EMPTY_URI);
    }
}
