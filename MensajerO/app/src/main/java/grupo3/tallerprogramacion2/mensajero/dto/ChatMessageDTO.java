package grupo3.tallerprogramacion2.mensajero.dto;

/**
 * Created by uriel on 30/05/15.
 */
public class ChatMessageDTO {
    private boolean left;
    private String message;
    private String emisor;
    private String receptor;

    public ChatMessageDTO(boolean left, String message, String emisor, String receptor) {
        this.left = left;
        this.message = message;
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public boolean getLeft() { return left; }
    public String getMessage() { return message; }
    public String getEmisor() { return emisor; }
    public String getReceptor() { return receptor; }
}
