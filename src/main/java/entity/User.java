package entity;

import javax.servlet.http.Cookie;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class User {
    private User user = new User();
    private Cookie cookie = null;
    private String login = null;
    private String pass = null;

    {
        try(InputStream input = new FileInputStream("/UserConfig/config.properties")){
            Properties properties = new Properties();
            login = properties.getProperty("login");
            pass = properties.getProperty("pass");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPass(String pass) {
        try(InputStream input = new FileInputStream("/UserConfig/config.properties")){
            Properties properties = new Properties();
            properties.setProperty("pass", pass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.pass = pass;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public void setLogin(String login) {
        try(InputStream input = new FileInputStream("/UserConfig/config.properties")){
            Properties properties = new Properties();
            properties.setProperty("login", login);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public String getLogin() {
        return login;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
