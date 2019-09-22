package service;

import database.HomeDAO;
import entity.Home;
import exception.ConnectionException;
import exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static validation.HomeValidator.*;

public class AddNewHomeService implements Service {
    private String homeMessage;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
                                                    IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            createNewHome(request, response);
        } else {
            homeMessage = "Devices into home you can add later";
            newHomeForward(request, response);
        }
    }

    private boolean isApplyPressed(HttpServletRequest request){
        if (request.getParameter("apply") != null){
            return true;
        } else {
            return false;
        }
    }

    private void newHomeForward(HttpServletRequest request, HttpServletResponse response)
                                            throws  IOException, ServletException, SQLException, ConnectionException{
        request.getSession().setAttribute("homeMessage", homeMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/newHome.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createNewHome(HttpServletRequest request, HttpServletResponse response)
                                            throws  IOException, ServletException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        Home home = new Home();
        String homeName = "", homeAddress = "";
        try{
            homeName = validateHomeName(request.getParameter("homeName"));
            homeAddress = validateHomeAddress(request.getParameter("homeAddress"));
        } catch (ValidationException e){
            homeMessage = "Please enter valid home address!";
            request.getSession().setAttribute("homeMessage", homeMessage);
            newHomeForward(request, response);
        }
        home.setHomeName(homeName);
        home.setHomeAddress(homeAddress);
        homeDAO.addNewHome(home);
        homeMessage = "Home added successful";
        response.sendRedirect("/addNewHome");
    }
}
