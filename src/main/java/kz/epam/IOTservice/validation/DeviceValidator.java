package kz.epam.IOTservice.validation;

import kz.epam.IOTservice.exception.ValidationException;

public class DeviceValidator extends AbstractValidation {

    public static String validateDeviceName(String deviceName) throws ValidationException {
        return validateString(deviceName, MAX_TINYTEXT_LENGTH);
    }
}
