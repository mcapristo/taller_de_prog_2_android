package grupo3.tallerprogramacion2.mensajero.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.aplication.MensajerO;
import grupo3.tallerprogramacion2.mensajero.dto.ChatMessageDTO;
import grupo3.tallerprogramacion2.mensajero.dto.ConversationDTO;
import grupo3.tallerprogramacion2.mensajero.exceptions.ExceptionsHandle;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class ChatActivity extends ActionBarActivity {
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private final RestService restService = RestServiceFactory.getRestService();
    private String myUsername;
    private String myToken;
    private String recpetorUsername;
    private String recpetorFullname;
    private String receptorLocation;
    private String receptorProfileImage;
    private String receptorState;

    private AlertDialog errorDialog;

    private boolean side = false;
    Timer timer;
    MyTimerTask myTimerTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle args = getIntent().getExtras();
        this.myUsername = args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.myToken= args.getString(RestService.LOGIN_TOKEN);
        this.recpetorUsername = args.getString(RestService.CHAT_RECEPTOR_USERNAME);
        this.recpetorFullname = args.getString(RestService.CHAT_RECEPTOR_FULLNAME);
        this.receptorState = args.getString(RestService.CHAT_RECEPTOR_STATE);
        this.receptorLocation = args.getString(RestService.CHAT_RECEPTOR_LOCATION);
        this.receptorProfileImage = args.getString(RestService.LOGIN_IMAGE);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        String title = this.recpetorFullname;
        if(this.receptorState != null){
            title = title + this.receptorState;
        }
        actionBar.setTitle(title);

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

        //define variables for button listener
        final String username = this.myUsername;
        final String receptor = this.recpetorUsername;
        final String token = this.myToken;
        final ChatActivity activity = this;
        buttonSend.setOnClickListener(new View.OnClickListener() {
            Bundle args = getIntent().getExtras();

            @Override
            public void onClick(View arg0) {
                ChatMessageDTO message = new ChatMessageDTO(false, chatText.getText().toString(), username, receptor);
                restService.sendMessage(token, message, activity);
                chatText.setText("");
            }
        });

        getMessages();

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

        if(timer != null){
            timer.cancel();
        }

        timer = new Timer();
        myTimerTask = new MyTimerTask();

        timer.schedule(myTimerTask, 1000, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_viewProfile) {
            Intent i = new Intent(this, UserDetailActivity.class);
            i.putExtra(RestService.LOGIN_RESPONSE_NAME, this.myUsername);
            i.putExtra(RestService.LOGIN_TOKEN, this.myToken);
            i.putExtra(RestService.CHAT_RECEPTOR_USERNAME, this.recpetorUsername);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getMessages(){
        restService.getMessages(this.myUsername, this.myToken, this.recpetorUsername, this);
    }

    public void LoadMessages(ArrayList<ChatMessageDTO> messages){
        for(int i=0; i < messages.size(); i++){
            ChatMessageDTO message = messages.get(i);
            boolean esViejo = false;
            for(int j=0; j < chatArrayAdapter.getCount(); j++){
                if(chatArrayAdapter.getItem(j).getId().equals(message.getId())){
                    esViejo = true;
                }
            }

            if(!esViejo){
                message.setLeft(message.getReceptor().equals(this.myUsername));
                chatArrayAdapter.add(message);
            }
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    getMessages();
                }
            });
        }
    }

    public void handleUnexpectedError(int errorCode) {
        this.errorDialog = (new ExceptionsHandle(this, errorCode)).loadError();
        this.errorDialog.show();
    }
}