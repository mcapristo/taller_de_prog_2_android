package grupo3.tallerprogramacion2.mensajero.service;

import grupo3.tallerprogramacion2.mensajero.activity.CreateUserActivity;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;

public interface RestService {
    public final static String RESPONSE_CODE = "grupo3.tallerprogramacion2.mensajero.service.RestService.CODE";
    public final static String LOGIN_RESPONSE_NAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_RESPONSE_NAME";
    public final static String LOGIN_TOKEN = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_TOKEN";

    public void login(String username, String password, final LoginActivity context);

    public void createUser(String userName, String fullName, String password, final CreateUserActivity context);
}
