package grupo3.tallerprogramacion2.mensajero.exceptions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.HashMap;

public class ExceptionsHandle {
    private AlertDialog errorDialog;
    private int errorCode;
    private HashMap<Integer, String> titles;
    private HashMap<Integer, String> messages;

    public ExceptionsHandle(Context context, int errorCode) {
        errorDialog = new AlertDialog.Builder(context).create();
        this.errorCode = errorCode;
        this.fillTitles();
        this.fillMessages();
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

    public String getDialogTitle(int errorCode) {
        String title = this.titles.get(errorCode);
        if(title != null) {
            return title;
        } else {
            return "Error desconocido";
        }
    }

    public String getDialogMessage(int errorCode) {
        String message = this.messages.get(errorCode);
        if(message != null) {
            return message;
        } else {
            return "Se produjo un error desconocido, por favor ponganse en contacto con el administrador =(";
        }
    }

    private void setTitle(int errorCode) {
        this.errorDialog.setTitle(getDialogTitle(errorCode));
    }

    private void setMessage(int errorCode) {
        this.errorDialog.setMessage(getDialogMessage(errorCode));
    }

    private void setButton() {
        this.errorDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

    private void fillTitles() {
        this.titles = new HashMap<Integer, String>();
        this.titles.put(1,"Username inválido");
        this.titles.put(2, "Password inválido");
        this.titles.put(3, "Token inválido");
        this.titles.put(4,"Error al envíar mensaje");
        this.titles.put(5, "No existe el perfil de usuario");
        this.titles.put(6, "Usuario existente");
        this.titles.put(7, "Falta password");
        this.titles.put(8, "Falta username");
        this.titles.put(9, "Json inválido");
        this.titles.put(1001, "Error de conexión");
    }

    private void fillMessages() {
        this.messages = new HashMap<Integer, String>();
        this.messages.put(1,"");
        this.messages.put(2, "El password que ingresó es incorrecto =(");
        this.messages.put(3, "");
        this.messages.put(4,"No se pudo enviar el mensaje al destinatario =(");
        this.messages.put(5, "No se pudo cargar el perfil del usuario =(");
        this.messages.put(6, "El usuario que intenta crear ya existe =(");
        this.messages.put(7, "");
        this.messages.put(8, "");
        this.messages.put(9, "No se puedo obtener los mensajes antiguos =(");
        this.messages.put(1001, "Ha ocurrido un error de conexión. Verifique si esta conectado a la red");
    }
}
