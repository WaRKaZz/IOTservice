package kz.epam.IOTservice.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static final Map<String, Service> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put("/login", new LoginService());
        FACTORY_MAP.put("/logout", new LogoutService());
        FACTORY_MAP.put("/index", new IndexService());
        FACTORY_MAP.put("/registration", new RegistrationService());
        FACTORY_MAP.put("/submitRegistration", new RegistrationSubmitService());
        FACTORY_MAP.put("/main", new MainPageService());
        FACTORY_MAP.put("/chooseHome", new HomeViewService());
        FACTORY_MAP.put("/addNewHome", new AddNewHomeService());
        FACTORY_MAP.put("/addNewDevice", new AddNewDeviceService());
        FACTORY_MAP.put("/settings", new SettingsService());
        FACTORY_MAP.put("/updateDevice", new UpdateDeviceService());
        FACTORY_MAP.put("/updateHome", new UpdateHomeService());
        FACTORY_MAP.put("/devices", new DeviceViewService());
        FACTORY_MAP.put("/changeDevice", new DeviceUpdateService());
        FACTORY_MAP.put("/administration", new UserAdministrationService());
        FACTORY_MAP.put("/profile", new ProfileService());
    }

    public static synchronized Service getService(String serviceRequest){
        Service service;
        if (FACTORY_MAP.containsKey(serviceRequest)){
            service = FACTORY_MAP.get(serviceRequest);
        } else {
            service = new PageNotFoundService();
        }
        return service;
    }
}
