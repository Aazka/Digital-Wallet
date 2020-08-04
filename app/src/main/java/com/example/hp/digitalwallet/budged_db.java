package com.example.hp.digitalwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class budged_db extends SQLiteOpenHelper {

    public static final String databaseName="User.db";
    //user_table
    public static final String table1="User_info";
    public static final String T1col1="FirstName";
    public static final String T1col2="LastName";
    public static final String T1col3="Email";
    public static final String T1col4="Password";
    public static final String T1col5="Gender";
    //budged
    public static final String table2="budged_datail";
    public static final String T2col1="Email";
    public static final String T2col2="CurrentDate";
    public static final String T2col3="TargetDate";
    public static final String T2col4="TotalAmount";

    private String SQLString1=("create table " +table1+
            "(" +T1col1+ " text ,"
            +T1col2+ " text, "
            +T1col3+ " text primary key,"
            +T1col4+ " text, "
            +T1col5+ " text "+
            ")");

    private String SQLString2 ="create table " + table2 +
            "("
            + T2col1 + " Text, "
            + T2col2 + " Date, "
            + T2col3 + " Date, "
            + T2col4 + " integer, "
            + "foreign key("+T2col1+") References "+table1+"("+T1col3+")"
            + ")";

    public budged_db(Context context) {
        super(context,databaseName,null,1);
        SQLiteDatabase db=this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLString1);
        db.execSQL(SQLString2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ table1);
        db.execSQL("drop table if exists "+ table2);
        onCreate(db);
    }
    public Cursor If_balance()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from " + table2,null);
        return res;
    }
    public Cursor onCheck(String email)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM table2 WHERE Email = '"+email+"'",null );
        return  result;

    }
    public boolean insertDataBudged(String CurrentD,String TargetD,String Amount)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent = new ContentValues();
        ourContent.put(T2col2,CurrentD);
        ourContent.put(T2col3,TargetD);
        ourContent.put(T2col4,Amount);
        long result=db.insert(table2,null,ourContent);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    /*public String get_email()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from " + table2,null);
        int i=0;
        while(res.moveToNext())
        {

        }
    }*/
}
