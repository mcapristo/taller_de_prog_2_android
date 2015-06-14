package grupo3.tallerprogramacion2.mensajero.aplication;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;

/**
 * Created by uriel on 11/06/15.
 */
public class MensajerO extends Application {
    private ArrayList<UserDTO> users;

    public ArrayList<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserDTO> users) {
        this.users = users;
    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
