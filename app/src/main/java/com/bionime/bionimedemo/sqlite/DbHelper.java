package com.bionime.bionimedemo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bionime.bionimedemo.BasedbContracts;

public class DbHelper extends SQLiteOpenHelper implements BasedbContracts {
    private final static int Dbversion = 1;
    private final static String Dbname = "Bionime.db";

    public DbHelper(Context context) {
        super(context, Dbname, null, Dbversion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Do Nothing
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Do Nothing
    }

    public void execSQL(){
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_BOOK = "create table if not exists " + TableName + "("
                + "id integer primary key autoincrement, "
                + siteName + " text, "
                + county + " text, "
                + aqi + " integer, "
                + status + " text, "
                + pollutant + " text)";
        db.execSQL(CREATE_BOOK);
    }

    public Cursor query(){
      SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TableName,null,null,null,null
        ,null,null);
    }

    public long insert(String name,String cty,String aqiview,String sts,String poll){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(siteName,name);
        cv.put(county,cty);
        cv.put(aqi,aqiview);
        cv.put(status,sts);
        cv.put(pollutant,poll);
        return db.insert(TableName,null,cv);
    }

    public void delete(String sitename){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " +TableName + " WHERE " + siteName +" = '"+sitename+"'");
        Log.d("Log-> DB ","" + sitename);
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TableName);
    }
    public boolean tabIsExist(String tabName){
        boolean result = false;
        if(tabName == null){
            return false;
        }
        SQLiteDatabase db ;
        Cursor cursor ;
        try {
            db = this.getReadableDatabase();
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                    cursor.close();
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;
    }
}
