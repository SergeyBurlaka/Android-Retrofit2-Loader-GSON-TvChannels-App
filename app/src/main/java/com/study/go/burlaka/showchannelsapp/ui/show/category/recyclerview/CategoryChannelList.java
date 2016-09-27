package com.study.go.burlaka.showchannelsapp.ui.show.category.recyclerview;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by Operator on 25.09.2016.
 */
public class CategoryChannelList implements ParentListItem {


    private String mName;
    private List<Channels> mChannelses;

    public CategoryChannelList(String name, List<Channels> channelses) {
        mName = name;
        mChannelses = channelses;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<?> getChildItemList() {
        return mChannelses;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}