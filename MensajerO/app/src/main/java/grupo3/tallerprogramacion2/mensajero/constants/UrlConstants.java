package grupo3.tallerprogramacion2.mensajero.constants;

public class UrlConstants {

    public static final String BASE_URL = "http://192.168.1.109:8000/api/";
    public static final String LOGIN_SERVICE = "login/";
    public static final String USER_SERVICE = "user/";
    public static final String CONVERSATION_SERVICE = "conversation/";

    public static String getLoginServiceUrl() { return (BASE_URL + LOGIN_SERVICE); }
    public static String getUserServiceUrl() { return (BASE_URL + USER_SERVICE); }
    public static String getConversationServiceUrl() {
        return (BASE_URL + CONVERSATION_SERVICE);
    }

}
