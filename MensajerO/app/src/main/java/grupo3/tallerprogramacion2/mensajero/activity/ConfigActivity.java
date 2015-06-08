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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
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
    */

    public void saveConfigClick(View view) {
        // Store values at the time of the login attempt.
        String ip = this.ipConfig.getText().toString().trim();

        if (ip.length() > 0) {
            UrlConstants.setBaseUrl(ip);
            this.saveOK();
        }
    }

    public void saveOK() {
        this.saveOKDialog.setTitle("Save");
        this.saveOKDialog.setMessage("Save success!");
        this.saveOKDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        this.saveOKDialog.show();
    }
}
