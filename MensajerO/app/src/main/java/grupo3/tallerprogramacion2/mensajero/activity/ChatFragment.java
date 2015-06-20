package grupo3.tallerprogramacion2.mensajero.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.aplication.MensajerO;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    SwipeRefreshLayout swipeLayout;
    protected ArrayList<String> contactsWithConvs = new ArrayList<String>();
    protected ArrayList<ConversationDTO> convs = new ArrayList<ConversationDTO>();
    private final RestService restService = RestServiceFactory.getRestService();
    private String myUsername;
    private String myToken;

    public static final ChatFragment newInstance(String message)
    {
        ChatFragment f = new ChatFragment();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contact_fragment, container, false);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getContactsFromDB();
            }

        });
        swipeLayout.setRefreshing(true);

        Bundle args = getActivity().getIntent().getExtras();
        this.myUsername = args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.myToken= args.getString(RestService.LOGIN_TOKEN);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getContactsFromDB();
    }

    private void getContactsFromDB(){
        restService.getConversations(this.myUsername, this.myToken, this, getActivity());
    }

    public void PopulateContacts(ArrayList<UserDTO> users){
        this.contactsWithConvs = getUsersFromConversation(convs, myUsername, users);
        ListView lst = (ListView) getView().findViewById(R.id.listView);

        LazyAdapter adapter=new LazyAdapter(getActivity(), this.contactsWithConvs);

        swipeLayout.setRefreshing(false);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (contactsWithConvs != null) {
                    if (position <= contactsWithConvs.size()) {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        String contact = (contactsWithConvs.get(position)).split("&")[0];
                        intent.putExtra(RestService.LOGIN_RESPONSE_NAME, myUsername);
                        intent.putExtra(RestService.LOGIN_TOKEN, myToken);
                        intent.putExtra(RestService.CHAT_RECEPTOR_USERNAME, (contactsWithConvs.get(position)).split("&")[0]);
                        intent.putExtra(RestService.CHAT_RECEPTOR_FULLNAME, (contactsWithConvs.get(position)).split("&")[1]);
                        intent.putExtra(RestService.CHAT_RECEPTOR_LOCATION, (contactsWithConvs.get(position)).split("&")[2]);
                        intent.putExtra(RestService.CHAT_RECEPTOR_STATE, (contactsWithConvs.get(position)).split("&")[3]);
                        if((contactsWithConvs.get(position)).split("&").length > 4){
                            intent.putExtra(RestService.LOGIN_IMAGE, (contactsWithConvs.get(position)).split("&")[4]);
                        }
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getActivity(), "Ups! no connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void getAllUsers(ArrayList<ConversationDTO> conversations){
        this.convs = conversations;
        restService.getUsers(myUsername, myToken, this, this.getActivity());
    }

    private ArrayList<String> getUsersFromConversation(ArrayList<ConversationDTO> chats, String myUserName, ArrayList<UserDTO> users){
        ArrayList<String> otherUsernames = new ArrayList<String>();
        ArrayList<String> onlineUsernames = new ArrayList<String>();
        ArrayList<String> offlineUsernames = new ArrayList<String>();

        if(chats != null){
            for(int i=0; i < chats.size(); i++){
                UserDTO user = new UserDTO();
                if(chats.get(i).getUsername1().equals(myUserName)){
                    for(int j=0; j < users.size(); j++){
                        if(users.get(j).getUsername().equals(chats.get(i).getUsername2())){
                            user = users.get(j);
                        }
                    }
                    String contact = loadContact(user);

                    if(user.isOnline()){
                        onlineUsernames.add(contact);
                    }else{
                        offlineUsernames.add(contact);
                    }
                }else {
                    for(int j=0; j < users.size(); j++){
                        if(users.get(j).getUsername().equals(chats.get(i).getUsername1())){
                            user = users.get(j);
                        }
                    }
                    String contact = loadContact(user);

                    if(user.isOnline()){
                        onlineUsernames.add(contact);
                    }else {
                        offlineUsernames.add(contact);
                    }
                }
            }
        }
        otherUsernames.addAll(onlineUsernames);
        otherUsernames.addAll(offlineUsernames);
        return otherUsernames;
    }

    public String loadContact(UserDTO user){
        String contact = user.getUsername() + "&" + user.getName();
        String contactLocation = user.getLocation();
        if(contactLocation != null){
            contact = contact + "&" + contactLocation;
        }else{
            contact = contact + "&" + "";
        }
        if(user.isOnline()){
            contact = contact + "&" + "(Conectado)";
        }else{
            contact = contact + "&" + "(No Conectado)";
        }
        String profileImage = user.getProfileImage();
        contact = contact + "&" + profileImage;

        return contact;
    }

    public void handleUnexpectedError(Exception error) {
        // TODO define what to show on unexpected errors.
    }

    public void refresh(String username, String token){
        this.myToken = token;
        this.myUsername = username;
        getContactsFromDB();
    }
}
