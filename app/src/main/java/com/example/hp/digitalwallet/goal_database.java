package com.example.hp.digitalwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class goal_database extends SQLiteOpenHelper {

    public static final String databaseName="wallet.db";
    public static final String tableName="Goals";

    public static final String col1="Goal_ID";
    public static final String col2="Goal_Name";
    public static final String col3="Goal_Amount";
    public static final String col4="Goal_SAmount";
    public static final String col5="Goal_Date";

    public goal_database(Context context) {
        super(context,databaseName,null,1);
        SQLiteDatabase db=this.getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLString ="create table " + tableName +
                " ( "
                + col1 + " integer primary key autoincrement, "
                + col2 + " Text, "
                + col3 + " integer, "
                + col4 + " integer, "
                + col5 + " text "
                + " ) ";

        db.execSQL(SQLString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ tableName);
        onCreate(db);
    }

    public boolean insertData(String name,String Amount,String SAmount,String Date){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent=new ContentValues();
        ourContent.put(col2,name);
        ourContent.put(col3,Amount);
        ourContent.put(col4,SAmount);
        ourContent.put(col5,Date);


        long result=db.insert( tableName,null,ourContent);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }


    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+ tableName ,null);

        return result;
    }
}
