package grupo3.tallerprogramacion2.mensajero.factory;

import grupo3.tallerprogramacion2.mensajero.service.RestService;
import grupo3.tallerprogramacion2.mensajero.service.impl.RestServiceImpl;

public class RestServiceFactory {

    // Load from config file
    private static boolean useMockedServer = true;

    public static RestService getRestService() {
        return RestServiceImpl.getInstance();
    }
}