package grupo3.tallerprogramacion2.mensajero.service.impl;

import android.app.DownloadManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class RestServiceImpl implements RestService {

    private static RestServiceImpl ourInstance = new RestServiceImpl();

    public static RestServiceImpl getInstance() {
        return ourInstance;
    }

    private RestServiceImpl() { }

    @Override
    public JSONObject login(String username, String password) {
        //TODO:Integrar con rest services reales usando voley como en el ejemplo de abajo
        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(this); TODO: Pasar el contexto por parámetro al servicio.
        String url ="http://localhost/login/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        //queue.add(stringRequest); // TODO: descomentar cuando se haya inicializado correctamente la queue.

        return null;
    }


}
