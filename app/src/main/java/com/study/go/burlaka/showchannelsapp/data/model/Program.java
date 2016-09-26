package com.study.go.burlaka.showchannelsapp.data.model;

/**
 * Created by Operator on 22.09.2016.
 */
public class Program {
    public static final String TABLE = "ProgramTable";
    //Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_CHANNEL_Id = "ChannelId";
    public static final String KEY_NAME = "ShowName";


    private String channelId;
    private String name;


    public void setName(String name) {
        this.name = name;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public String getChannelId() {
        return channelId;
    }
}
