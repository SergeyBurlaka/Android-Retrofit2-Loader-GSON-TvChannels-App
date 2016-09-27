package com.study.go.burlaka.showchannelsapp.ui.show.programs.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Operator on 23.09.2016.
 */
public class TextPagerAdapter extends FragmentStatePagerAdapter {

    List<String> data;

    public TextPagerAdapter(FragmentManager fm, List<String> data) {
        super(fm);
        this.data = data;
    }


    @Override
    public Fragment getItem(int i) {

        Fragment fragment = new ItemFragment();

        Bundle args = new Bundle();
        args.putString(ItemFragment.ARG_TEXT, data.get(i));
        args.putInt(ItemFragment.ARG_POSITION, i+1);
        args.putInt(ItemFragment.ARG_COUNT, getCount());

        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return "Item " + (position + 1);
    }
}
