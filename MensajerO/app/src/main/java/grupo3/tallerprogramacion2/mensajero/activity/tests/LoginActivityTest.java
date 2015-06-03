package grupo3.tallerprogramacion2.mensajero.activity.tests;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
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
        try {
            loginActivity.processLoginResponse(getErrorResponse());
            assertEquals(loginActivity.getString(R.string.error_incorrect_password), mPasswordView.getError());
        } catch (JSONException e) {
            System.out.println("testResponseProcess :: Error");
        }
    }

    @SmallTest
    public void test_successResponse_processLoginResponse_navigateNextActivity() {
        try {
            loginActivity.processLoginResponse(getSuccessResponse());
            final Intent launchIntent = getStartedActivityIntent();

            assertNotNull("Intent was null", launchIntent);
            assertTrue(isFinishCalled());

            final String responseName = launchIntent.getStringExtra(RestService.LOGIN_RESPONSE_NAME);
            final String responseToken = launchIntent.getStringExtra(RestService.LOGIN_TOKEN);

            assertEquals("Username is wrong", "testuser", responseName);
            assertEquals("Token is wrong", "123456", responseToken);

        } catch (JSONException e) {
            System.out.println("testResponseProcess :: Error");
        }
    }

    private JSONObject getErrorResponse() {
        JSONObject response = new JSONObject();
        try {
            response.put("result", "ERROR");
        } catch(JSONException e) {
            System.out.println("Error building error response");
        }
        return response;
    }

    private JSONObject getSuccessResponse() {
        JSONObject response = new JSONObject();
        try {
            response.put("result", "OK");
            JSONObject data = new JSONObject();
            data.put("username", "testuser");
            data.put("token", "123456");
            response.put("data", data);
        } catch(JSONException e) {
            System.out.println("Error building error response");
        }
        return response;
    }
}
