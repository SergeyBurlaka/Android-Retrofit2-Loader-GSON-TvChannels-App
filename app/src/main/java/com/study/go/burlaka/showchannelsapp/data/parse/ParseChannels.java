package com.study.go.burlaka.showchannelsapp.data.parse;

import com.study.go.burlaka.showchannelsapp.data.model.Channel;
import com.study.go.burlaka.showchannelsapp.data.repo.ChannelRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.ResponseBody;

/**
 * Created by Operator on 22.09.2016.
 */
public class ParseChannels {

    private ResponseBody channelResult;
    private  final String CHANNEL_NAME = "name";
    private  final String CHANNEL_ID = "id";
    //private static final String TAG = "GetChannel";
    private ChannelRepo cr;
    private Channel channel;


    public ParseChannels(ResponseBody channelResult) {
        this.channelResult = channelResult;
    }


    public void parseInsertDB () throws IOException, JSONException {
        //Log.d(TAG,"on start parse" );
        cr = new ChannelRepo();
        channel = new Channel();
        JSONObject channelsJSON;
        JSONObject getChannelObj;

        channelsJSON = new JSONObject(channelResult.string());
        Iterator<String> iter = channelsJSON.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                getChannelObj = (JSONObject) channelsJSON.get(key);

                //Log.d(TAG,"insert channel_id" +getChannelObj.getString(CHANNEL_ID));
                channel.setChannelId(getChannelObj.getString(CHANNEL_ID));

                //Log.d(TAG,"insert channel_name" +getChannelObj.getString(CHANNEL_NAME));
                channel.setName(getChannelObj.getString(CHANNEL_NAME));

                //Insert to database channel table
                cr.insert(channel);

            } catch (JSONException e) {
                //Something went wrong!
                //Log.e(TAG,"Something went wrong! " +e.toString());
            }
        }
    }
}
