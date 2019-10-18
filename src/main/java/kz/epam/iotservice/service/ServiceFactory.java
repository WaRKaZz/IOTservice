package kz.epam.iotservice.service;

import java.util.HashMap;
import java.util.Map;

import static kz.epam.iotservice.util.ConstantsUri.*;

public class ServiceFactory {

    private static final Map<String, Service> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(LOGIN_URI, new LoginService());
        FACTORY_MAP.put(LOGOUT_URI, new LogoutService());
        FACTORY_MAP.put(INDEX_URI, new IndexService());
        FACTORY_MAP.put(REGISTRATION_URI, new RegistrationService());
        FACTORY_MAP.put(SUBMIT_REGISTRATION_URI, new RegistrationSubmitService());
        FACTORY_MAP.put(MAIN_URI, new MainPageService());
        FACTORY_MAP.put(CHOOSE_HOME_URI, new HomeViewService());
        FACTORY_MAP.put(ADD_NEW_HOME_URI, new AddNewHomeService());
        FACTORY_MAP.put(ADD_NEW_DEVICE_URI, new AddNewDeviceService());
        FACTORY_MAP.put(SETTINGS_URI, new SettingsService());
        FACTORY_MAP.put(UPDATE_DEVICE_URI, new UpdateDeviceService());
        FACTORY_MAP.put(UPDATE_HOME_URI, new UpdateHomeService());
        FACTORY_MAP.put(DEVICES_URI, new DeviceViewService());
        FACTORY_MAP.put(CHANGE_DEVICE_URI, new DeviceUpdateService());
        FACTORY_MAP.put(ADMINISTRATION_URI, new UserAdministrationService());
        FACTORY_MAP.put(PROFILE_URI, new ProfileService());
        FACTORY_MAP.put(LANGUAGE_URI, new ChangeLanguageService());
    }

    private ServiceFactory() {
    }

    public static synchronized Service getService(String serviceRequest) {
        Service service;
        if (FACTORY_MAP.containsKey(serviceRequest)) {
            service = FACTORY_MAP.get(serviceRequest);
        } else {
            service = new PageNotFoundService();
        }
        return service;
    }
}
