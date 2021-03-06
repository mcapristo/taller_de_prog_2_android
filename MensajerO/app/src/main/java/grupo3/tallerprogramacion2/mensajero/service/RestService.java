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
import grupo3.tallerprogramacion2.mensajero.activity.EditUserActivity;
import grupo3.tallerprogramacion2.mensajero.activity.HomeActivity;
import grupo3.tallerprogramacion2.mensajero.activity.InitActivity;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.activity.UserDetailActivity;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;

/**
 * Interfaz que define la interaccion con la rest API del servidor.
 */
public interface RestService {
    public final static String RESPONSE_CODE = "grupo3.tallerprogramacion2.mensajero.service.RestService.CODE";
    public final static String LOGIN_RESPONSE_NAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_RESPONSE_NAME";
    public final static String LOGIN_TOKEN = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_TOKEN";
    public final static String LOGIN_FULL_NAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_FULL_NAME";
    public final static String LOGIN_PASSWORD = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_PASSWORD";
    public final static String LOGIN_LOCATION = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_LOCATION";
    public final static String LOGIN_IMAGE = "grupo3.tallerprogramacion2.mensajero.service.RestService.LOGIN_IMAGE";
    public final static String CHAT_RECEPTOR_USERNAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.CHAT_RECEPTOR_USERNAME";
    public final static String CHAT_RECEPTOR_FULLNAME = "grupo3.tallerprogramacion2.mensajero.service.RestService.CHAT_RECEPTOR_FULLNAME";
    public final static String CHAT_RECEPTOR_LOCATION = "grupo3.tallerprogramacion2.mensajero.service.RestService.CHAT_RECEPTOR_LOCATION";
    public final static String CHAT_RECEPTOR_STATE = "grupo3.tallerprogramacion2.mensajero.service.RestService.CHAT_RECEPTOR_STATE";

    public void login(String username, String password, final LoginActivity context) throws JSONException;

    public void login(String username, String password, final InitActivity context) throws JSONException;

    public void logOut(String username, String token, final HomeActivity context);

    public void createUser(String userName, String fullName, String password, final CreateUserActivity context);

    public void getUsers(String userName, String token, final ContactFragment fragment, final FragmentActivity context);

    public void getUsers(String userName, String token, final ChatFragment fragment, final FragmentActivity context);

    public void getConversations(String userName, String token, final ChatFragment fragment, final FragmentActivity context);

    public void sendMessage(String token, ChatMessageDTO message, final ChatActivity context);

    public void getMessages(String userName, String token, String receptor, final ChatActivity context);

    public void updateUser(final String username, final String token, UserDTO modifiedUser, final EditUserActivity context);

    public void getUser(final String username, final String token, final EditUserActivity context);

    public void getUser(final String username, final String token, String receptorUsername, final UserDetailActivity context);
}
