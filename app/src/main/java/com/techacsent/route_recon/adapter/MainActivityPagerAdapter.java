package com.techacsent.route_recon.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.fragments.WelcomeFragment;

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    private static final int ID_DEFAULT = 0;
    private static final int ID_STYLED = 1;
    private static final int ID_CUSTOM_LAYOUT = 2;
    private static final int ID_CUSTOM_VIEW_HOLDER = 3;
    private static final int ID_CUSTOM_CONTENT = 4;


    private Context context;

    public MainActivityPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        int drawable = 0;
        switch (i) {
            case ID_DEFAULT:
                drawable = R.drawable.map;
                break;
            case ID_STYLED:
                drawable = R.drawable.create;
                break;
            case ID_CUSTOM_LAYOUT:
                drawable = R.drawable.routes;
                break;
            case ID_CUSTOM_VIEW_HOLDER:
                drawable = R.drawable.tracker;
                break;
        }
        return WelcomeFragment.newInstance(i, drawable);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
