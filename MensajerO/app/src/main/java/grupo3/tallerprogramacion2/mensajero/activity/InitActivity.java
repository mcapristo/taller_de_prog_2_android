package grupo3.tallerprogramacion2.mensajero.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class InitActivity extends ActionBarActivity {
    private final RestService restService = RestServiceFactory.getRestService();
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(UrlConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String ip = sharedPref.getString("IP", "");
        UrlConstants.setBaseUrl(ip);

        this.username = sharedPref.getString(RestService.LOGIN_RESPONSE_NAME, "");
        this.password = sharedPref.getString(RestService.LOGIN_PASSWORD, "");
        if(username != "" && password != "" && ip != ""){
            try {
                restService.login(username, password, this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void processLoginResponse(UserDTO user){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(RestService.LOGIN_RESPONSE_NAME, user.getUsername());
        intent.putExtra(RestService.LOGIN_TOKEN, user.getToken());
        intent.putExtra(RestService.LOGIN_FULL_NAME, user.getName());
        intent.putExtra(RestService.LOGIN_PASSWORD, password);
        startActivity(intent);
        finish();
    }

    public void handleUnexpectedError() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
