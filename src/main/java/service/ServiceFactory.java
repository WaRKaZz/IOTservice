package service;

import javax.xml.stream.FactoryConfigurationError;
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
