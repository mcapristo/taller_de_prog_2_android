package grupo3.tallerprogramacion2.mensajero.dto;

public class ChatMessageDTOSingleContainer extends BaseDTO {

    private ChatMessageDTO data;

    public ChatMessageDTO getData() {
        return data;
    }

    public void setData(ChatMessageDTO data) {
        this.data = data;
    }
}
