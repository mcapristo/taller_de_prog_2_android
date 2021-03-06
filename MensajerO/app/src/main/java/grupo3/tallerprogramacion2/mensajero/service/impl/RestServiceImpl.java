package grupo3.tallerprogramacion2.mensajero.service.impl;

import android.app.DownloadManager;
import android.support.v4.app.FragmentActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import grupo3.tallerprogramacion2.mensajero.activity.ChatActivity;
import grupo3.tallerprogramacion2.mensajero.activity.ChatFragment;
import grupo3.tallerprogramacion2.mensajero.activity.ContactFragment;
import grupo3.tallerprogramacion2.mensajero.activity.CreateUserActivity;
import grupo3.tallerprogramacion2.mensajero.activity.EditUserActivity;
import grupo3.tallerprogramacion2.mensajero.activity.HomeActivity;
import grupo3.tallerprogramacion2.mensajero.activity.InitActivity;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.activity.UserDetailActivity;
import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;
import grupo3.tallerprogramacion2.mensajero.dto.BaseDTO;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTO;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTOContainer;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTOSingleContainer;
import grupo3.tallerprogramacion2.mensajero.dto.ContactsDTOContainer;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTOContainer;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTOContainer;
import grupo3.tallerprogramacion2.mensajero.factory.RequestQueueFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

/**
 * Implementacion de {@link RestService} que encapsula toda la interaccion con la rest API del servidor.
 */
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

    /**
     * @param username
     * @param password
     * @param context
     * @throws JSONException
     */
    @Override
    public void login(final String username, final String password, final LoginActivity context) throws JSONException{
        String url = UrlConstants.getLoginServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserDTOContainer userContainer = new Gson().fromJson(response.toString(), UserDTOContainer.class);
                        if("OK".equals(userContainer.getResult())) {
                            context.processLoginResponse(userContainer.getData());
                        } else {
                            context.handleUnexpectedError(userContainer.getCode());
                            context.showProgress(false);
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

        /*
        req.setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                */

        // Add the request to the RequestQueue.
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    /**
     * @param username
     * @param password
     * @param context
     * @throws JSONException
     */
    @Override
    public void login(final String username, final String password, final InitActivity context) throws JSONException{
        String url = UrlConstants.getLoginServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserDTOContainer userContainer = new Gson().fromJson(response.toString(), UserDTOContainer.class);
                        if("OK".equals(userContainer.getResult())) {
                            context.processLoginResponse(userContainer.getData());
                        } else {
                            context.handleUnexpectedError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError();
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

        /*
        req.setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                */

        // Add the request to the RequestQueue.
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    /**
     * @param userName
     * @param fullName
     * @param password
     * @param context
     */
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

        /*
        req.setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                */

        // add the request object to the queue to be executed
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    /**
     * @param username
     * @param token
     * @param fragment
     * @param context
     */
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
                        fragment.getAllUsers(conversationContainer.getData());
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

    /**
     * @param username
     * @param token
     * @param context
     */
    @Override
    public void logOut(final String username, final String token, final HomeActivity context) {
        String url = UrlConstants.getLogoutServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        context.logOut();
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

    /**
     * @param username
     * @param token
     * @param fragment
     * @param context
     */
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

    /**
     * @param username
     * @param token
     * @param fragment
     * @param context
     */
    @Override
    public void getUsers(final String username, final String token, final ChatFragment fragment, final FragmentActivity context){
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

    /**
     * @param token
     * @param message
     * @param context
     */
    @Override
    public void sendMessage(final String token, final ChatMessageDTO message, final ChatActivity context) {
        String url = UrlConstants.getMessageServiceUrl();

        final String emisor = message.getEmisor();
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("emisor", message.getEmisor());
        params.put("receptor", message.getReceptor());
        params.put("body", message.getBody());

        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        ChatMessageDTOSingleContainer messageContainer =
                                new Gson().fromJson(response.toString(), ChatMessageDTOSingleContainer.class);
                        ArrayList<ChatMessageDTO> messages = new ArrayList<ChatMessageDTO>();
                        messages.add(messageContainer.getData());
                        if("OK".equals(messageContainer.getResult())) {
                            context.LoadMessages(messages);
                        } else {
                            int a = 0;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int a = 0;
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", emisor);
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    /**
     * @param username
     * @param token
     * @param receptor
     * @param context
     */
    @Override
    public void getMessages(final String username, final String token, final String receptor, final ChatActivity context) {
        String url = UrlConstants.getMessageServiceUrl() + "?username=" + receptor;

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        ChatMessageDTOContainer messagesContainer = new ChatMessageDTOContainer();
                        try {
                            if(response.getString("data").equals("[\"\"]")){
                                messagesContainer.setData(null);
                            }else{
                                messagesContainer =
                                        new Gson().fromJson(response.toString(), ChatMessageDTOContainer.class);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if("OK".equals(messagesContainer.getResult())) {
                            context.LoadMessages(messagesContainer.getData());
                        } else {
                            int a = 0;
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

    /**
     * @param username
     * @param token
     * @param user
     * @param context
     */
    public void updateUser(final String username, final String token, UserDTO user, final EditUserActivity context){
        String url = UrlConstants.getUserServiceUrl();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject> () {
            @Override
            public void onResponse(JSONObject response) {
                BaseDTO responseContainer =
                        new Gson().fromJson(response.toString(), BaseDTO.class);
                if("OK".equals(responseContainer.getResult())) {
                    context.handleResponse();
                } else {
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        };

        JSONObject a = user.toJSONObject();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, user.toJSONObject(), responseListener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                return headers;
            }
        };
        RequestQueueFactory.getRequestQueue(context).add(req);
    }

    /**
     * @param username
     * @param token
     * @param context
     */
    @Override
    public void getUser(final String username, final String token, final EditUserActivity context){
        String url = UrlConstants.getUserServiceUrl() +  "?username=" + username;

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserDTOContainer userContainer = new Gson().fromJson(response.toString(), UserDTOContainer.class);
                        if("OK".equals(userContainer.getResult())) {
                            context.populateData(userContainer.getData());
                        } else {
                            context.handleUnexpectedError(userContainer.getCode());
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
                headers.put("token", token);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }

    /**
     * @param username
     * @param token
     * @param receptorUsername
     * @param context
     */
    @Override
    public void getUser(final String username, final String token, String receptorUsername, final UserDetailActivity context){
        String url = UrlConstants.getUserServiceUrl() +  "?username=" + receptorUsername;

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserDTOContainer userContainer = new Gson().fromJson(response.toString(), UserDTOContainer.class);
                        if("OK".equals(userContainer.getResult())) {
                            context.populateData(userContainer.getData());
                        } else {
                            context.handleUnexpectedError(userContainer.getCode());
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
                headers.put("token", token);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        Request response = RequestQueueFactory.getRequestQueue(context).add(req);
    }
}
