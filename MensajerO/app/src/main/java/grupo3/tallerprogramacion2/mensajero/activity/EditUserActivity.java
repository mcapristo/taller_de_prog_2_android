package grupo3.tallerprogramacion2.mensajero.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class EditUserActivity extends ActionBarActivity {

    private final RestService restService = RestServiceFactory.getRestService();
    private String username;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Bundle args = getIntent().getExtras();
        this.username = args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.token = args.getString(RestService.LOGIN_TOKEN);

        UserDTO user = new UserDTO();
        user.setName("mateo bosco");
        user.setPassword("contrasenia");

        this.completeField(user);

    }

    public void completeField(UserDTO user){
        EditText fullnameEditText = (EditText)findViewById(R.id.fullnameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        fullnameEditText.setText(user.getName());
        passwordEditText.setText(user.getPassword());
    }

    public void cancelButtonOnClick(View v){
        finish();
    }

    public void saveButtonOnClick(View v){
        UserDTO modifiedUser = new UserDTO();
        EditText fullnameEditText = (EditText)findViewById(R.id.fullnameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        String newFullname = fullnameEditText.getText().toString();
        String newPassword = passwordEditText.getText().toString();
        modifiedUser.setUsername(username);
        modifiedUser.setPassword(newPassword);
        modifiedUser.setName(newFullname);
        restService.updateUser(username, token, modifiedUser, this);
    }

    public void handleResponse(){
        int a = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_user, menu);
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
