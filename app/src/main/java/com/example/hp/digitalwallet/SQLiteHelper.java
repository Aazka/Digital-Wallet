package com.example.hp.digitalwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {//built in class from which we use function for database

    public static final String databaseName="User.db";
    public static final String tableName="UserInfo";
    public static final String col1="FirstName";
    public static final String col2="LastName";
    public static final String col3="Email";
    public static final String col4="Password";
    public static final String col5="Gender";

    //budged_table
    public static final String table1="budged_datail";
    public static final String T2col1="Emails";
    public static final String T2col2="CurrentDate";
    public static final String T2col3="TargetDate";
    public static final String T2col4="TotalAmount";

    public SQLiteHelper( Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db =this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLString =("create table " +tableName+
                "(" +col1+ " text ,"
                +col2+ " text, "
                +col3+ " text primary key,"
                +col4+ " text, "
                +col5+ " text "+
                ")");

        db.execSQL(SQLString);

         String SQLString2 ="create table " + table1 +
                "("
                + T2col1 + " Text, "
                + T2col2 + " Date, "
                + T2col3 + " Date, "
                + T2col4 + " integer, "
                + "foreign key("+T2col1+") References "+table1+"("+col3+")"
                + ")";
        db.execSQL(SQLString2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " +tableName);
        db.execSQL("drop table if exists " +table1);
        onCreate(db);

    }
    public boolean insertData(String FirstName,String LastName,String Email,String Password,String Gender)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent = new ContentValues();
        ourContent.put (col1,FirstName);/*key ,value*/
        ourContent.put(col2,LastName);
        ourContent.put(col3,Email);
        ourContent.put(col4,Password);
        ourContent.put(col5,Gender);
        long result=db.insert(tableName,null,ourContent);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor onCheck(String email)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM UserInfo WHERE Email = '"+email+"'",null );
         return  result;

    }
    public boolean updateData(String FirstName,String LastName,String Email,String Password,String Gender)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent = new ContentValues();
        ourContent.put (col1,FirstName);/*key ,value*/
        ourContent.put(col2,LastName);
        ourContent.put(col3,Email);
        ourContent.put(col4,Password);
        ourContent.put(col5,Gender);
        long res=db.update(tableName,ourContent,"Email = ?",new String[]{Email});
        if(res==-1)
        {
            return false;
        }
        else
            return true;
    }

    public boolean insertDataBudged(String CurrentD,String TargetD,String Amount)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent = new ContentValues();
        ourContent.put(T2col2,CurrentD);
        ourContent.put(T2col3,TargetD);
        ourContent.put(T2col4,Amount);
        long result=db.insert(table1,null,ourContent);

        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor If_balance()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from " + table1,null);
        return res;
    }
    public Cursor onCheck2(String email)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM  budged_datail WHERE Emails = '"+email+"'",null );
        return  results;

    }
}
