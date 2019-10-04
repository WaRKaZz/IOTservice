package kz.epam.IOTService.validation;

import kz.epam.IOTService.exception.ValidationException;

public class FunctionValidation extends AbstractValidation {

    public static boolean validateFunctionTrue(String trueStatement) throws ValidationException {
        return validateBoolean(trueStatement);
    }
    public static int validateFunctionInteger(String intStatement) throws ValidationException{
        return validateInteger(intStatement);
    }
    public static String validateFunctionString(String stringStatement) throws ValidationException{
        return validateString(stringStatement, MAX_TEXT_LENGTH);
    }
}
