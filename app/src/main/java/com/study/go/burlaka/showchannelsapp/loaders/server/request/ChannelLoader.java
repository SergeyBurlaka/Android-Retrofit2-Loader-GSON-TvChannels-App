package com.study.go.burlaka.showchannelsapp.loaders.server.request;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.study.go.burlaka.showchannelsapp.retrofit.ApiFactory;
import com.study.go.burlaka.showchannelsapp.retrofit.ChannelService;
import com.study.go.burlaka.showchannelsapp.data.repo.ChannelRepo;
import com.study.go.burlaka.showchannelsapp.data.parse.ParseChannels;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Operator on 22.09.2016.
 */
public class ChannelLoader extends BaseLoader {
    private static final String TAG = "GetChannel";

    public ChannelLoader(Context context) {
        super(context);
    }

    @Override
    protected Cursor apiCall() throws IOException, JSONException {

        //Toast.makeText( context, "Channel loader on start!", Toast.LENGTH_SHORT).show();
        ChannelRepo cr = new ChannelRepo();
        cr.delete();
        new ParseChannels( RequestToChannel ()).parseInsertDB();
        return null;
        //return  cr.query() ;
    }


    private ResponseBody RequestToChannel() throws IOException {
        ChannelService service = ApiFactory.getChannelService();
        Call<ResponseBody> call = service.getChannels();

        Log.d(TAG, "Channel loader became api call execute...");
        return call.execute().body();
        //printResult (channelResult);
    }


    private void printResult( ResponseBody channelResult) {
        try {
            Log.d(TAG, channelResult.string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }
}