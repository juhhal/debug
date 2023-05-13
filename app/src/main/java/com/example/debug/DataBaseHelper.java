package com.example.debug;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String TOOL_TABLE = "TOOL_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME ";
    public static final String COLUMN_RATE = "RATE ";
    public static final String COLUMN_MODEL = "MODEL ";
    public static final String COLUMN = "user";
    public static final String COLUMN_OVERVIEW = "TOOL_OVERVIEW ";
    public static final String COLUMN_COST = "TOOL_USAGE ";
    public static final String COLUMN_PRODUCTION = "PRODUCTION";
    public static final String COLUMN_RATENUM = "RATENUM ";
    public static final String TABLENAME = "users";
    public static final String COL1 = "username";
    public static final String COL2 = "password";


    public DataBaseHelper(@Nullable Context context) {

        super(context, "tool.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement="CREATE TABLE " +TOOL_TABLE+
                "(" +COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_NAME+ " TEXT,"+
                COLUMN_RATE+" INT, "
                +COLUMN_MODEL+ " TEXT, "
                +COLUMN_OVERVIEW+ " TEXT, "
                +COLUMN_COST+ " INTEGER, "
                +COLUMN_PRODUCTION+ " TEXT, "
                +COLUMN_RATENUM+ " INTEGER, "
                + COLUMN + " TEXT, "
                + "FOREIGN KEY(" + COLUMN + ") REFERENCES " + TABLENAME + "(" + COL1 + ")"
                + ")";
        db.execSQL("create Table " + TABLENAME + "(" + COL1 + " TEXT primary key, " + COL2 + " TEXT)");
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists " + TABLENAME);
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL1, username);
        contentValues.put(COL2, password);
        long result = MyDB.insert(TABLENAME, null, contentValues);
        if(result==-1) return false;
        return true;
    }



    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ?", new String[]{username});
        if (cursor.getCount() > 0) return true;
        return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ? and " + COL2 + " = ?", new String[] {username,password});
        if(cursor.getCount()>0) return true;
        return false;
    }

    public boolean addOne(toolModel toolModel, String ue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(COLUMN_RATE,0);
        cv.put(COLUMN_NAME,toolModel.getName());
        cv.put(COLUMN_MODEL,toolModel.getModel());
        cv.put(COLUMN_OVERVIEW,toolModel.getOverview());
        cv.put(COLUMN_COST,toolModel.getCost());
        cv.put(COLUMN_PRODUCTION,toolModel.getProdYear());
        cv.put(COLUMN_RATENUM,0);
        cv.put(COLUMN, ue);
        long insert = db.insert(TOOL_TABLE, null, cv);
        return insert != -1;
    }



    public List<toolModel> getEveryone(){

        List<toolModel> returnList=new ArrayList<>();
        String quString="SELECT * FROM "+ TOOL_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(quString,null);
        if (cursor.moveToFirst()){
            do{
                int toolID=cursor.getInt(0);
                int toolRate=cursor.getInt(1);
                String toolName=cursor.getString(2);
                String toolModel=cursor.getString(3);
                String tooloverview=cursor.getString(4);
                int toolCost =Integer.parseInt(cursor.getString(5));
                String toolProd=cursor.getString(6);
                int toolRateNum=cursor.getInt(7);

                toolModel newTool=new toolModel(toolID,toolRate,toolName,toolModel,tooloverview,toolCost,toolProd,toolRateNum);
                returnList.add(newTool);

            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();

        return returnList;
    }
/*
    // we can use this method to show the user's vehicles (he offer it for rent)
    public List<toolModel> getEveryone(){
        List<toolModel> returnList = new ArrayList<>();
        // get data from database
        String queryString = "SELECT * FROM " + TOOL_TABLE + " v JOIN " + TABLENAME + " u ON v." + COL3 + " = u." + COL1 + " WHERE u." + COL1 + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            // loop through cursor results
            do{
                int toolID=cursor.getInt(0);
                int toolRate=cursor.getInt(1);
                String toolName=cursor.getString(2);
                String toolModel=cursor.getString(3);
                String tooloverview=cursor.getString(4);
                int toolCost = Integer.parseInt(cursor.getString(5));
                String toolProd=cursor.getString(6);
                int toolRateNum=cursor.getInt(7);
                byte[] photoData=cursor.getBlob(8);

                toolModel newTool=new toolModel(toolID,toolRate,toolName,toolModel,tooloverview,toolCost,toolProd,toolRateNum, photoData);
                returnList.add(newTool);
            }while (cursor.moveToNext());
        } else{
            // nothing happens. no one is added.
        }
        //close
        cursor.close();
        db.close();
        return returnList;
    }
*/


    public boolean DeleteOne(toolModel TM){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString= "Delete From " + TOOL_TABLE + " WHERE " + COLUMN_ID + " = " + TM.getId() ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        } else{
            // nothing happens. no one is added.
            return false;
        }
        //close
    }

    public boolean DeleteOne(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString= "Delete From " + TOOL_TABLE + " WHERE " + COLUMN_ID + " = " + id ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        } else{
            // nothing happens. no one is added.
            return false;
        }
        //close
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TOOL_TABLE ", null);
        return cursor;
    }



}
/*
    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ?", new String[]{email});
        if (cursor.getCount() > 0) return true;
        return false;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + COL1 + " = ? and " + COL2 + " = ?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        return false;
    }*/
