package com.study.go.burlaka.showchannelsapp.server.loader.db;

import android.content.Context;
import android.database.Cursor;

import com.study.go.burlaka.showchannelsapp.data.repo.ProgramRepo;
import com.study.go.burlaka.showchannelsapp.server.loader.BaseLoader;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Operator on 23.09.2016.
 */
public class ChannelDBLoader extends BaseLoader {

    public ChannelDBLoader(Context context) {
        super(context);
    }

    @Override
    protected Cursor apiCall() throws IOException, JSONException {
        ProgramRepo pr = new ProgramRepo();
        return pr.getChannelCursor4View();
    }
}
