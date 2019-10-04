package kz.epam.IOTService.entity;

import java.util.Objects;

public class User {
    private Long userID = Long.parseLong("0");
    private String userLogin = "";
    private String userPassword = "";
    private int userRole = 0;
    private Boolean userBlocked = false;

    public Boolean getUserBlocked() {
        return userBlocked;
    }

    public void setUserBlocked(Boolean userBlocked) {
        this.userBlocked = userBlocked;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(userID, userLogin, userPassword, userRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID.equals(user.userID) &&
                Objects.equals(userLogin, user.userLogin) &&
                Objects.equals(userPassword, user.userPassword) &&
                Objects.equals(userRole, user.userRole);
    }
}
