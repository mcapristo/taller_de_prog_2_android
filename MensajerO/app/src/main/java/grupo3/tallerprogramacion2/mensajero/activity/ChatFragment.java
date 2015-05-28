package grupo3.tallerprogramacion2.mensajero.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class ChatFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    SwipeRefreshLayout swipeLayout;
    protected ArrayList<String> contactsWithConvs = new ArrayList<String>();
    private final RestService restService = RestServiceFactory.getRestService();

    public static final ChatFragment newInstance(String message)
    {
        ChatFragment f = new ChatFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contact_fragment, container, false);
        //EventsInfoRetrivial retrieveEventsThread = new EventsInfoRetrivial(this);
        //retrieveEventsThread.execute(serverUrl);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

        });
        swipeLayout.setRefreshing(true);

        return rootView;
    }

    public void refresh() {
        //EventsInfoRetrivial retrieveEventsThread = new EventsInfoRetrivial(this);
        //retrieveEventsThread.execute(serverUrl);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getContactsFromDB();
        ListView lst = (ListView) getView().findViewById(R.id.listView);
    }

    private void getContactsFromDB(){
        Bundle args = getActivity().getIntent().getExtras();
        String myUserName= args.getString(RestService.LOGIN_RESPONSE_NAME);
        String myToken= args.getString(RestService.LOGIN_TOKEN);
        restService.getConversations(myUserName, myToken, this, getActivity());
    }

    public void PopulateContacts(ArrayList<ConversationDTO> chats){
        //TODO: get my username
        String myUsername = "";
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
                        //TODO: mandar a ConversationActivity.class en vez de LoginActivity.class
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        String contact = contactsWithConvs.get(position);
                        intent.putExtra("contactUsername", contact);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getActivity(), "Ups! parece que no hay conexion en este momento!",
                            Toast.LENGTH_LONG).show();
                    refresh();

                }
            }
        });

    }

    private ArrayList<String> getUsersFromConversation(ArrayList<ConversationDTO> chats, String myUserName){
        ArrayList<String> otherUsernames = new ArrayList<String>();

        for(int i=0; i < chats.size(); i++){
            if(chats.get(i).getUsername1() != myUserName){
                otherUsernames.add(chats.get(i).getUsername1());
            }else {
                otherUsernames.add(chats.get(i).getUsername2());
            }
        }
        return otherUsernames;
    }

    public void handleUnexpectedError(Exception error) {
        // TODO define what to show on unexpected errors.
    }
}
