package com.example.hp.digitalwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public static final String databaseName="GroupSharing.db";
    //Table_group
    public static final String table1="Groups";
    public static final String T1col1="Group_Id";
    public static final String T1col2="Group_Name";
    //Member_group
    public static final String table2="Members";
    public static final String T2col1="Group_Id";
    public static final String T2col2="Member_Id";
    public static final String T2col3="Member_Name";

    //Expense_group
    public static final String table3="Expense";
    public static final String T3col1="Member_Id";
    public static final String T3col2="Expense_Name";
    public static final String T3col3="Expense_Amount";

   private String SQLString1="create table " + table1 +
            "("
            + T1col1 + " integer primary key, "
            + T1col2 + " Text "
            + " ) ";
    private String SQLString2 ="create table " + table2 +
            "("
            + T2col2 + " integer primary key, "
            + T2col3 + " Text, "
            + T2col1 + " integer , "
            + "foreign key(" + T2col1 + ") References " + table1 + " ( " + T1col1 + " ) "
            + " ) ";

    private String SQLString3 ="create table " + table3 +
            " ( "
            + T3col1 + " integer primary key , "
            + T3col2 + " Text , "
            + T3col3 + " integer , "
            + " foreign key ( " + T3col1 + " ) References " + table2 + " (" + T2col2 + " ) "
            + " ) ";

    public Database(Context context) {
        super(context,databaseName,null,1);
        SQLiteDatabase db=this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLString1);
        db.execSQL(SQLString2);
        db.execSQL(SQLString3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ table1);
        db.execSQL("drop table if exists "+ table2);
        db.execSQL("drop table if exists "+ table3);
        onCreate(db);

    }

    public boolean insertDataintoGroups(String groupname,String groupid){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent=new ContentValues();
        ourContent.put(T1col1,groupid);
        ourContent.put(T1col2,groupname);


        long result=db.insert( table1 ,null,ourContent);

        if(result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getGroupTableData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+ table1,null);
        return result;
    }

    public boolean insertDataintoMembers(String memname,String groupid){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent=new ContentValues();
        ourContent.put(T2col1,groupid);
        ourContent.put(T2col3,memname);


        long result=db.insert( table2 ,null,ourContent);

        if(result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getMemTableData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+ table2 ,null);
        return result;
    }

    public boolean ExpenseData(String memid,String expensename,String expenseamount){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent1=new ContentValues();
        ourContent1.put(T3col1,memid);
        ourContent1.put(T3col2,expensename);
        ourContent1.put(T3col3,expenseamount);


        long result=db.insert( table3 ,null,ourContent1);

        if(result == -1){
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getExpenseData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+ table3 ,null);
        return result;
    }

}
