package com.study.go.burlaka.showchannelsapp.data.model;

/**
 * Created by Operator on 24.09.2016.
 */
public class Category {
    //public static final String TAG = Channel.class.getSimpleName();
    public static final String TABLE = "Category";

    //Labels Table Columns names
    public static final String KEY_CATEGORY = "Name";
    public static final String KEY_CHANNEL = "ChannelId";
    public static final String KEY_ID = "KeyId" ;

    private String channelId;
    private String categoryId;

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }



    public String getChannelId() {
        return channelId;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
