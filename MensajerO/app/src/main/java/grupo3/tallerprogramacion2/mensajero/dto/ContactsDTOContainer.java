package grupo3.tallerprogramacion2.mensajero.dto;

import java.util.ArrayList;

public class ContactsDTOContainer extends BaseDTO{

    private ArrayList<UserDTO> data;

    public ArrayList<UserDTO> getData() {
        return data;
    }

    public void setData(ArrayList<UserDTO> data) {
        this.data = data;
    }

}
