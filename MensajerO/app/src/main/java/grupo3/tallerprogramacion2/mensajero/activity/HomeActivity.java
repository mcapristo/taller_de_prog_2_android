package grupo3.tallerprogramacion2.mensajero.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import grupo3.tallerprogramacion2.mensajero.R;
import grupo3.tallerprogramacion2.mensajero.service.RestService;

public class HomeActivity extends ActionBarActivity implements ActionBar.TabListener {

    MyPageAdapter pageAdapter;
    ViewPager mViewPager;
    private String username;
    private String token;

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

        if (id == R.id.action_viewProfile) {
            Intent i = new Intent(this, UserDetailActivity.class);
            i.putExtra(RestService.LOGIN_RESPONSE_NAME, username);
            i.putExtra(RestService.LOGIN_TOKEN, token);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_editProfile) {
            Intent i = new Intent(this, EditUserActivity.class);
            i.putExtra(RestService.LOGIN_RESPONSE_NAME, username);
            i.putExtra(RestService.LOGIN_TOKEN, token);
            startActivity(i);
            return true;
        }

        if (id == R.id.action_exit) {
            finish();
            return true;
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
}