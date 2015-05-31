package grupo3.tallerprogramacion2.mensajero.dto;

import java.util.ArrayList;

/**
 * Created by uriel on 31/05/15.
 */
public class ContactsDTOContainer extends BaseDTO{

    private ArrayList<UserDTO> data;

    public ArrayList<UserDTO> getData() {
        return data;
    }

    public void setData(ArrayList<UserDTO> data) {
        this.data = data;
    }

}
