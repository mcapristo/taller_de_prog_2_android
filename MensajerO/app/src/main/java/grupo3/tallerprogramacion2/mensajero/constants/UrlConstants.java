package grupo3.tallerprogramacion2.mensajero.constants;

public class UrlConstants {

    public static final String BASE_URL = "http://192.168.0.18:8282/rest/";
    public static final String LOGIN_SERVICE = "login/";

    public static String getLoginServiceUrl() {
        return (BASE_URL + LOGIN_SERVICE);
    }

}
