package service;

import database.HomeDAO;
import entity.Home;
import entity.User;
import exception.ConnectionException;
import exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static util.ServiceManagement.isApplyPressed;
import static validation.HomeValidator.*;

public class UpdateHomeService implements Service {
    private String homeMessage = "";
    private static final int ADMIN_ROLE = 1;
    private HomeDAO homeDAO = new HomeDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            updateHome(request, response);
        } else if (isDeleteHomePressed(request)) {
            deleteHome(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/updateHome.jsp");
            request.getSession().setAttribute("homeMessage", homeMessage);
            requestDispatcher.forward(request, response);
        }
    }

    private void refreshPage (HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException{
        User user = (User) request.getSession().getAttribute("user");
        List<Home> homeAdminList = homeDAO.getHomeListByRole(user, ADMIN_ROLE);
        request.getSession().setAttribute("homeAdminList", homeAdminList);
        request.getSession().setAttribute("homeMessage", homeMessage);
        response.sendRedirect("/updateHome");
    }

    private static boolean isDeleteHomePressed(HttpServletRequest request){
        if (request.getParameter("delete") != null){
            return  request.getParameter("delete").equals("true");
        } else {
            return false;
        }
    }

    private void deleteHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        Long homeID = (long) 0;
        boolean validationException = false;
        try {
            homeID = validateID(request.getParameter("homeID"));
        } catch (ValidationException e){
            homeMessage = "Enter correct home ID";
            validationException = true;
        }
        if (!validationException){
            homeDAO.deleteHome(homeID);
            homeMessage = "Home deleted successfully";
        }
        refreshPage(request, response);
    }

    private void updateHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException, ConnectionException{
        Home home = new Home();
        Long homeID = (long) 0;
        boolean validationException = false;
        String homeName = "", homeAddress = "";
        try {
            homeID = validateID(request.getParameter("homeID"));
            homeAddress = validateHomeAddress(request.getParameter("homeAddress"));
            homeName = validateHomeName(request.getParameter("homeName"));

        } catch (ValidationException e){
            homeMessage = "Please enter correct data of Home";
            validationException = true;
        }
        if (!validationException){
            home.setHomeAddress(homeAddress);
            home.setHomeName(homeName);
            home.setHomeID(homeID);
            homeDAO.updateHome(home);
            homeMessage = "Home updated successfully";
        }
        refreshPage(request, response);
    }
}
