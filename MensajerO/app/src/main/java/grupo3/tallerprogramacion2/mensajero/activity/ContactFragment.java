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

public class ContactFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    SwipeRefreshLayout swipeLayout;
    private ArrayList<String> contacts = new ArrayList<String>();
    private final RestService restService = RestServiceFactory.getRestService();
    private String myUsername;
    private String myToken;

    public static final ContactFragment newInstance(String message)
    {
        ContactFragment f = new ContactFragment();
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

        Bundle args = getActivity().getIntent().getExtras();
        this.myUsername= args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.myToken= args.getString(RestService.LOGIN_TOKEN);

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
        restService.getUsers(this.myUsername, this.myToken, this, getActivity());
    }

    public void PopulateContacts(ArrayList<UserDTO> allContacts) {
        ListView lst = (ListView) getView().findViewById(R.id.listView);

        for (int i = 0; i < allContacts.size(); i++) {
            if(!allContacts.get(i).getUsername().equals(myUsername)){
                this.contacts.add(allContacts.get(i).getUsername());
            }
        }
        LazyAdapter adapter = new LazyAdapter(getActivity(), this.contacts);

        swipeLayout.setRefreshing(false);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (contacts != null) {
                    if (position <= contacts.size()) {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        String contact = contacts.get(position);
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

    public void handleUnexpectedError(Exception error) {
        // TODO define what to show on unexpected errors.
    }
}
