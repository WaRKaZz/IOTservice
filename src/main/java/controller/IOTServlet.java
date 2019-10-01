package controller;

import exception.ConnectionException;
import service.Service;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class IOTServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Service service = ServiceFactory.getService(request.getRequestURI());
        try {
            service.execute(request,response);
        } catch (SQLException e) {
            System.out.println("My main is SQLException");
            // TODO Log sql
            // LOGSQLю
        } catch (IOException e){
            // LOGIO
            System.out.println("My main is IOException");
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }
}

