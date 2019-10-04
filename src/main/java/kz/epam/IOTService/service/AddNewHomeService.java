package kz.epam.IOTService.service;

import kz.epam.IOTService.database.HomeDAO;
import kz.epam.IOTService.entity.Home;
import kz.epam.IOTService.entity.User;
import kz.epam.IOTService.exception.ConnectionException;
import kz.epam.IOTService.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static kz.epam.IOTService.util.ServiceManagement.isApplyPressed;
import static kz.epam.IOTService.validation.HomeValidator.validateHomeAddress;
import static kz.epam.IOTService.validation.HomeValidator.validateHomeName;

public class AddNewHomeService implements Service {
    private String homeMessage = "key.empty";
    private static final int ADMIN_IN_HOME_ROLE = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            createNewHome(request, response);
        } else {
            request.getSession().setAttribute("homeMessage", homeMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/newHome.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private void refreshPage(HttpServletRequest request, HttpServletResponse response, List homeAdminList)
            throws  IOException, ServletException, SQLException, ConnectionException{
        request.getSession().setAttribute("homeMessage", homeMessage);
        request.getSession().setAttribute("homeAdminList", homeAdminList);
        response.sendRedirect("/addNewHome");
    }

    private void createNewHome(HttpServletRequest request, HttpServletResponse response)
            throws  IOException, ServletException, SQLException, ConnectionException{
        HomeDAO homeDAO = new HomeDAO();
        Home home = new Home();
        User user = (User) request.getSession().getAttribute("user");
        String homeName = "", homeAddress = "";
        boolean validationException = false;
        try{
            homeName = validateHomeName(request.getParameter("homeName"));
            homeAddress = validateHomeAddress(request.getParameter("homeAddress"));
        } catch (ValidationException e){
            homeMessage = "key.newHomeMessageValidDeviceAddress";
            validationException = true;
        }
        if(!validationException){
            home.setHomeName(homeName);
            home.setHomeAddress(homeAddress);
            Long newHomeID = homeDAO.addNewHome(home);
            homeDAO.addHomeDependencyAdministrator(newHomeID, user.getUserID());
        }
        homeMessage = "key.newHomeMessageSuccess";
        refreshPage(request, response, homeDAO.getHomeListByRole(user, ADMIN_IN_HOME_ROLE));
    }
}
