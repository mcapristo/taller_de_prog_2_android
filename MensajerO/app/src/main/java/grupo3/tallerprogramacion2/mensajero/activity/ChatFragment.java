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

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTO;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    SwipeRefreshLayout swipeLayout;
    protected ArrayList<String> contactsWithConvs = new ArrayList<String>();
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

    public void PopulateContacts(ArrayList<ConversationDTO> chats){
        this.contactsWithConvs = getUsersFromConversation(chats, myUsername);

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
                        intent.putExtra("contactUsername", (contactsWithConvs.get(position)).split("&")[0]);
                        intent.putExtra("contactFullName", (contactsWithConvs.get(position)).split("&")[1]);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getActivity(), "Ups! no connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private ArrayList<String> getUsersFromConversation(ArrayList<ConversationDTO> chats, String myUserName){
        ArrayList<String> otherUsernames = new ArrayList<String>();

        if(chats != null){
            for(int i=0; i < chats.size(); i++){
                if(chats.get(i).getUsername1().equals(myUserName)){
                    //deberia ser getName2()
                    String contact = chats.get(i).getUsername2() + "&" + chats.get(i).getUsername2();
                    /*String contactLocation = chats.get(i).getLocation();
                    if(contactLocation != ""){
                        contact = contact + " - " + contactLocation;
                    }*/
                    otherUsernames.add(contact);
                }else {
                    //deberia ser getName1()
                    String contact = chats.get(i).getUsername1() + "&" + chats.get(i).getUsername1();
                    /*String contactLocation = chats.get(i).getLocation();
                    if(contactLocation != ""){
                        contact = contact + " - " + contactLocation;
                    }*/
                    otherUsernames.add(contact);
                }
            }
        }
        return otherUsernames;
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
