package grupo3.tallerprogramacion2.mensajero.factory;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import grupo3.tallerprogramacion2.mensajero.network.mock.HttpStackMock;

public class RequestQueueFactory {

    private static boolean useMockedServer = false;

    public static RequestQueue getRequestQueue(Context context) {
        if(useMockedServer) {
            return Volley.newRequestQueue(context, new HttpStackMock());
        } else {
            return Volley.newRequestQueue(context);
        }
    }
}
