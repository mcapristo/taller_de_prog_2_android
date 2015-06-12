package grupo3.tallerprogramacion2.mensajero.aplication;

import android.app.Application;

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
}
