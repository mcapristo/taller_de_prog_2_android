package grupo3.tallerprogramacion2.mensajero.activity;

/**
 * Created by uriel on 04/05/15.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;

public class LazyAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<UserDTO> contacts;
    public LazyAdapter(Activity context, ArrayList<UserDTO> contacts, List<String> contactsUsernames ) {
        super(context, R.layout.list_item_event,contactsUsernames);

        // TODO Auto-generated constructor stub

        this.context=context;
        this.contacts=contacts;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item_event, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.contact);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        txtTitle.setText(contacts.get(position).getName());
        //imageView.setImageResource(imgid[position]);
        return rowView;

    };
}