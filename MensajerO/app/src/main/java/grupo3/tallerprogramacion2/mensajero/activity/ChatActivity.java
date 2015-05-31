package grupo3.tallerprogramacion2.mensajero.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTO;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class ChatActivity extends Activity {
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private final RestService restService = RestServiceFactory.getRestService();
    private String myUsername;
    private String myToken;
    private String recpetorUsername;

    Intent intent;
    private boolean side = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle args = getIntent().getExtras();
        this.myUsername = args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.myToken= args.getString(RestService.LOGIN_TOKEN);
        this.recpetorUsername = args.getString("contactUsername");

        setTitle(this.recpetorUsername);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        listView = (ListView) findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_message);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.chatText);
        chatText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                /*if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }*/
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            Bundle args = getIntent().getExtras();
            String myUsername = args.getString(RestService.LOGIN_RESPONSE_NAME);
            String receptorUsername = args.getString(RestService.LOGIN_TOKEN);

            @Override
            public void onClick(View arg0) {
                ChatMessageDTO message = new ChatMessageDTO(side, chatText.getText().toString(), this.myUsername, this.receptorUsername);
                chatArrayAdapter.add(message);
                chatText.setText("");
                side = !side;
                sendMessageToServer(message);
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private void sendMessageToServer(ChatMessageDTO message){
        //TODO: send to server
        restService.sendMessage(myToken, message, this);
    }
}