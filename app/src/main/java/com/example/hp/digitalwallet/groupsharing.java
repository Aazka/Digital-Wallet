package com.example.hp.digitalwallet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.BatchUpdateException;

public class groupsharing extends AppCompatActivity
{

    EditText GroupID,GroupName;
    Button CreateGroup,viewbutton;
    Database myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupsharing);
        myDatabase =new Database(this);
        GroupID=(EditText)findViewById(R.id.group_id);
        GroupName=(EditText)findViewById(R.id.group_title);
        CreateGroup=(Button)findViewById(R.id.create_group);
        viewbutton=(Button)findViewById(R.id.view_button);
        GroupSharing();
        ViewGroupTable();
    }

    public void GroupSharing(){
        CreateGroup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          boolean isInserted=  myDatabase.insertDataintoGroups(GroupName.getText().toString(),GroupID.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(groupsharing.this, "Data Inserted",Toast.LENGTH_LONG ).show();
                        }
                        else
                        {
                            Toast.makeText(groupsharing.this, "Data not Inserted",Toast.LENGTH_LONG ).show();

                        }
                        Intent i=new Intent(groupsharing.this,Members.class);
                        startActivity(i);
                    }
                }
        );
    }

    public void ViewGroupTable(){
        viewbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res=myDatabase.getGroupTableData();

                        if(res.getCount()==0)
                        {
                            showMessage("Error ","Nothing found");
                            return;
                        }

                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Group_ID :"+res.getString(0)+"\n");
                            buffer.append("Group_Name :"+res.getString(1)+"\n");
                        }
                        showMessage("Data",buffer.toString());

                    }
                }
        );
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
