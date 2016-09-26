package com.study.go.burlaka.showchannelsapp.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.study.go.burlaka.showchannelsapp.R;
import com.study.go.burlaka.showchannelsapp.data.DatabaseManager;
import com.study.go.burlaka.showchannelsapp.data.model.Channel;
import com.study.go.burlaka.showchannelsapp.data.repo.ProgramRepo;
import com.study.go.burlaka.showchannelsapp.loaders.db.ChannelDBLoader;
import com.study.go.burlaka.showchannelsapp.loaders.server.request.ChannelLoader;
import com.study.go.burlaka.showchannelsapp.loaders.server.request.ProgramLoader;
import com.study.go.burlaka.showchannelsapp.ui.show.programs.viewpager.TextPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String CH_TAG = "get_Channel";
    private static final String PR_TAG = "Get_Program";
    private Button getCannel;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*getCannel = (Button) findViewById(R.id.getChannelB);
        getCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //  getLoaderManager().initLoader(R.id.channel_loader, Bundle.EMPTY,  MainActivity.this);
                readProgramData(/*Cursor data);
            }
        });*/

        Log.d(CH_TAG, "On create");
      //  getData();

        TextPagerAdapter mTextPagerAdapter = new TextPagerAdapter(
                getSupportFragmentManager(), getMyData());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTextPagerAdapter);

    }


    public List<String> getMyData() {
        ProgramRepo pr = new ProgramRepo();
        return pr.getChannelForShowing();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case R.id.channel_loader:
               // Log.v(TAG, "onCreateLoader"+"MainActivity");
                return new ChannelLoader(this);
            case R.id.tvshow_loader:
                return new ProgramLoader(this);
            case R.id.channel_db_loader:
                return new ChannelDBLoader(this);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch ( loader.getId()) {
            case R.id.channel_loader:
               // readChannelData(data);
                getLoaderManager().initLoader(R.id.tvshow_loader, Bundle.EMPTY,  MainActivity.this);
                break;
            case R.id.tvshow_loader:
               // readProgramData (/*data*/);
                break;
            case R.id.channel_db_loader:
                createViewPager (data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public void getData() {
        getLoaderManager().initLoader(R.id.channel_db_loader, Bundle.EMPTY,  MainActivity.this);
        //return data;
    }

    private void createViewPager(Cursor data) {

       // cursor = data;
        TextPagerAdapter mTextPagerAdapter = new TextPagerAdapter(
                getSupportFragmentManager(), readeCursor (data));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTextPagerAdapter);
    }




    private List <String> readeCursor(Cursor cursor) {
        ArrayList <String> channels = new ArrayList<>();

      //  if(cursor.isClosed())return channels;

        if(cursor == null && cursor.isClosed())return channels;
        if (cursor.moveToFirst()) {
            do {
                channels.add(cursor.getString(cursor.getColumnIndex(Channel.KEY_CHANNEL_Id)) );
            } while (cursor.moveToNext());
        }
       //cursor.close();

        DatabaseManager.getInstance().closeDatabase();
        return channels;
    }


   /* private void readProgramData() {
        ProgramRepo pr = new ProgramRepo();
        ArrayList<String> al;

        String ch5 = "5kanal";
        //al =  pr.getChannelForShowing();
        al =  pr.getPrograms4Channel(ch5  );
        int i = 0;
        for ( String ch : al){
            i++;
            Log.d(PR_TAG, " #=-> _+: " + ch +" Count "+Integer.toString(i));
        }

       // if (data.isClosed())return;

        /*if (data.moveToFirst()) {
            int i = 0;
            do {
                i++;
                String programName = data.getString(data.getColumnIndex(Program.KEY_NAME));
                Log.d(PR_TAG, "#=->Program: " + programName+" Count: "+i);
                // do what ever you want here
            } while (data.moveToNext());*/
          //  DatabaseManager.getInstance().closeDatabase();
           // data.close();

    //}

   /* private void readChannelData(Cursor data) {
            int i = 0;

        if (data.isClosed())return;

        if (data.moveToFirst()) {
            do {
                i++;
                String channelName = data.getString(data.getColumnIndex(Channel.KEY_NAME));
                Log.d(CH_TAG, "#=->Channel: " + channelName+" Count: "+i);
                // do what ever you want here
            } while (data.moveToNext());
            DatabaseManager.getInstance().closeDatabase();
            data.close();
        }

    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //cursor.close();
       // DatabaseManager.getInstance().closeDatabase();
    }


}
