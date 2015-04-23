package grupo3.tallerprogramacion2.mensajero.service;


import org.json.JSONException;
import org.json.JSONObject;

public class RestServiceMockImpl implements RestService {
    @Override
    public JSONObject login(String username, String password) {
        JSONObject response = new JSONObject();

        try {
            response.put(RESPONSE_CODE, "OK");
            response.put(LOGIN_RESPONSE_NAME, "Matias");
            response.put(LOGIN_TOKEN, "12345678");
        } catch (JSONException e) {
            response = null;
        }

        return response;
    }
}
