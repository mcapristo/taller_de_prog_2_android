package grupo3.tallerprogramacion2.mensajero.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import android.app.AlertDialog;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.constants.ResponseConstants;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.exceptions.ExceptionsHandle;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class CreateUserActivity extends ActionBarActivity {
    public final static String USER_NAME = "grupo3.tallerprogramacion2.mensajero.activity.CreateUserActivity.USER_NAME";
    private final RestService restService = RestServiceFactory.getRestService();

    // UI references.
    private EditText mUserNameView;
    private EditText mFullNameView;
    private EditText mPasswordView;

    private AlertDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mUserNameView = (EditText) findViewById(R.id.CreateUser_f_UserName);
        mFullNameView = (EditText) findViewById(R.id.CreateUser_f_FullName);
        mPasswordView = (EditText) findViewById(R.id.CreateUser_f_Password);
        findViewById(R.id.CreateUser_b_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateUserActivity.this, LoginActivity.class));
            }
        });
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    public void handleUnexpectedError(int errorCode) {
        this.errorDialog = (new ExceptionsHandle(this, errorCode)).loadError();
        this.errorDialog.show();
    }

    public void createUserClick(View view) {
        mUserNameView.setError(null);
        mFullNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserNameView.getText().toString();
        String fullName = mFullNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        restService.createUser(userName, fullName, password, this);
    }

   /* public void processCreateUserRequest(UserDTO userDTO) {
        if(ResponseConstants.OK_RESPONSE.equals(userDTO.getResult())) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(RestService.LOGIN_RESPONSE_NAME, userDTO.getName());
            intent.putExtra(RestService.LOGIN_TOKEN, userDTO.getToken());
            startActivity(intent);
            finish();
        } else {
            // showCredentialsError();
        }
    }*/

    public void processCreateUserResponse(UserDTO user) {
        // String username = response.getJSONObject("Data").getString("username");
        // String name = (String) response.getString("Name");
        Intent intent = new Intent(this, CreateUserResponseActivity.class);
        intent.putExtra(USER_NAME, user.getName());
        startActivity(intent);
        finish();
    }
}
