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

    public void handleUnexpectedError(int errorCode) {
        this.errorDialog = (new ExceptionsHandle(this, errorCode)).loadError();
        this.errorDialog.show();
    }

    public void createUserClick(View view) {
        mUserNameView.setError(null);
        mFullNameView.setError(null);
        mPasswordView.setError(null);

        String userName = mUserNameView.getText().toString();
        String fullName = mFullNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        restService.createUser(userName, fullName, password, this);
    }

    public void processCreateUserResponse(UserDTO user) {
        Intent intent = new Intent(this, CreateUserResponseActivity.class);
        intent.putExtra(USER_NAME, user.getName());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
