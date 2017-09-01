package com.skeleton.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.MyApplication;
import com.skeleton.R;
import com.skeleton.fragment.BookingFragment;
import com.skeleton.fragment.HomeFragment;
import com.skeleton.fragment.ProfileFragment;
import com.skeleton.fragment.WeekFragment;

import static com.skeleton.constant.AppConstant.TAB_AVAILABILITY;
import static com.skeleton.constant.AppConstant.TAB_BOOKING;
import static com.skeleton.constant.AppConstant.TAB_COUNT;
import static com.skeleton.constant.AppConstant.TAB_HOME;
import static com.skeleton.constant.AppConstant.TAB_PROFILE;

/**
 * +++++++++++++++++++++++++++++++++
 * ++++++++++Click labs ++++++++++++
 * +++++++++++++++++++++++++++++++++
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private int[] tabImages = {R.drawable.tab_icon_home, R.drawable.tab_icon_booking
            , R.drawable.tab_icon_status, R.drawable.tab_icon_profile};
    private int[] tabText = {R.string.tab_home, R.string.tab_booking
            , R.string.tab_status, R.string.tab_profile};

    /**
     * @param fm FragmentManager
     */
    public HomeViewPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        Fragment fragment = null;
        switch (position) {
            case TAB_HOME:
                fragment = HomeFragment.newInstance("", "");
                break;
            case TAB_BOOKING:
                fragment = BookingFragment.newInstance("", "");
                break;
            case TAB_AVAILABILITY:
                fragment = WeekFragment.newInstance();
                break;
            case TAB_PROFILE:
                fragment = ProfileFragment.newInstance("", "");
                break;
            default:
                fragment = HomeFragment.newInstance("", "");
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    /**
     * @param position position index
     * @return View at position
     */
    public View getTabIcon(final int position) {
        View view = LayoutInflater.from(MyApplication.getAppContext())
                .inflate(R.layout.layout_hometab_icon, null, false);
        ImageView view1 = (ImageView) view.findViewById(R.id.tab_icon);
        TextView view2 = (TextView) view.findViewById(R.id.tab_text);
        view1.setImageResource(tabImages[position]);
        view2.setText(tabText[position]);
        return view;
    }
}
