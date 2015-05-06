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
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;

public class ChatFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    SwipeRefreshLayout swipeLayout;
    protected ArrayList<UserDTO> contactsWithConvs = new ArrayList<UserDTO>();

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
        //TODO: obtener los usuarios con conversaciones activas del server
        UserDTO uriel = new UserDTO();
        uriel.setUsername("UriKusnesov"); uriel.setName("Uriel");
        UserDTO ramiro = new UserDTO();
        ramiro.setUsername("RamiroDoi"); ramiro.setName("Ramiro");
        this.contactsWithConvs = new ArrayList<UserDTO>();
        this.contactsWithConvs.add(uriel);this.contactsWithConvs.add(ramiro);
        //this.contacts = server.obtenerUsuariosConConversacion();

        ListView lst = (ListView) getView().findViewById(R.id.listView);
        /*ArrayAdapter<Event> adapter = new ArrayAdapter<com.smule.entrepreneurevents.model.Event>(getActivity(),
                android.R.layout.simple_list_item_1, this.events);*/


        List<String> contactsWithConvUsernames = new ArrayList<String>();
        for (int i=0; i<this.contactsWithConvs.size();i++){
            contactsWithConvUsernames.add(this.contactsWithConvs.get(i).getUsername());
        }
        LazyAdapter adapter=new LazyAdapter(getActivity(), this.contactsWithConvs,contactsWithConvUsernames);

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
                        UserDTO contact;
                        contact = contactsWithConvs.get(position);
                        intent.putExtra("contactUsername", contact.getUsername());
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
}
