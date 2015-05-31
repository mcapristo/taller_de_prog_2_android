package grupo3.tallerprogramacion2.mensajero.dto;

import java.util.ArrayList;

/**
 * Created by uriel on 31/05/15.
 */
public class ConversationDTOContainer extends BaseDTO{

    private ArrayList<ConversationDTO> data;

    public ArrayList<ConversationDTO> getData() {
        return data;
    }

    public void setData(ArrayList<ConversationDTO> data) {
        this.data = data;
    }
}
