package grupo3.tallerprogramacion2.mensajero.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;
import grupo3.tallerprogramacion2.mensajero.factory.RestServiceFactory;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class HomeActivity extends ActionBarActivity implements ActionBar.TabListener {

    private final RestService restService = RestServiceFactory.getRestService();

    MyPageAdapter pageAdapter;
    ViewPager mViewPager;
    private String username;
    private String token;
    private String fullName;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("MensajerO");
        actionBar.setDisplayShowHomeEnabled(false);

        pageAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mViewPager.setAdapter(pageAdapter);

        Bundle args = getIntent().getExtras();
        this.username = args.getString(RestService.LOGIN_RESPONSE_NAME);
        this.token = args.getString(RestService.LOGIN_TOKEN);
        this.fullName = args.getString(RestService.LOGIN_FULL_NAME);
        this.password = args.getString(RestService.LOGIN_PASSWORD);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < pageAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(pageAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_editProfile) {
            Intent i = new Intent(this, EditUserActivity.class);
            i.putExtra(RestService.LOGIN_RESPONSE_NAME, username);
            i.putExtra(RestService.LOGIN_TOKEN, token);
            i.putExtra(RestService.LOGIN_PASSWORD, password);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_difusion) {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(RestService.LOGIN_RESPONSE_NAME, username);
            intent.putExtra(RestService.LOGIN_TOKEN, token);
            intent.putExtra(RestService.CHAT_RECEPTOR_USERNAME, "");
            intent.putExtra(RestService.CHAT_RECEPTOR_FULLNAME, "Difusion");
            startActivity(intent);
        }

        if (id == R.id.action_logOut) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(UrlConstants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(RestService.LOGIN_RESPONSE_NAME, "");
            editor.putString(RestService.LOGIN_PASSWORD, "");
            editor.commit();
            restService.logOut(username, token, this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    public void logOut(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}