package grupo3.tallerprogramacion2.mensajero.dto;

import java.util.ArrayList;

public class ChatMessageDTOContainer extends BaseDTO{

    private ArrayList<ChatMessageDTO> data;

    public ArrayList<ChatMessageDTO> getData() {
        return data;
    }

    public void setData(ArrayList<ChatMessageDTO> data) {
        this.data = data;
    }
}
