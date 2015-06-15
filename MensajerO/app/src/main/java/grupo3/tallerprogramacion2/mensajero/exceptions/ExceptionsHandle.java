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

    public AlertDialog loadError(String title, String message) {
        this.errorDialog.setTitle(title);
        this.errorDialog.setMessage(message);
        setButton();

        return(this.errorDialog);
    }

    private void setTitle(int errorCode) {
        switch (errorCode) {
            case 1:
                this.errorDialog.setTitle("Username inválido");
                break;

            case 2:
                this.errorDialog.setTitle("Password inválido");
                break;

            case 3:
                this.errorDialog.setTitle("Token inválido");
                break;

            case 4:
                this.errorDialog.setTitle("Error al envíar mensaje");
                break;

            case 5:
                this.errorDialog.setTitle("No existe el perfil de usuario");
                break;

            case 6:
                this.errorDialog.setTitle("Usuario existente");
                break;

            case 7:
                this.errorDialog.setTitle("Falta password");
                break;

            case 8:
                this.errorDialog.setTitle("Falta username");
                break;

            case 9:
                this.errorDialog.setTitle("Json inválido");
                break;

            default:
                this.errorDialog.setTitle("Error desconocido");
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
                this.errorDialog.setMessage("No se pudo enviar el mensaje al destinatario =(");
                break;

            case 5:
                this.errorDialog.setMessage("No se pudo cargar el perfil del usuario =(");
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
                this.errorDialog.setMessage("No se puedo obtener los mensajes antiguos =(");
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
