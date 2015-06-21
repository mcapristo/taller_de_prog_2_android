package grupo3.tallerprogramacion2.mensajero.dto;

public class ChatMessageDTO {
    private String id;
    private boolean left;
    private String body;
    private String emisor;
    private String receptor;

    public ChatMessageDTO(boolean left, String message, String emisor, String receptor) {
        this.left = left;
        this.body = message;
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public String getId() { return id; }
    public boolean getLeft() { return left; }
    public void setLeft(boolean data) { this.left = data; }
    public String getBody() { return body; }
    public String getEmisor() { return emisor; }
    public String getReceptor() { return receptor; }
}
