package com.example.hp.digitalwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database2 extends SQLiteOpenHelper {

    public static final String databaseName="GroupSharing.db";
    //Expense_group
    public static final String table3="Expense";
    public static final String T3col1="Member_Id";
    public static final String T3col2="Expense_Name";
    public static final String T3col3="Expense_Amount";

    private String SQLString3 ="create table " + table3 +
            "("
            + T3col1 + " integer primary key,"
            + T3col2 + " Text ,"
            + T3col3 + "integer "
            + ")";

    public Database2(Context context) {
        super(context,databaseName,null,1);
        SQLiteDatabase db=this.getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLString3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ table3);
        onCreate(db);
    }

    public boolean ExpenseData(String memid,String expensename,String expenseamount){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues ourContent1=new ContentValues();
        ourContent1.put(T3col1,memid);
        ourContent1.put(T3col2,expensename);
        ourContent1.put(T3col3,expenseamount);


        long result=db.insert(table3,null,ourContent1);

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
        Cursor result=db.rawQuery("select * from "+table3,null);
        return result;
    }
}
