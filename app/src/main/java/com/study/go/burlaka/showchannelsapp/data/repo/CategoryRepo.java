package com.study.go.burlaka.showchannelsapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.study.go.burlaka.showchannelsapp.data.DatabaseManager;
import com.study.go.burlaka.showchannelsapp.data.model.Category;

import java.util.ArrayList;

/**
 * Created by Operator on 24.09.2016.
 */
public class CategoryRepo {

    public  String createTable (){
        return "CREATE TABLE " + Category.TABLE + "("+
                Category.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
               Category.KEY_CATEGORY + " TEXT, "+
                Category.KEY_CHANNEL + " TEXT);";
    }


    public   int insert (Category category){
        int categoryId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Category.KEY_CATEGORY, category.getCategoryId());
        values.put(Category.KEY_CHANNEL, category.getChannelId());
        //Inserting Row
        //delete();
        categoryId = (int)db.insert(Category.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return categoryId;
    }

    public  void delete( ) {
       // if(db.e)
        try {
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            db.delete(Category.TABLE,null,null);
            DatabaseManager.getInstance().closeDatabase();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
    }


    public Cursor query (){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        return db.query(Category.TABLE, null, null, null, null, null, null);
    }


    public ArrayList<String> getCategory (){
        //StudentCourseList studentCourseList = new StudentCourseList();
        ArrayList<String> category = new ArrayList<>();
        try{
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

            String selectQuery =  " SELECT DISTINCT " + Category.TABLE+"." + Category.KEY_CATEGORY
                    + " FROM " +Category.TABLE
                    ;

            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    category.add(cursor.getString(cursor.getColumnIndex(Category.KEY_CATEGORY)) );
                } while (cursor.moveToNext());
            }
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
          } catch (SQLiteException e) {
            // database doesn't exist yet.
         }
        return category;
    }


    public ArrayList <String> getChannels4mCategory(String keyChannel){
        ArrayList <String> channels = new ArrayList<>();
        try{
            SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
            //Create select to SQLite
            String selectQuery = " SELECT "+ Category.TABLE+"."+ Category.KEY_CHANNEL
                    + " FROM "+ Category.TABLE
                    + " WHERE " + Category.TABLE + "."+Category.KEY_CATEGORY +" = '"+keyChannel+"'";
            //Working with cursor to get data to array list
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()){
                do {
                    channels.add( cursor.getString( cursor.getColumnIndex(Category.KEY_CHANNEL ) ) );
                }while (cursor.moveToNext());
            }
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return channels;
    }
}
