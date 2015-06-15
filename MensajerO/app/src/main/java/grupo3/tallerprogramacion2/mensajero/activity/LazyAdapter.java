package grupo3.tallerprogramacion2.mensajero.activity;

/**
 * Created by uriel on 04/05/15.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.aplication.MensajerO;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;

public class LazyAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> contacts;
    public LazyAdapter(Activity context, List<String> contactsUsernames ) {
        super(context, R.layout.list_item_event,contactsUsernames);

        // TODO Auto-generated constructor stub

        this.context=context;
        this.contacts=contactsUsernames;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item_event, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.contact);
        TextView txtUsername = (TextView) rowView.findViewById(R.id.username);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);

        String[] data = (contacts.get(position)).split("&");
        String username = data[0];
        String fullName = data[1];

        if(data.length > 2){
            String profileImage = data[2];
            Bitmap image = MensajerO.decodeBase64(profileImage);
            imageView.setImageBitmap(image);
        }

        txtTitle.setText(fullName);
        txtUsername.setText(username);
        return rowView;
    };
}