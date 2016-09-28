package com.study.go.burlaka.showchannelsapp.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.study.go.burlaka.showchannelsapp.R;
import com.study.go.burlaka.showchannelsapp.data.model.Category;
import com.study.go.burlaka.showchannelsapp.data.repo.CategoryRepo;
import com.study.go.burlaka.showchannelsapp.loaders.DBCategoryChannelsLoader;
import com.study.go.burlaka.showchannelsapp.ui.category.recyclerview.CategoryChannelList;
import com.study.go.burlaka.showchannelsapp.ui.category.recyclerview.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Operator on 25.09.2016.
 */
public class ShowCategory extends Fragment  {

    private static final String TAG = "GetCategoryDB";
    private MyAdapter mAdapter;
    private ArrayList<String> category;
    private RecyclerView recyclerView;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.show_category_fragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        startGettingData ();
    }

    private void startGettingData() {
       // Toast.makeText(getActivity(), "startGettingData", Toast.LENGTH_SHORT).show();
        new GetCategoryAsyncTaskRunner().execute();
    }

    /*
    *     Override loaders methods
     * */
    private LoaderManager.LoaderCallbacks<List<CategoryChannelList>> mMyCallback = new  LoaderManager.LoaderCallbacks <List<CategoryChannelList>>(){
        @Override
        public Loader<List<CategoryChannelList>> onCreateLoader(int id, Bundle bundle) {
            switch (id) {
                case R.id.category_channel_db_loader:
                    return new DBCategoryChannelsLoader(getContext(),category );
                default:
                    return null;
            }
        }

        @Override
        public void onLoadFinished(Loader<List<CategoryChannelList>> loader, final List<CategoryChannelList> categoryChannelLists) {
            switch ( loader.getId()) {
                case R.id.category_channel_db_loader:
                    createRecycleViewCategory (categoryChannelLists);
                    break;
            }
        }

        private void createRecycleViewCategory(final List<CategoryChannelList> categoryChannelLists) {
            mAdapter = new MyAdapter(getActivity(), categoryChannelLists);
            mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                @Override
                public void onListItemExpanded(int position) {
                    CategoryChannelList expandedCategoryChannelList = categoryChannelLists.get(position);



                }

                @Override
                public void onListItemCollapsed(int position) {
                    CategoryChannelList collapsedCategoryChannelList = categoryChannelLists.get(position);



                }
            });
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        @Override
        public void onLoaderReset(Loader<List<CategoryChannelList>> loader) {

        }
    };

    /*
    *     AsynTask for get category
     * */
    private class GetCategoryAsyncTaskRunner extends AsyncTask<String, String , ArrayList<String>> {

        @Override
        protected  ArrayList <String> doInBackground(String... params) {
            CategoryRepo cr = new CategoryRepo();
            ArrayList<String> category = cr.getCategory();
          //  Log.d(TAG, "InBackground begin to read cursor.. " );
            readCategory(cr.query());
           // Log.d(TAG, "End! " );
            return category;
        }


        @Override
        protected void onPostExecute(ArrayList <String> categoryList) {
            category = categoryList;
            getActivity().getLoaderManager().initLoader(R.id.category_channel_db_loader, Bundle.EMPTY,   mMyCallback);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
    }


    private void readCategory(Cursor data) {
        if (data.moveToFirst()) {
            int i = 0;
            do {
                i++;
                String programName = data.getString(data.getColumnIndex(Category.KEY_CATEGORY));
                String channel = data.getString(data.getColumnIndex(Category.KEY_CHANNEL));
               // Log.d(TAG, "Category: " + programName+" Channel "+channel+" Count: "+i);
            } while (data.moveToNext());
        }
    }
}