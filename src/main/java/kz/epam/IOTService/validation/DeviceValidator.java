package kz.epam.IOTService.validation;

import kz.epam.IOTService.exception.ValidationException;

public class DeviceValidator extends AbstractValidation {

    public static String validateDeviceName(String deviceName) throws ValidationException {
        return validateString(deviceName, MAX_TINYTEXT_LENGTH);
    }
}
