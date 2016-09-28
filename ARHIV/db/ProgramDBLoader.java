package com.study.go.burlaka.showchannelsapp.server.loader.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.study.go.burlaka.showchannelsapp.server.loader.BaseLoader;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Operator on 23.09.2016.
 */
public class ProgramDBLoader extends BaseLoader {

    public ProgramDBLoader(Context context) {
        super(context);
    }
    private static final String CH_TAG = "get_Channel";
    @Override
    protected Cursor apiCall() throws IOException, JSONException {

        Log.d(CH_TAG, "ProgramDBLoader starting" );

        return null;
    }
}
