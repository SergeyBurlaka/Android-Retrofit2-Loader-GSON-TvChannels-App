package com.study.go.burlaka.showchannelsapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.study.go.burlaka.showchannelsapp.data.DatabaseManager;
import com.study.go.burlaka.showchannelsapp.data.model.Channel;

/**
 * Created by Operator on 22.09.2016.
 */
public class ChannelRepo {


    public ChannelRepo (){

    }

    public  String createTable (){
        return "CREATE TABLE " + Channel.TABLE + "("+
                Channel.KEY_CHANNEL_Id + " TEXT PRIMARY KEY, "+
                Channel.KEY_NAME + " TEXT);";
    }

    public   int insert (Channel channel){
        int channelId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Channel.KEY_CHANNEL_Id, channel.getChannelId());
        values.put(Channel.KEY_NAME, channel.getName());

        //Inserting Row
       //delete();
        channelId = (int)db.insert(Channel.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    return channelId;
    }

    public  void delete( ) {
        try{
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Channel.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
    }

    public  Cursor query (){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        return db.query(Channel.TABLE, null, null, null, null, null, null);
    }

}
