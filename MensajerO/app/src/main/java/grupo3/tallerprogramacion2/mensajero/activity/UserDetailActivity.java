package grupo3.tallerprogramacion2.mensajero.activity;

import android.app.AlertDialog;
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
import grupo3.tallerprogramacion2.mensajero.exceptions.ExceptionsHandle;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;


public class UserDetailActivity extends ActionBarActivity {

    private final RestService restService = RestServiceFactory.getRestService();
    private AlertDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ImageView profileImage = (ImageView)findViewById(R.id.viewProfileImage);
        profileImage.setDrawingCacheEnabled(true);

        Bundle args = getIntent().getExtras();
        String username = args.getString(RestService.LOGIN_RESPONSE_NAME);
        String token = args.getString(RestService.LOGIN_TOKEN);
        String receptorUsername = args.getString(RestService.CHAT_RECEPTOR_USERNAME);

        restService.getUser(username, token, receptorUsername, this);
    }

    public void populateData(UserDTO user){
        this.completeFields(user);
    }

    public void completeFields(UserDTO user){
        TextView nameTextView = (TextView)findViewById(R.id.nameTextView);
        TextView usernameTextView = (TextView)findViewById(R.id.usernameTextView);
        CheckBox online = (CheckBox)findViewById(R.id.onlineCheckBox);
        ImageView profileImage = (ImageView)findViewById(R.id.viewProfileImage);
        TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
        TextView lastActivityTextView = (TextView)findViewById(R.id.lastActivityDatetime);

        nameTextView.setText(user.getName());
        usernameTextView.setText(user.getUsername());
        online.setChecked(user.isOnline());
        if(user.getLocation() != null){
            locationTextView.setText(user.getLocation() + " - " + user.getCheckinDatetime());
        }

        String a = user.getCheckinDatetime();

        lastActivityTextView.setText(user.getLastActivityDatetime());

        if(user.getProfileImage() != null){
            Bitmap image = MensajerO.decodeBase64(user.getProfileImage());
            profileImage.setImageBitmap(image);
        }
    }

    public void handleUnexpectedError(int errorCode) {
        this.errorDialog = (new ExceptionsHandle(this, errorCode)).loadError();
        this.errorDialog.show();
    }
}
