package grupo3.tallerprogramacion2.mensajero.activity;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.aplication.MensajerO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.service.RestService;


public class UserDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ImageView profileImage = (ImageView)findViewById(R.id.viewProfileImage);
        profileImage.setDrawingCacheEnabled(true);

        Bundle args = getIntent().getExtras();
        UserDTO user = new UserDTO();
        user.setUsername(args.getString(RestService.LOGIN_RESPONSE_NAME));
        user.setName(args.getString(RestService.LOGIN_FULL_NAME));
        user.setProfileImage(args.getString(RestService.LOGIN_IMAGE));
        this.completeFields(user);
    }

    public void completeFields(UserDTO user){
        TextView nameTextView = (TextView)findViewById(R.id.nameTextView);
        TextView usernameTextView = (TextView)findViewById(R.id.usernameTextView);
        ImageView profileImage = (ImageView)findViewById(R.id.viewProfileImage);

        nameTextView.setText(user.getName());
        usernameTextView.setText(user.getUsername());

        if(user.getProfileImage() != null){
            Bitmap image = MensajerO.decodeBase64(user.getProfileImage());
            profileImage.setImageBitmap(image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
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
