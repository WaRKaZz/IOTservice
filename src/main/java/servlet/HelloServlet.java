package servlet;

import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    private User user = new User();


    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String userLoginCookie = "";
        String userPassCookie = "";
        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("pass");
        Cookie loginInfoCookie = new Cookie("userCookie", user.getLogin() + " " + user.getPass());
        Cookie[] cookies = httpServletRequest.getCookies();

        for (Cookie cookie: cookies){
            if (cookie.getName().equals("userCookie")){
                String[] getCookieValue = cookie.getValue().split(" ");
                userLoginCookie = getCookieValue[0];
                userPassCookie = getCookieValue[1];
            }
        }

        if ((userLoginCookie.equals(user.getLogin()))&&userPassCookie.equals(user.getPass())){
            httpServletResponse.getWriter().write("Your login is: " + user.getLogin() + "\nYour pass is: " + user.getPass());
        } else if (login.equals(user.getLogin())&&pass.equals(user.getPass())){
            httpServletResponse.getWriter().write("Your login is: " + user.getLogin() + "\nYour pass is: " + user.getPass());
            httpServletResponse.addCookie(loginInfoCookie);
        } else {
            httpServletResponse.getWriter().write("Bad login");
            httpServletResponse.setHeader("Refresh", "5;" + httpServletRequest.getContextPath() + "/index.jsp");
        }


    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }
}
