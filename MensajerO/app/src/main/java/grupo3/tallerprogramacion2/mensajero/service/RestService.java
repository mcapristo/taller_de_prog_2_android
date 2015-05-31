package grupo3.tallerprogramacion2.mensajero.service;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import grupo3.tallerprogramacion2.mensajero.activity.ChatActivity;
import grupo3.tallerprogramacion2.mensajero.activity.ChatFragment;
import grupo3.tallerprogramacion2.mensajero.activity.ContactFragment;
import grupo3.tallerprogramacion2.mensajero.activity.CreateUserActivity;
import grupo3.tallerprogramacion2.mensajero.activity.HomeActivity;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;

public interface RestService {
    public final static String RESPONSE_CODE = "grupo3.tallerprogramacion2.mensajero.service.RestService.CODE";
    public final static String LOGIN_RESPONSE_NAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_RESPONSE_NAME";
    public final static String LOGIN_TOKEN = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_TOKEN";

    public void login(String username, String password, final LoginActivity context) throws JSONException;

    public void createUser(String userName, String fullName, String password, final CreateUserActivity context);

    public void getUsers(String userName, String token, final ContactFragment fragment, final FragmentActivity context);

    public void getConversations(String userName, String token, final ChatFragment fragment, final FragmentActivity context);

    public void sendMessage(String token, ChatMessageDTO message, final ChatActivity context);
}
