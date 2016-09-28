package com.study.go.burlaka.showchannelsapp.data.parse;

import com.study.go.burlaka.showchannelsapp.data.model.Category;
import com.study.go.burlaka.showchannelsapp.data.repo.CategoryRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.ResponseBody;

/**
 * Created by Operator on 24.09.2016.
 */
public class ParseCategory {

    private ResponseBody categoryResult;
    private static final String TAG = "GetCategory";
    private CategoryRepo cr;
    private Category category;


    public ParseCategory (ResponseBody channelResult) {
        this.categoryResult = channelResult;
    }


    public void parseInsertDB () throws IOException, JSONException {
        //Log.d(TAG,"on start parse" );
        cr = new CategoryRepo();
        category = new Category();
        JSONObject categoryJSON;
        categoryJSON = new JSONObject(categoryResult.string());
        Iterator<String> iter = categoryJSON.keys();

        while (iter.hasNext()) {
            //get category name
            String key = iter.next();
            category.setCategoryId(key);
            try {
                    iterateInCategory (categoryJSON,key, cr);
            } catch (JSONException e) {
                // Something went wrong!
             //   Log.e(TAG,"Something went wrong! " +e.toString());
            }
        }
    }


    private void iterateInCategory(JSONObject categoryJSON, String key, CategoryRepo cr) throws JSONException {
        JSONObject getCategoryObj;
        getCategoryObj = (JSONObject) categoryJSON.get(key);
        Iterator<String> iter = getCategoryObj.keys();

        while (iter.hasNext()) {
            //get channel name
            String channelKey = iter.next();
            category.setChannelId(channelKey);
           //set in db
            cr.insert(category);
        }
    }
}
