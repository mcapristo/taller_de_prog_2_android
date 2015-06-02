package grupo3.tallerprogramacion2.mensajero.exceptions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ExceptionsHandle {
    private AlertDialog errorDialog;
    private int errorCode;

    public ExceptionsHandle(Context context, int errorCode) {
        errorDialog = new AlertDialog.Builder(context).create();
        this.errorCode = errorCode;
    }

    public AlertDialog loadError() {
        setTitle(this.errorCode);
        setMessage(this.errorCode);
        setButton();

        return(this.errorDialog);
    }

    private void setTitle(int errorCode) {
        switch (errorCode) {
            case 1:
                this.errorDialog.setTitle("Invalid User error");
                break;

            case 2:
                this.errorDialog.setTitle("Invalid Password error ");
                break;

            case 3:
                this.errorDialog.setTitle("Invalid Token error");
                break;

            case 4:
                this.errorDialog.setTitle("Send message error");
                break;

            case 5:
                this.errorDialog.setTitle("User profile doesnt exists error");
                break;

            case 6:
                this.errorDialog.setTitle("User already exist");
                break;

            case 7:
                this.errorDialog.setTitle("No password error");
                break;

            case 8:
                this.errorDialog.setTitle("No username error");
                break;

            case 9:
                this.errorDialog.setTitle("Invalid json error");
                break;

            default:
                this.errorDialog.setTitle("Unhandle error");
                break;
        }
    }

    private void setMessage(int errorCode) {
        switch (errorCode) {
            case 1:
                this.errorDialog.setMessage("");
                break;

            case 2:
                this.errorDialog.setMessage("El password que ingresó es incorrecto =(");
                break;

            case 3:
                this.errorDialog.setMessage("");
                break;

            case 4:
                this.errorDialog.setMessage("");
                break;

            case 5:
                this.errorDialog.setMessage("");
                break;

            case 6:
                this.errorDialog.setMessage("El usuario que intenta crear ya existe =(");
                break;

            case 7:
                this.errorDialog.setMessage("");
                break;

            case 8:
                this.errorDialog.setMessage("");
                break;

            case 9:
                this.errorDialog.setMessage("");
                break;

            default:
                this.errorDialog.setMessage("Se produjo un error desconocido, por favor ponganse en contacto con el administrador =(");
                break;
        }
    }

    private void setButton() {
        this.errorDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
}
