package com.study.go.burlaka.showchannelsapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.study.go.burlaka.showchannelsapp.data.repo.CategoryRepo;
import com.study.go.burlaka.showchannelsapp.ui.category.recyclerview.CategoryChannelList;
import com.study.go.burlaka.showchannelsapp.ui.category.recyclerview.Channels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Operator on 25.09.2016.
 */
public class DBCategoryChannelsLoader extends AsyncTaskLoader <List<CategoryChannelList>> {

   // private static final String TAG ="GetCategoryDB" ;
    ArrayList <String> categoryList ;

    public DBCategoryChannelsLoader(Context context, ArrayList <String> categoryList) {
        super(context);
        this.categoryList = categoryList;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    public List<CategoryChannelList> loadInBackground() {
      //  Log.d(TAG, "in Begin category loader");
        for (String category: categoryList){
          //  Log.d(TAG, "My category:"+category);
        }

        CategoryChannelList categoryChannelList;
        List<CategoryChannelList> categoryChannelListList =new ArrayList<>();
        if (categoryList==null)return  new ArrayList<>();
        for (String category: categoryList){
          //  Log.d(TAG, "get from list: "+category);
            categoryChannelList = new CategoryChannelList( category,  getChannelsFromDB (category));
            categoryChannelListList.add(categoryChannelList);
        }
       return categoryChannelListList;
    }


    public ArrayList<Channels> getChannelsFromDB(String category) {
        CategoryRepo cr = new CategoryRepo();
        ArrayList<Channels> categoryChannels = new ArrayList<>();
        ArrayList <String> channels;
        Channels ingredient;
        channels = cr.getChannels4mCategory(category);

        for (String channel : channels ){
            ingredient = new Channels(channel);
            categoryChannels.add(ingredient);
        }

        return categoryChannels;
    }
}
