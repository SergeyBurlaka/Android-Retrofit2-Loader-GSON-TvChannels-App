package com.study.go.burlaka.showchannelsapp.loaders;

import android.content.Context;
import android.database.Cursor;

import com.study.go.burlaka.showchannelsapp.data.parse.ParsePrograms;
import com.study.go.burlaka.showchannelsapp.retrofit.ApiFactory;
import com.study.go.burlaka.showchannelsapp.retrofit.ProgramService;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Operator on 22.09.2016.
 */
public class ServerProgramLoader extends ServerBaseLoader {

   // private static final String TAG_PROGRAM = "GetProgram";

    public ServerProgramLoader(Context context) {
        super(context);
    }


    @Override
    protected Cursor apiCall() throws IOException, JSONException {
        //Request to get program
        new ParsePrograms(RequestToProgram()).readJsonInsertDB();
        return null;
    }


    private ResponseBody RequestToProgram() throws IOException {
        ProgramService ps = ApiFactory.getProgramService();
        Call<ResponseBody> call = ps.getPrograms();
       // Log.d(TAG_PROGRAM, "apiCall() call.execute()");
        return call.execute().body();
    }
}


