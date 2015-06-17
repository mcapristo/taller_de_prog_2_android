package grupo3.tallerprogramacion2.mensajero.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class CreateUserResponseActivity extends ActionBarActivity {

    public final static String USER_NAME = "grupo3.tallerprogramacion2.mensajero.activity.CreateUserActivity.USER_NAME";

    private TextView mNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_response);
        findViewById(R.id.CreateUserResponse_b_Exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateUserResponseActivity.this, LoginActivity.class));
            }
        });

        Bundle args = getIntent().getExtras();
        String myUserName = args.getString(USER_NAME);
        mNameView = (TextView) findViewById(R.id.CreateUserResponse_f_UserName);
        mNameView.setText(myUserName + "!");
    }
}
