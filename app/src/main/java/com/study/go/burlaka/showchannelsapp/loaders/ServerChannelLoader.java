package com.study.go.burlaka.showchannelsapp.loaders;

import android.content.Context;
import android.database.Cursor;

import com.study.go.burlaka.showchannelsapp.data.parse.ParseChannels;
import com.study.go.burlaka.showchannelsapp.data.repo.ChannelRepo;
import com.study.go.burlaka.showchannelsapp.retrofit.ApiFactory;
import com.study.go.burlaka.showchannelsapp.retrofit.ChannelService;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Operator on 22.09.2016.
 */
public class ServerChannelLoader extends ServerBaseLoader {
   // private static final String TAG = "GetChannel";

    public ServerChannelLoader(Context context) {
        super(context);
    }


    @Override
    protected Cursor apiCall() throws IOException, JSONException {
        ChannelRepo cr = new ChannelRepo();
        cr.delete();
        new ParseChannels( RequestToChannel ()).parseInsertDB();
        return null;
    }


    private ResponseBody RequestToChannel() throws IOException {
        ChannelService service = ApiFactory.getChannelService();
        Call<ResponseBody> call = service.getChannels();
     //   Log.d(TAG, "Channel loader became api call execute...");
        return call.execute().body();
    }
}