package kz.epam.iotservice.validation;

import kz.epam.iotservice.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static kz.epam.iotservice.util.IOTServiceConstants.*;

public class UserValidation extends AbstractValidation {
    private static final int MIN_LOGIN_LENGTH = 4;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 30;

    public static String validateLogin(String userLogin)throws ValidationException {
        final Pattern pattern = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = pattern.matcher(userLogin);
        if (((EMPTY_STRING.equals(userLogin) || (SPACE.equals(userLogin)) ||
                (userLogin.length() > MAX_TINYTEXT_LENGTH) || (userLogin.length() < MIN_LOGIN_LENGTH) ||
                !matcher.matches()))){
            throw new ValidationException();
        } else {
            return validateString(userLogin, MAX_TINYTEXT_LENGTH);
        }
    }

    public static String validatePassword(String userPassword) throws ValidationException{
        if ((userPassword == null) || (EMPTY_STRING.equals(userPassword) || (SPACE.equals(userPassword)) ||
                (userPassword.length() > MAX_PASSWORD_LENGTH) || (userPassword.length() < MIN_PASSWORD_LENGTH))){
            throw new ValidationException();
        } else{
            return validateString(userPassword, MAX_TINYTEXT_LENGTH);
        }
    }
}
