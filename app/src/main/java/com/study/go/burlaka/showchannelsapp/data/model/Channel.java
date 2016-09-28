package com.study.go.burlaka.showchannelsapp.data.model;

/**
 * Created by Operator on 22.09.2016.
 */
public class Channel {

    public static final String TAG = Channel.class.getSimpleName();
    public static final String TABLE = "Channel";
    //Labels Table Columns names
    public static final String KEY_CHANNEL_Id = "ChannelId";
    public static final String KEY_NAME = "Name";

    private String channelId;
    private String name;

    public String getChannelId() {
        return channelId;
    }


    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

}
