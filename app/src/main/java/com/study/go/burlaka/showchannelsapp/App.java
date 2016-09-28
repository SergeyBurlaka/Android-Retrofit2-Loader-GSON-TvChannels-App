package com.study.go.burlaka.showchannelsapp;

import android.app.Application;
import android.content.Context;

import com.study.go.burlaka.showchannelsapp.data.DBHelper;
import com.study.go.burlaka.showchannelsapp.data.DatabaseManager;

/**
 * Created by Operator on 22.09.2016.
 */
public class App  extends Application {

    private static Context context;
    private static DBHelper dbHelper;


    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);
    }


    public static Context getContext(){
        return context;
    }
}