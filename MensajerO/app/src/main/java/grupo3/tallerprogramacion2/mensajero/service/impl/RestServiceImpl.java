package grupo3.tallerprogramacion2.mensajero.service.impl;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grupo3.tallerprogramacion2.mensajero.activity.ChatActivity;
import grupo3.tallerprogramacion2.mensajero.activity.ChatFragment;
import grupo3.tallerprogramacion2.mensajero.activity.ContactFragment;
import grupo3.tallerprogramacion2.mensajero.activity.CreateUserActivity;
import grupo3.tallerprogramacion2.mensajero.activity.HomeActivity;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTO;
import grupo3.tallerprogramacion2.mensajero.dto.ContactsDTOContainer;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTO;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTOContainer;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTOContainer;
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
    public void login(final String username, final String password, final LoginActivity context) throws JSONException{
        String url = UrlConstants.getLoginServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // handle response
                        try {
                            String username = response.getString("result");
                            context.processLoginResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError(1001);
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
                        UserDTOContainer userContainer = new Gson().fromJson(response.toString(), UserDTOContainer.class);
                        if("OK".equals(userContainer.getResult())) {
                            context.processCreateUserResponse(userContainer.getData());
                        } else {
                            context.handleUnexpectedError(userContainer.getCode());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError(1001);
            }
        }
        );

        req.setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // add the request object to the queue to be executed
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    @Override
    public void getConversations(final String username, final String token, final ChatFragment fragment, final FragmentActivity context) {
        String url = UrlConstants.getConversationServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
            new Response.Listener<JSONObject> () {
                @Override
                public void onResponse(JSONObject response) {
                    ConversationDTOContainer conversationContainer =
                            new Gson().fromJson(response.toString(), ConversationDTOContainer.class);
                    if("OK".equals(conversationContainer.getResult())) {
                        fragment.PopulateContacts(conversationContainer.getData());
                    } else {
                    }
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                return headers;
            }
        };


        // Add the request to the RequestQueue.
        RequestQueueFactory.getRequestQueue(context).add(req);
    }

    @Override
    public void getUsers(final String username, final String token, final ContactFragment fragment, final FragmentActivity context){
        String url = UrlConstants.getUserServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        ContactsDTOContainer contactsContainer =
                                new Gson().fromJson(response.toString(), ContactsDTOContainer.class);
                        if("OK".equals(contactsContainer.getResult())) {
                            fragment.PopulateContacts(contactsContainer.getData());
                        } else {
                            int a = 0;
                            // Do something with userContainer.getCode() to display proper error
                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int a = 0;
                    //handle error
                }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    @Override
    public void sendMessage(String token, ChatMessageDTO message, ChatActivity context) {
        String url = UrlConstants.getCreateUserServiceUrl();

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", message.getEmisor());
        params.put("token", token);

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error
            }
        }
        );

        // add the request object to the queue to be executed
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }
}
