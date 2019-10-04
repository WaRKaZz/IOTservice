package kz.epam.IOTService.validation;

import kz.epam.IOTService.exception.ValidationException;

public class HomeValidator extends AbstractValidation {

    public static String validateHomeName(String homeName) throws ValidationException {
        return validateString(homeName, MAX_TINYTEXT_LENGTH);
    }

    public static String validateHomeAddress(String homeAddress) throws ValidationException{
        return validateString(homeAddress, MAX_TINYTEXT_LENGTH);
    }

}
