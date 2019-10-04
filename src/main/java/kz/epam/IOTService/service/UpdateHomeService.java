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
import static kz.epam.IOTService.validation.HomeValidator.*;

public class UpdateHomeService implements Service {
    private String homeMessage = "key.empty";
    private static final int ADMIN_ROLE = 1;
    private HomeDAO homeDAO = new HomeDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, SQLException, ConnectionException {
        if (isApplyPressed(request)){
            if(isDeleteHomePressed(request)){
                deleteHome(request, response);
            } else {
                updateHome(request, response);
            }
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
            homeMessage = "key.updateHomeMessageIncorrectID";
            validationException = true;
        }
        if (!validationException){
            homeDAO.deleteHome(homeID);
            homeMessage = "key.updateHomeMessageSuccessDelete";
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
            homeMessage = "key.updateHomeMessageIncorrectData";
            validationException = true;
        }
        if (!validationException){
            home.setHomeAddress(homeAddress);
            home.setHomeName(homeName);
            home.setHomeID(homeID);
            homeDAO.updateHome(home);
            homeMessage = "key.updateHomeMessageSuccessUpdate";
        }
        refreshPage(request, response);
    }
}
