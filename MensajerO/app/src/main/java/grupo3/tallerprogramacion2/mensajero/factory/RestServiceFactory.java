package grupo3.tallerprogramacion2.mensajero.factory;

import grupo3.tallerprogramacion2.mensajero.service.RestService;
import grupo3.tallerprogramacion2.mensajero.service.impl.RestServiceImpl;

/**
 * Factory para encapsular la implementación de RestService que se utilizará.
 */
public class RestServiceFactory {

    // Load from config file
    private static boolean useMockedServer = false;

    public static RestService getRestService() {
        return RestServiceImpl.getInstance();
    }
}