package com.study.go.burlaka.showchannelsapp.loaders.server.request;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.study.go.burlaka.showchannelsapp.data.parse.ParseCategory;
import com.study.go.burlaka.showchannelsapp.data.repo.CategoryRepo;
import com.study.go.burlaka.showchannelsapp.retrofit.ApiFactory;
import com.study.go.burlaka.showchannelsapp.retrofit.CategoryService;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Operator on 24.09.2016.
 */
public class CategoryLoader extends BaseLoader {
    private static final String TAG = "GetCategory";

    public CategoryLoader(Context context) {
        super(context);
    }


    @Override
    protected Cursor apiCall() throws IOException, JSONException {

        CategoryRepo cr = new CategoryRepo();
        cr.delete();
        new ParseCategory( RequestToCategory()).parseInsertDB();
       // RequestToCategory();
        return  cr.query() ;
        //return null;
    }


    private ResponseBody RequestToCategory() throws IOException {
        CategoryService service = ApiFactory.getCategoryService();
        Call<ResponseBody> call = service.getCategory();
        Log.d(TAG, "Category loader became api call execute...");
        //printResult (call.execute().body());
        return call.execute().body();
        //return null;
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