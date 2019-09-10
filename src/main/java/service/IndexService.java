package service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class IndexService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index_page.jsp");
        requestDispatcher.forward(request, response);
    }
}
