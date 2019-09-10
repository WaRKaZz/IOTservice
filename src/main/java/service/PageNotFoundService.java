package service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PageNotFoundService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
