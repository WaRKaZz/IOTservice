package controller;

import service.LoginService;
import service.LogoutService;
import service.Service;
import service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Servlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service = ServiceFactory.getService(request.getRequestURI());
        try {
            service.execute(request,response);
        } catch (SQLException e) {
            System.out.println("My main is SQLException");
            // LOGSQL
        } catch (IOException e){
            // LOGIO
            System.out.println("My main is IOException");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

