package grupo3.tallerprogramacion2.mensajero.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.aplication.MensajerO;
import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public final static String LOGIN_RESPONSE_NAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_RESPONSE_NAME";

    private LoginActivity loginActivity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        loginActivity = getActivity();
    }
/*
    public void test_successResponse_processLoginResponse_SharedPrefsComlpeted() {
        UserDTO user = getSuccessResponse();
        loginActivity.processLoginResponse(user);
        SharedPreferences sharedPref = loginActivity.getApplicationContext().getSharedPreferences(UrlConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        assertEquals(user.getUsername(), "pepe");
        //assertEquals(user.getUsername(), sharedPref.getString(this.LOGIN_RESPONSE_NAME, null));
    }*/

    public void test_badUsername_processLoginResponse_ShowError() {
        loginActivity.handleUnexpectedError(1);
        SharedPreferences sharedPref = loginActivity.getApplicationContext().getSharedPreferences(UrlConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        assertNull(sharedPref.getString(this.LOGIN_RESPONSE_NAME, null));
        assertEquals("Username inválido", loginActivity.getErrorDialogMessage());
        loginActivity.finish();
    }

    private UserDTO getErrorResponse() {
        UserDTO response = new UserDTO();
        response.setResult("ERROR");
        return response;
    }

    private UserDTO getSuccessResponse() {
        UserDTO response = new UserDTO();
        response.setResult("OK");
        response.setUsername("testuser");
        response.setToken("123456");
        return response;
    }
}
