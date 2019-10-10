package kz.epam.iotservice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageNotFoundService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
