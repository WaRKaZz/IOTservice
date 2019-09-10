package validation;

import exception.ValidationException;

public abstract class AbstractValidation {
    private static final String EMPTY_STRING = "";
    static final int  MAX_TEXT_LENGTH = 5500;
    static final int MAX_TINYTEXT_LENGTH = 23;


    public static Long validateID (String ID) throws ValidationException{
        if (EMPTY_STRING.equals(ID)){
            ID = "0";
        }
        return validateLong(ID);
    }

    static  String validateString (String string, int maxLength) throws ValidationException{
        if (string == null || string.equals(EMPTY_STRING) || string.length() > maxLength){
            throw new ValidationException();
        } else {
            return string;
        }
    }

    static Long validateLong(String stringToLong) throws  ValidationException{
        Long longDigit;
        try {
            longDigit = Long.parseLong(stringToLong);
        } catch (NumberFormatException e){
            throw new ValidationException();
        }
        return longDigit;
    }

    static int validateInteger(String stringInt) throws ValidationException {
        int intDigit;
        try {
            intDigit = Integer.parseInt(stringInt);
        } catch (NumberFormatException e){
            throw new ValidationException();
        }
        return intDigit;
    }

    static boolean validateBoolean(String stringBool) throws  ValidationException {
        boolean isTrue;
        try {
            isTrue = Boolean.parseBoolean(stringBool);
        } catch (NumberFormatException e){
            throw new ValidationException();
        }
        return isTrue;
    }

}
