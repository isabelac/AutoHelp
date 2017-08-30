package ahv1.app.autohelpv2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ahv1.app.autohelpv2.fragment.ForumFragment;

/**
 * Created by bella on 27/07/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] titulosAba = { "TUTORIAIS", "FORUM" };

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0 :
                fragment = new ForumFragment();
                break;
            case 1 :
                fragment = new ForumFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titulosAba.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titulosAba[position];
    }
}
