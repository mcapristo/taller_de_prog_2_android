package grupo3.tallerprogramacion2.mensajero.activity;

import android.app.Activity;
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

public class CreateUserResponseActivity extends Activity {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user_response, menu);
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
}
