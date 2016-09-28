package com.study.go.burlaka.showchannelsapp.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.study.go.burlaka.showchannelsapp.App;
import com.study.go.burlaka.showchannelsapp.data.model.Category;
import com.study.go.burlaka.showchannelsapp.data.model.Channel;
import com.study.go.burlaka.showchannelsapp.data.model.Program;
import com.study.go.burlaka.showchannelsapp.data.repo.CategoryRepo;
import com.study.go.burlaka.showchannelsapp.data.repo.ChannelRepo;
import com.study.go.burlaka.showchannelsapp.data.repo.ProgramRepo;

/**
 * Created by Operator on 22.09.2016.
 */
public class DBHelper   extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION =14;
    // Database Name
    private static final String DATABASE_NAME = "sqliteDBMultiTbl.db";
    private static final String TAG = DBHelper.class.getSimpleName().toString();
    private ChannelRepo channelRepo;
    private ProgramRepo programRepo;
    private CategoryRepo categoryRepo;

    public DBHelper( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        channelRepo = new ChannelRepo();
        programRepo = new ProgramRepo();
        categoryRepo = new CategoryRepo();
        //All necessary tables you like to create will create here
        db.execSQL(channelRepo.createTable());
        db.execSQL(programRepo.createTable());
        db.execSQL(categoryRepo.createTable());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  Log.i(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));
        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Channel.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Program.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Category.TABLE);
        onCreate(db);
    }
}