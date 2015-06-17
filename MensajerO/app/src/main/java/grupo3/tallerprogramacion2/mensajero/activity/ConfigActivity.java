package grupo3.tallerprogramacion2.mensajero.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;

public class ConfigActivity extends ActionBarActivity {
    private EditText ipConfig;
    private AlertDialog saveOKDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        this.ipConfig = (EditText) findViewById(R.id.Config_f_IP);
        findViewById(R.id.Config_b_Exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfigActivity.this, LoginActivity.class));
            }
        });

        saveOKDialog = new AlertDialog.Builder(this).create();
    }

    public void saveConfigClick(View view) {
        // Store values at the time of the login attempt.
        String ip = this.ipConfig.getText().toString().trim();

        if (ip.length() > 0) {
            UrlConstants.setBaseUrl(ip);
            this.saveOK();
        }
    }

    public void saveOK() {
        this.saveOKDialog.setTitle("Guardar");
        this.saveOKDialog.setMessage("Guardado con exito!");
        this.saveOKDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        this.saveOKDialog.show();
    }
}
