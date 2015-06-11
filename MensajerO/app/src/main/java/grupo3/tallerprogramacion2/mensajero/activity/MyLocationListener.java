package grupo3.tallerprogramacion2.mensajero.activity;

/**
 * Created by uriel on 09/06/15.
 */
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener{
    EditUserActivity mainActivity;

    public EditUserActivity getMainActivity(){
        return mainActivity;
    }

    public void setMainActivity(EditUserActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location loc){
        //Cada vez que cambia la localizacion del gps, la seteo en el mainActivity
        this.mainActivity.setLocation(loc);
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Este metodo se ejecuta cuando el GPS es desactivado
        //TextView messageTextView = (TextView) mainActivity.findViewById(R.id.messageTextView);
        //messageTextView.setText("GPS Desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Este metodo se ejecuta cuando el GPS es desactivado
        //TextView messageTextView = (TextView) mainActivity.findViewById(R.id.messageTextView);
        //messageTextView.setText("GPS Activado.. Buscando Ubicación");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
        // Este metodo se ejecuta cada vez que se detecta un cambio en el
        // status del proveedor de locaclizacion (GPS)
        // Los diferentes Status son:
        // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
        // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
        // espera que este disponible en breve
        // AVAILABLE -> Disponible
    }
}
