package com.study.go.burlaka.showchannelsapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.study.go.burlaka.showchannelsapp.data.DatabaseManager;
import com.study.go.burlaka.showchannelsapp.data.model.Channel;
import com.study.go.burlaka.showchannelsapp.data.model.Program;

import java.util.ArrayList;

/**
 * Created by Operator on 22.09.2016.
 */
public class ProgramRepo {



    public ProgramRepo (){

    }

    public  String createTable (){
        return "CREATE TABLE " + Program.TABLE + "("+
                Program.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Program.KEY_CHANNEL_Id + " TEXT, "+
                Program.KEY_NAME + " TEXT);";
    }


    public int insert (Program program){
        int programId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Program.KEY_CHANNEL_Id, program.getChannelId());
        values.put(Program.KEY_NAME, program.getName());

        //Inserting Row
        //delete();
        programId = (int)db.insert(Program.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return programId;
    }

    public  void delete( ) {

       try{
           SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
           db.delete(Program.TABLE,null,null);
           DatabaseManager.getInstance().closeDatabase();
       } catch (SQLiteException e) {
           // database doesn't exist yet.
       }

    }

    public  Cursor query (){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        return db.query(Program.TABLE, null, null, null, null, null, null);
    }



    public  ArrayList <String> getChannelForShowing (){
        //StudentCourseList studentCourseList = new StudentCourseList();
        ArrayList<String> channels = new ArrayList<>();
        try{

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String selectQuery =  " SELECT DISTINCT " +Program.TABLE+"." + Program.KEY_CHANNEL_Id
                + " FROM " +Program.TABLE
                ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                channels.add(cursor.getString(cursor.getColumnIndex(Channel.KEY_CHANNEL_Id)) );
            } while (cursor.moveToNext());
        }

        cursor.close();


        DatabaseManager.getInstance().closeDatabase();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return channels;
    }


    public ArrayList <String> getPrograms4Channel (String keyChannel){
        ArrayList <String> programs = new ArrayList<>();
        try{
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //Create select to SQLite
        String selectQuery = " SELECT "+Program.TABLE+"."+ Program.KEY_NAME
                + " FROM "+ Program.TABLE
                + " WHERE " + Program.TABLE + "."+Program.KEY_CHANNEL_Id +" = '"+keyChannel+"'";

        //Working with cursor to get data to array list

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                programs.add( cursor.getString( cursor.getColumnIndex(Program.KEY_NAME ) ) );

            }while (cursor.moveToNext());
        }

            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }


        return programs;
    }




}
