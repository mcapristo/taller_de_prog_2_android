package grupo3.tallerprogramacion2.mensajero.constants;

/**
 * Constantes para armado de url's con las cuales se invocará a la rest API.
 */
public class UrlConstants {

    //public static final String BASE_URL = "10.0.2.2";
    public static String BASE_URL = "";
    public static final String LOCALHOST_PORT = ":8000/";
    public static final String HTTP = "http://";
    public static final String API = "api/";
    public static final String LOGIN_SERVICE = "login";
    public static final String LOGOUT_SERVICE = "logout";
    public static final String USER_SERVICE = "user";
    public static final String CONVERSATION_SERVICE = "conversation";
    public static final String MESSAGE_SERVICE = "message";
    public static final String SHARED_PREFERENCES = "commonKey";

    public static String getCreateUserServiceUrl() {
        return(HTTP + BASE_URL + LOCALHOST_PORT + API + USER_SERVICE);
    }
    public static String getLoginServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + API + LOGIN_SERVICE); }
    public static String getLogoutServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + API + LOGOUT_SERVICE); }
    public static String getUserServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + API + USER_SERVICE); }
    public static String getConversationServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + API + CONVERSATION_SERVICE); }
    public static String getMessageServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + API + MESSAGE_SERVICE); }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
}
