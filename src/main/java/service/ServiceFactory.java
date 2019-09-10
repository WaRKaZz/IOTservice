package service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private static final Map<String, Service> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put("/login", new LoginService());
        FACTORY_MAP.put("/logout", new LogoutService());
        FACTORY_MAP.put("/index", new IndexService());

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
