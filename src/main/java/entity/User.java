package entity;

import java.util.Objects;

public class User {
    private Long userID = Long.parseLong("0");
    private String userLogin = "";
    private String userPassword = "";
    private int userRole = 0;
    private String userFirstName = "";
    private String userLastName = "";

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userLogin, userPassword, userRole, userFirstName, userLastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID.equals(user.userID) &&
                Objects.equals(userLogin, user.userLogin) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(userRole, user.userRole) &&
                Objects.equals(userFirstName, user.userFirstName) &&
                Objects.equals(userLastName, user.userLastName);
    }
}
