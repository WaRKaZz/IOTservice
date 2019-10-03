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

import static util.ServiceManagement.isApplyPressed;
import static validation.HomeValidator.validateHomeAddress;
import static validation.HomeValidator.validateHomeName;

public class AddNewHomeService implements Service {
    private String homeMessage = "";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            createNewHome(request, response);
        } else {
            homeMessage = "Devices into home you can add later";
            request.getSession().setAttribute("homeMessage", homeMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/newHome.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException, SQLException, ConnectionException{
        request.getSession().setAttribute("homeMessage", homeMessage);
        response.sendRedirect("/addNewHome");
    }

    private void createNewHome(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        Home home = new Home();
        String homeName = "", homeAddress = "";
        boolean validationException = false;
        try{
            homeName = validateHomeName(request.getParameter("homeName"));
            homeAddress = validateHomeAddress(request.getParameter("homeAddress"));
        } catch (ValidationException e){
            homeMessage = "Please enter valid home address!";
            validationException = true;
        }
        if(!validationException){
            home.setHomeName(homeName);
            home.setHomeAddress(homeAddress);
            homeDAO.addNewHome(home);
        }
        homeMessage = "Home added successful";
        refreshPage(request, response);
    }
}
