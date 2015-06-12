package grupo3.tallerprogramacion2.mensajero.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import grupo3.tallerprogramacion2.mensajero.R;
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
    private Location location;
    private LocationManager mlocManager;
    private MyLocationListener mlocListener;
    private String dir;
    private AlertDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Bundle args = getIntent().getExtras();
        this.username = args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.token = args.getString(RestService.LOGIN_TOKEN);
        this.fullName = args.getString(RestService.LOGIN_FULL_NAME);
        this.password = args.getString(RestService.LOGIN_PASSWORD);

        UserDTO user = new UserDTO();
        user.setName(this.fullName);
        user.setPassword(this.password);

        this.completeField(user);
    }

    public void completeField(UserDTO user){
        EditText fullnameEditText = (EditText)findViewById(R.id.fullnameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
        fullnameEditText.setText(user.getName());
        passwordEditText.setText(user.getPassword());
        locationTextView.setText(user.getLocation());
    }

    public void cancelButtonOnClick(View v){
        finish();
    }

    public void saveButtonOnClick(View v){
        UserDTO modifiedUser = new UserDTO();
        EditText fullnameEditText = (EditText)findViewById(R.id.fullnameEditText);
        EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        TextView locationTextView = (TextView)findViewById(R.id.lastLocation);
        String newFullname = fullnameEditText.getText().toString();
        String newPassword = passwordEditText.getText().toString();
        String newLocation = locationTextView.getText().toString();
        modifiedUser.setUsername(username);
        modifiedUser.setPassword(newPassword);
        modifiedUser.setName(newFullname);
        modifiedUser.setLocation(newLocation);
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
            mlocListener = new MyLocationListener();
            mlocListener.setMainActivity(this);
            Location loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setLocation(loc);
            //mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) mlocListener);
        }
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

                    //mlocManager.removeUpdates((LocationListener) mlocListener);

                    //send to server
                    UserDTO modifiedUser = new UserDTO();
                    modifiedUser.setUsername(username);
                    modifiedUser.setLocation(dir);
                    restService.updateUser(username, token, modifiedUser, this);
                }
            } catch (IOException e) {

            }
        }
    }

}
