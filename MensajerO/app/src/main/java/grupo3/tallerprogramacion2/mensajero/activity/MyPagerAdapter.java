package grupo3.tallerprogramacion2.mensajero.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.Locale;

/**
 * Created by uriel on 04/05/15.
 */
class MyPageAdapter extends FragmentPagerAdapter {

    ChatFragment chatFragment = new ChatFragment();
    ContactFragment contactFragment = new ContactFragment();

    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return chatFragment.newInstance("chats");
            case 1:
                return contactFragment.newInstance("contactos");
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Chats".toUpperCase(l);
            case 1:
                return "Contacts".toUpperCase(l);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}