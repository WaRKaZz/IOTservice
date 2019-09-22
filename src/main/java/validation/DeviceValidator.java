package validation;

import exception.ValidationException;

public class DeviceValidator extends AbstractValidation {

    public static String validateDeviceName(String deviceName) throws ValidationException {
        return validateString(deviceName, MAX_TINYTEXT_LENGTH);
    }
}
