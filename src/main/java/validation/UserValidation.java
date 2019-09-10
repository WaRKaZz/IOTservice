package validation;


import exception.ValidationException;

public class UserValidation extends AbstractValidation {
    private static final int MIN_LOGIN_LENGTH = 4;
    private static final int MIN_PASSWORD_LENGTH = 6;

    public static String validateLogin(String userLogin)throws ValidationException {
        if (userLogin.length() < MIN_LOGIN_LENGTH){
            throw new ValidationException();
        } else {
            validateString(userLogin, MAX_TINYTEXT_LENGTH);
            return userLogin;
        }
    }

    public static String validatePassword(String userPassword) throws ValidationException{
        if (userPassword.length() < MIN_PASSWORD_LENGTH){
            throw new ValidationException();
        } else{
            validateString(userPassword, MAX_TINYTEXT_LENGTH);
            return userPassword;
        }
    }

    public static int validateUserRole (String userRole) throws  ValidationException{
        return validateInteger(userRole);
    }

    public static String validateUserFirstName (String userFirstName) throws ValidationException{
        return validateString(userFirstName, MAX_TINYTEXT_LENGTH);
    }

    public static String validateUserLastName (String userLastName) throws ValidationException{
        return validateString(userLastName, MAX_TINYTEXT_LENGTH);
    }

}
