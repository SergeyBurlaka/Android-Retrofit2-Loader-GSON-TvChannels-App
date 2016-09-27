package com.study.go.burlaka.showchannelsapp.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.go.burlaka.showchannelsapp.R;
import com.study.go.burlaka.showchannelsapp.constant.MyConstants;
import com.study.go.burlaka.showchannelsapp.data.repo.ProgramRepo;
import com.study.go.burlaka.showchannelsapp.ui.show.programs.viewpager.TextPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Operator on 25.09.2016.
 */
public class ShowPrograms extends Fragment {


    private ViewPager mViewPager;
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.show_program_fragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextPagerAdapter mTextPagerAdapter = new TextPagerAdapter(
                getChildFragmentManager(), getMyData());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mTextPagerAdapter);
    }

    public List<String> getMyData() {

        ArrayList<String> data = new ArrayList<>();
        ProgramRepo pr = new ProgramRepo();
        data = pr.getChannelForShowing();
        if(!data.isEmpty()) saveStateApp ();
        return data;
    }

    private void saveStateApp() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MyConstants.MY_DB, getActivity().MODE_PRIVATE).edit();
        editor.putInt(MyConstants.IS_EMPTY, MyConstants.NOT_EMPTY);
        editor.commit();
    }
}
