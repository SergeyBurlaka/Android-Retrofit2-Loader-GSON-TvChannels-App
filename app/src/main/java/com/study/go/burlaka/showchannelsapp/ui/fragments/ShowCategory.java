package com.study.go.burlaka.showchannelsapp.ui.fragments;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.study.go.burlaka.showchannelsapp.R;
import com.study.go.burlaka.showchannelsapp.data.model.Category;
import com.study.go.burlaka.showchannelsapp.data.repo.CategoryRepo;
import com.study.go.burlaka.showchannelsapp.loaders.database.GetCategoryChannels;
import com.study.go.burlaka.showchannelsapp.ui.show.category.recyclerview.MyAdapter;
import com.study.go.burlaka.showchannelsapp.ui.show.category.recyclerview.Recipe;

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
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
       // recyclerView.setAdapter(mAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        startGettingData ();
    }



    private void startGettingData() {
        Toast.makeText(getActivity(), "startGettingData", Toast.LENGTH_SHORT).show();
        new GetCategoryAsyncTaskRunner().execute();

    }






    /*
    *     Override loaders methods
     * */

    private LoaderManager.LoaderCallbacks<List<Recipe>> mMyCallback = new  LoaderManager.LoaderCallbacks <List<Recipe>>(){


        @Override
        public Loader<List<Recipe>> onCreateLoader(int id, Bundle bundle) {
            switch (id) {
                case R.id.category_channel_db_loader:
                    // Log.v(TAG, "onCreateLoader"+"MainActivity");
                    Toast.makeText(getActivity(), "on loader create", Toast.LENGTH_SHORT).show();
                    return new GetCategoryChannels(getContext(),category );

                default:
                    return null;
            }
        }

        @Override
        public void onLoadFinished(Loader<List<Recipe>> loader, final List<Recipe> recipes) {

            switch ( loader.getId()) {
                case R.id.category_channel_db_loader:
                    Toast.makeText(getActivity(), "on finish loader", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), "name " + recipes.toString(),Toast.LENGTH_SHORT).show();


                   //TODO: 26.09.2016 must build interface of category and channels

                    mAdapter = new MyAdapter(getActivity(), recipes);
                    mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                        @Override
                        public void onListItemExpanded(int position) {
                            Recipe expandedRecipe = recipes.get(position);



                        }

                        @Override
                        public void onListItemCollapsed(int position) {
                            Recipe collapsedRecipe = recipes.get(position);



                        }
                    });
                   // recyclerView.swapAdapter( mAdapter, false);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    break;

            }
        }

        @Override
        public void onLoaderReset(Loader<List<Recipe>> loader) {

        }
    };





    /*
    *     AsynTask for get category
     * */


    private class GetCategoryAsyncTaskRunner extends AsyncTask<String, String , ArrayList<String>> {



        @Override
        protected  ArrayList <String> doInBackground(String... params) {
            // TODO: 26.09.2016 ---/**---  create request to get uniq category
            CategoryRepo cr = new CategoryRepo();
            ArrayList<String> category = cr.getCategory();
            //Cursor cursor = cr.query();

            Log.d(TAG, "InBackground begin to read cursor.. " );
            readCategory(cr.query());
            Log.d(TAG, "End! " );

            Log.d(TAG, "InBackground category.size = " + category.size());
            //category.add("THIS IS ALL MATRIX ");
            return category;
        }


        @Override
        protected void onPostExecute(ArrayList <String> categoryList) {
            Toast.makeText(getActivity(), "on post execute", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "MainActivity category " + categoryList.size());

            for (String category : categoryList){
            Log.d(TAG, "MainActivity category " + category);
               Toast.makeText(getActivity(), "on post execute", Toast.LENGTH_SHORT).show();
           }
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
       // if (data.isClosed())return;

        if (data.moveToFirst()) {
            int i = 0;
            do {
                i++;
                String programName = data.getString(data.getColumnIndex(Category.KEY_CATEGORY));
                String channel = data.getString(data.getColumnIndex(Category.KEY_CHANNEL));
               // Toast.makeText(getActivity(), "Category: "+ programName+" Channel "+channel , Toast.LENGTH_SHORT).show();
                Log.d(TAG, "#=->Category: " + programName+"Channel "+channel+" Count: "+i);
                // do what ever you want here
            } while (data.moveToNext());
            //  DatabaseManager.getInstance().closeDatabase();
            // data.close();


        }
    }

}