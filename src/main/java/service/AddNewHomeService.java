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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
                                                    IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            createNewHome(request, response);
        } else {
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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/newHome.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createNewHome(HttpServletRequest request, HttpServletResponse response)
                                            throws  IOException, ServletException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        Home home = new Home();
        String homeName = "", homeAddress = "", homeMessage = "Home added successful";
        try{
            homeName = validateHomeName(request.getParameter("HomeName"));
            homeAddress = validateHomeAddress(request.getParameter("HomeAddress"));
        } catch (ValidationException e){
            homeMessage = "Please enter valid home address!";
            request.getSession().setAttribute("homeMessage", homeMessage);
            newHomeForward(request, response);
        }
        home.setHomeName(homeName);
        home.setHomeAddress(homeAddress);
        homeDAO.addNewHome(home);
        request.getSession().setAttribute("homeMessage", homeMessage);
        newHomeForward(request, response);
    }
}
