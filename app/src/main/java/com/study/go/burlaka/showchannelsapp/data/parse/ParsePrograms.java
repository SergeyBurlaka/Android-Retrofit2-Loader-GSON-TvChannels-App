package com.study.go.burlaka.showchannelsapp.data.parse;

import android.util.JsonReader;
import android.util.Log;

import com.study.go.burlaka.showchannelsapp.data.model.Program;
import com.study.go.burlaka.showchannelsapp.data.repo.ProgramRepo;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.ResponseBody;

/**
 * Created by Operator on 22.09.2016.
 */
public class ParsePrograms {
    private ResponseBody programResult;
    private static final String TAG_PROGRAM = "GetProgram";
    private Program program;
    private ProgramRepo pr;

    public ParsePrograms (ResponseBody programResult) {
        this.programResult = programResult;
    }

    public void readJsonInsertDB () throws IOException, JSONException {
        program = new Program();
        pr = new ProgramRepo();

        Log.d(TAG_PROGRAM, "So, get string from file... " );
        //For updating data, must delete old value
        pr.delete();
        //Get json reader from input stream
        InputStream fis =  programResult.byteStream();
        Reader streamReader = new InputStreamReader( fis);
        JsonReader readerJson = new JsonReader(streamReader );
        readerJson.beginObject();

        //while (readerJson.hasNext()){
        String name = readerJson.nextName();
        Log.d (TAG_PROGRAM, "name json"+name);
        readChannels (readerJson);
        //}
        //readerJson.endObject();
        readerJson.close();

        Log.d(TAG_PROGRAM,"end read" );
    }


    private void readChannels(JsonReader readerJson) throws IOException {
        readerJson.beginObject();
        while (readerJson.hasNext()){
            String name = readerJson.nextName();
            Log.d (TAG_PROGRAM, "name json"+name);
            readProgram (readerJson);
        }
        readerJson.endObject();
    }


    private void readProgram(JsonReader readerJson) throws IOException {
        readerJson.beginObject();
        while ( readerJson.hasNext()){
            String name = readerJson.nextName();
            if (name.equals("date")) {
                Log.d (TAG_PROGRAM, "date"+readerJson.nextLong());

            } else if (name.equals("showID")) {
               // Log.d (TAG_PROGRAM, "showID"+readerJson.nextString());
                program.setChannelId(readerJson.nextString());

            } else if (name.equals("tvShowName") ) {
                //Log.d (TAG_PROGRAM, "tvShowName"+readerJson.nextString());
                program.setName(readerJson.nextString());
            }
            else {
                readerJson.skipValue();
            }
        }
        readerJson.endObject();
        pr.insert(program);
    }
}
