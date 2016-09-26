package com.study.go.burlaka.showchannelsapp.loaders.server.request;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.study.go.burlaka.showchannelsapp.retrofit.ApiFactory;
import com.study.go.burlaka.showchannelsapp.retrofit.ProgramService;
import com.study.go.burlaka.showchannelsapp.data.repo.ProgramRepo;
import com.study.go.burlaka.showchannelsapp.data.parse.ParsePrograms;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Operator on 22.09.2016.
 */
public class ProgramLoader extends BaseLoader {

    private  final String DATE = "2016Aug01";
    private static final String TAG_PROGRAM = "GetProgram";
    private File futureStudioIconFile;
    private JSONObject programsJson;

    // String pathToApkFolder = App.getContext().getExternalFilesDir(null) + File.separator + "File_name" + ".apk";

    public ProgramLoader (Context context) {
        super(context);
    }

    @Override
    protected Cursor apiCall() throws IOException, JSONException {
        //Request to get program
        new ParsePrograms(RequestToProgram()).readJsonInsertDB();
        ProgramRepo pr = new ProgramRepo();
        //return pr.query();
        return null;
    }


    private ResponseBody RequestToProgram() throws IOException {
        ProgramService ps = ApiFactory.getProgramService();
        Call<ResponseBody> call = ps.getPrograms();
        Log.d(TAG_PROGRAM, "apiCall() call.execute()");
        return call.execute().body();
    }

}


