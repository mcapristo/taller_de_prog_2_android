package grupo3.tallerprogramacion2.mensajero.service.impl;

import android.support.v4.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grupo3.tallerprogramacion2.mensajero.activity.ChatFragment;
import grupo3.tallerprogramacion2.mensajero.activity.ContactFragment;
import grupo3.tallerprogramacion2.mensajero.activity.CreateUserActivity;
import grupo3.tallerprogramacion2.mensajero.activity.HomeActivity;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.network.GsonRequest;
import grupo3.tallerprogramacion2.mensajero.factory.RequestQueueFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class RestServiceImpl implements RestService {

    private static RestServiceImpl ourInstance = null;
    private static RequestQueueFactory requestQueueFactory;

    public static RestServiceImpl getInstance() {
        if(ourInstance == null) {
            ourInstance = new RestServiceImpl();
        }
        return ourInstance;
    }

    protected RestServiceImpl() {
    }

    /*
    @Override
    public void login(String username, String password, final LoginActivity context) {
        String url = UrlConstants.getLoginServiceUrl() + username + "/" + password;

        // Request a string response from the provided URL.
        GsonRequest request = new GsonRequest(url, UserDTO.class, null,
                new Response.Listener<UserDTO>() {
                    @Override
                    public void onResponse(UserDTO user) {
                        context.processLoginResponse(user);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        context.handleUnexpectedError(error);
                    }
        });

        // Add the request to the RequestQueue.
        RequestQueueFactory.getRequestQueue(context).add(request);
    }
    */

    @Override
    public void login(final String username, final String password, final LoginActivity context) {
        String url = UrlConstants.getLoginServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // handle response
                        context.processLoginResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("password", password);
                return headers;
            }
        };

        req.setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    @Override
    public void createUser(final String userName, final String fullName, final String password, final CreateUserActivity context) {
        String url = UrlConstants.getCreateUserServiceUrl();

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", userName);
        params.put("name", fullName);
        params.put("password", password);

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String result = response.toString();
                        context.processCreateUserResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // add the request object to the queue to be executed
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    @Override
    public void getConversations(String userName, String token, final ChatFragment fragment, final FragmentActivity context) {
        String url = UrlConstants.getConversationServiceUrl();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("username", userName);
        headers.put("token", token);

        GsonRequest request = new GsonRequest(url, ConversationDTO[].class, headers,
                new Response.Listener<ArrayList<ConversationDTO>>() {
                    @Override
                    public void onResponse(ArrayList<ConversationDTO> conversations) {
                        fragment.PopulateContacts(conversations);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        fragment.handleUnexpectedError(error);
                    }
                });

        // Add the request to the RequestQueue.
        RequestQueueFactory.getRequestQueue(context).add(request);
    }

    @Override
    public void getUsers(String userName, String token, final ContactFragment fragment, final FragmentActivity context){

    }
}
