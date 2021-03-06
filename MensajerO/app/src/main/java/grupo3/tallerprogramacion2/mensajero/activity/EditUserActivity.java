package grupo3.tallerprogramacion2.mensajero.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.aplication.MensajerO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.exceptions.ExceptionsHandle;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class EditUserActivity extends ActionBarActivity {

    private final RestService restService = RestServiceFactory.getRestService();
    private String username;
    private String token;
    private String fullName;
    private String password;
    private String lastLocation;
    private String profileImage;
    private Location location;
    private LocationManager mlocManager;
    private MyLocationListener mlocListener;
    private String dir;
    private AlertDialog errorDialog;
    private AlertDialog saveOKDialog;
    Timer timer;
    MyTimerTask myTimerTask;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int TAKE_OR_PICK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveOKDialog = new AlertDialog.Builder(this).create();
        setContentView(R.layout.activity_edit_user);
        ImageView profileImage = (ImageView)findViewById(R.id.profileImage);
        profileImage.setDrawingCacheEnabled(true);

        Bundle args = getIntent().getExtras();
        this.username = args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.token = args.getString(RestService.LOGIN_TOKEN);
        this.password = args.getString(RestService.LOGIN_PASSWORD);

        restService.getUser(username, token, this);
    }

    public void populateData(UserDTO user){
        this.completeField(user);
    }

    public void completeField(UserDTO user){
        EditText fullnameEditText = (EditText)findViewById(R.id.fullnameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
        CheckBox online = (CheckBox)findViewById(R.id.onlineCheckBox);
        ImageView profileImage = (ImageView)findViewById(R.id.profileImage);
        fullnameEditText.setText(user.getName());
        passwordEditText.setText(password);
        locationTextView.setText(user.getLocation());
        online.setChecked(user.isOnline());

        if(user.getProfileImage() != null){
            Bitmap image = MensajerO.decodeBase64(user.getProfileImage());
            profileImage.setImageBitmap(image);
        }
    }

    public void cancelButtonOnClick(View v){
        finish();
    }

    public void saveButtonOnClick(View v){
        UserDTO modifiedUser = new UserDTO();
        EditText fullnameEditText = (EditText)findViewById(R.id.fullnameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
        CheckBox online = (CheckBox)findViewById(R.id.onlineCheckBox);
        ImageView profileImage = (ImageView)findViewById(R.id.profileImage);
        String newFullname = fullnameEditText.getText().toString();
        String newPassword = passwordEditText.getText().toString();
        String newLocation = locationTextView.getText().toString();
        String image = MensajerO.encodeTobase64(profileImage.getDrawingCache());
        boolean isOnline = online.isChecked();
        modifiedUser.setUsername(username);
        modifiedUser.setPassword(newPassword);
        modifiedUser.setName(newFullname);
        modifiedUser.setLocation(newLocation);
        modifiedUser.setProfileImage(image);
        modifiedUser.setOnline(isOnline);
        restService.updateUser(username, token, modifiedUser, this);
    }

    public void handleResponse(){
        saveOK();
    }

    public void handleUnexpectedError(int errorCode) {
        this.errorDialog = (new ExceptionsHandle(this, errorCode)).loadError();
        this.errorDialog.show();
    }

    public void noCompartirClick(View v){
        TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
        locationTextView.setText("");

        //send to server
        UserDTO modifiedUser = new UserDTO();
        modifiedUser.setUsername(username);
        modifiedUser.setLocation("");
        restService.updateUser(username, token, modifiedUser, this);
    }

    public void compartirClick(View v){
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            String title = "GPS Desactivado";
            String message = "El GPS está desactivado. Para buscar su ubicación debe activarlo.";
            this.errorDialog = (new ExceptionsHandle(this, 0)).loadError(title, message);
            this.errorDialog.show();
        }
        else if (!hasConection(this)) {
            String title = "Sin conexión";
            String message = "No tiene una conexión a internet disponible. Para buscar su ubicación debe activar alguna conexión.";
            this.errorDialog = (new ExceptionsHandle(this, 0)).loadError(title, message);
            this.errorDialog.show();
        }
        else
        {
            TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
            locationTextView.setText("");
            mlocListener = new MyLocationListener();
            mlocListener.setMainActivity(this);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) mlocListener);
            showProgress(true);

            if(timer != null){
                timer.cancel();
            }

            timer = new Timer();
            myTimerTask = new MyTimerTask();

            timer.schedule(myTimerTask, 10000, 10000);
        }
    }

    public void setLastKnownLocation(){
        Location loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        setLocation(loc);
        timer.cancel();
    }

    public boolean hasConection(Context context) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    public void setLocation(Location loc){
        //Obtener el barrio a partir de la latitud y la longitud (necesita conectividad a internet)
        mlocManager.removeUpdates((LocationListener) mlocListener);
        if(loc != null && loc.getLatitude() !=0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(),1);

                if(!list.isEmpty()){
                    Address address = list.get(0);
                    String dir = address.getSubLocality();

                    if(dir != null){

                    }else if (address.getThoroughfare() != null && address.getSubThoroughfare() != null){
                        dir = address.getThoroughfare() + " " + address.getSubThoroughfare();
                    }else if(address.getLocality() != null){
                        dir = address.getLocality();
                    }

                    this.location = loc;
                    TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
                    locationTextView.setText(dir);

                    //send to server
                    UserDTO modifiedUser = new UserDTO();
                    modifiedUser.setUsername(username);
                    modifiedUser.setLocation(dir);
                    restService.updateUser(username, token, modifiedUser, this);
                    showProgress(false);
                }
            } catch (IOException e) {

            }
        }
    }

    public void chooseOrTakePhoto(View view) {
        final View v = getLayoutInflater().inflate(R.layout.dialog_edititem_photo, null);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        b.setView(v);
        b.setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                TAKE_OR_PICK = 1;
                Intent takePictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        b.setNeutralButton("Existente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TAKE_OR_PICK = 2;
                Intent choosePictureIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(choosePictureIntent, RESULT_LOAD_IMAGE);
            }
        });

        b.setView(inflater.inflate(R.layout.dialog_edititem_photo, null));
        b.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // si saca una foto
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && null != data  && TAKE_OR_PICK == 1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView myPhotoImage = (ImageView) findViewById(R.id.profileImage);
            myPhotoImage.setImageBitmap(imageBitmap);

        // si elige de la galeria
        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data  && TAKE_OR_PICK == 2) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView myPhotoImage = (ImageView) findViewById(R.id.profileImage);
            myPhotoImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        final View mEditUserFormView = findViewById(R.id.edit_user_form);
        final View mProgressView = findViewById(R.id.edit_progress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void saveOK() {
        this.saveOKDialog.setTitle("Guardar");
        this.saveOKDialog.setMessage("Guardado con exito");
        this.saveOKDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        this.saveOKDialog.show();
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    setLastKnownLocation();
                }
            });
        }
    }
}
