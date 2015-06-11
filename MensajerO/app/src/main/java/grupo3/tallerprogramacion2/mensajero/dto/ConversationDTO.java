package grupo3.tallerprogramacion2.mensajero.dto;

public class ConversationDTO extends BaseDTO {

    private int total_messages;
    private String user1;
    private String user2;
    private String datetime;

    public String getUsername1() {
        return user1;
    }

    public String getUsername2() {
        return user2;
    }

    public int getTotal_messages() { return total_messages; }

    public String getDatetime() { return datetime; }
}
