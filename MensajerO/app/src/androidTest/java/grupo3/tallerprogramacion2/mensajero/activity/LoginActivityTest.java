package grupo3.tallerprogramacion2.mensajero.activity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.aplication.MensajerO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class LoginActivityTest extends ActivityUnitTestCase<LoginActivity>{

    private LoginActivity loginActivity;
    private EditText mPasswordView;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), LoginActivity.class);
        startActivity(mLaunchIntent, null, null);
        loginActivity = getActivity();
        mPasswordView = (EditText) loginActivity.findViewById(R.id.password);
    }

    @SmallTest
    public void test_errorResponse_processLoginResponse_ShowError() {
        loginActivity.processLoginResponse(getErrorResponse());
        assertEquals(mPasswordView.getError(), "El password que ingresó es incorrecto =(");
    }

    @SmallTest
    public void test_successResponse_processLoginResponse_navigateNextActivity() {
        loginActivity.processLoginResponse(getSuccessResponse());
        final Intent launchIntent = getStartedActivityIntent();

        assertNotNull("Intent was null", launchIntent);
        assertTrue(isFinishCalled());

        final String responseName = launchIntent.getStringExtra(RestService.LOGIN_RESPONSE_NAME);
        final String responseToken = launchIntent.getStringExtra(RestService.LOGIN_TOKEN);

        assertEquals("Username is wrong", "testuser", responseName);
        assertEquals("Token is wrong", "123456", responseToken);
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
