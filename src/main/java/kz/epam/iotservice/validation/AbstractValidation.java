package kz.epam.iotservice.validation;

import kz.epam.iotservice.exception.ValidationException;

import static kz.epam.iotservice.constants.Other.EMPTY_STRING;
import static kz.epam.iotservice.constants.Other.STRING_ZERO;

public abstract class AbstractValidation {
    static final int MAX_TINYTEXT_LENGTH = 23;
    static final int MAX_TEXT_LENGTH = 5000;

    public static Long validateID(String ID) throws ValidationException {
        if (EMPTY_STRING.equals(ID)) {
            ID = STRING_ZERO;
        }
        return validateLong(ID);
    }

    static String validateString(String string, int maxLength) throws ValidationException {
        if (string == null || string.equals(EMPTY_STRING) || string.length() > maxLength) {
            throw new ValidationException();
        } else {
            return string;
        }
    }

    private static Long validateLong(String stringToLong) throws ValidationException {
        Long longDigit;
        try {
            longDigit = Long.parseLong(stringToLong);
        } catch (NumberFormatException e) {
            throw new ValidationException();
        }
        return longDigit;
    }

    static int validateInteger(String stringInt) throws ValidationException {
        int intDigit;
        try {
            intDigit = Integer.parseInt(stringInt);
        } catch (NumberFormatException e) {
            throw new ValidationException();
        }
        return intDigit;
    }

    static boolean validateBoolean(String stringBool) throws ValidationException {
        boolean isTrue;
        try {
            isTrue = Boolean.parseBoolean(stringBool);
        } catch (NumberFormatException e) {
            throw new ValidationException();
        }
        return isTrue;
    }
}
