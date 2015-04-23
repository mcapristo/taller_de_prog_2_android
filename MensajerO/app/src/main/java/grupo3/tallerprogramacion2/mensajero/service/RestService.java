package grupo3.tallerprogramacion2.mensajero.service;

import org.json.JSONObject;

import grupo3.tallerprogramacion2.mensajero.service.impl.RestServiceImpl;

public interface RestService {
    public final static String RESPONSE_CODE = "grupo3.tallerprogramacion2.mensajero.service.RestService.CODE";
    public final static String LOGIN_RESPONSE_NAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_RESPONSE_NAME";
    public final static String LOGIN_TOKEN = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_TOKEN";

    public JSONObject login(String username, String password);

}
