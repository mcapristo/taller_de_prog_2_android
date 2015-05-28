package grupo3.tallerprogramacion2.mensajero.constants;

public class UrlConstants {

    public static final String BASE_URL = "http://192.168.1.116";
    // public static final String LOCALHOST_URL = "http://10.0.2.2";
    public static final String LOCALHOST_PORT = ":8000/";
    public static final String API = "api/";
    public static final String LOGIN_SERVICE = "login";
    public static final String USER_SERVICE = "user";
    public static final String CONVERSATION_SERVICE = "conversation";

    public static String getCreateUserServiceUrl() {
        return(BASE_URL + LOCALHOST_PORT + API + USER_SERVICE);
    }
    public static String getLoginServiceUrl() { return (BASE_URL + LOCALHOST_PORT + API + LOGIN_SERVICE); }
    public static String getUserServiceUrl() { return (BASE_URL + LOCALHOST_PORT + API + USER_SERVICE); }
    public static String getConversationServiceUrl() {
        return (BASE_URL + LOCALHOST_PORT + API + CONVERSATION_SERVICE);
    }

}
