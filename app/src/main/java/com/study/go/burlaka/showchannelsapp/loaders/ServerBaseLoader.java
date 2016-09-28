package com.study.go.burlaka.showchannelsapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Operator on 22.09.2016.
 */
public abstract class ServerBaseLoader extends AsyncTaskLoader<Cursor> {

    private static final String TAG = "BaseLoaderTAG";


    public ServerBaseLoader(Context context) {
        super(context);
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    public Cursor loadInBackground() {
        try {
            return apiCall();
        } catch (IOException e) {
            e.printStackTrace();
         //   Log.e(TAG, "Error in loader!"+e.toString());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
          //  Log.e(TAG, "Error in loader!"+e.toString());
            return null;
        }
    }


    protected abstract Cursor apiCall() throws IOException, JSONException;
}
