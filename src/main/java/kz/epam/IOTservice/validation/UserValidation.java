package kz.epam.IOTService.validation;


import kz.epam.IOTService.exception.ValidationException;

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

}
