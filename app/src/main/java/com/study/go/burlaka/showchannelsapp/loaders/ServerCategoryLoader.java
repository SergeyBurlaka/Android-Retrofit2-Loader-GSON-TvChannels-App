package com.study.go.burlaka.showchannelsapp.loaders;

import android.content.Context;
import android.database.Cursor;

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
public class ServerCategoryLoader extends ServerBaseLoader {
   // private static final String TAG = "GetCategory";

    public ServerCategoryLoader(Context context) {
        super(context);
    }


    @Override
    protected Cursor apiCall() throws IOException, JSONException {
        CategoryRepo cr = new CategoryRepo();
        cr.delete();
        new ParseCategory( RequestToCategory()).parseInsertDB();
        return  cr.query() ;
    }


    private ResponseBody RequestToCategory() throws IOException {
        CategoryService service = ApiFactory.getCategoryService();
        Call<ResponseBody> call = service.getCategory();
      //  Log.d(TAG, "Category loader became api call execute...");
        //printResult (call.execute().body());
        return call.execute().body();
        //return null;
    }
}