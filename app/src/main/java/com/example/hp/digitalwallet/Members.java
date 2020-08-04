package com.example.hp.digitalwallet;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Members extends AppCompatActivity {

    EditText GroupId2,MemName;
    Button AddMem,viewmem;
    Database myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        myDatabase =new Database(this);
        GroupId2=(EditText)findViewById(R.id.group_id2);
        MemName=(EditText)findViewById(R.id.member_name);
        AddMem=(Button)findViewById(R.id.add_member);
        viewmem=(Button)findViewById(R.id.view_mem);
        AddMembers();
        ViewMembers();
    }

    public void AddMembers(){
        AddMem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted=  myDatabase.insertDataintoMembers(MemName.getText().toString(),GroupId2.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(Members.this, "Data Inserted",Toast.LENGTH_LONG ).show();
                        }
                        else
                        {
                            Toast.makeText(Members.this, "Data not Inserted",Toast.LENGTH_LONG ).show();

                        }
                        Intent i=new Intent(Members.this,add_expense.class);
                        startActivity(i);

                    }
                }
        );
    }

    public void ViewMembers(){
        viewmem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res=myDatabase.getMemTableData();

                        if(res.getCount()==0)
                        {
                            showMessage("Error ","Nothing found");
                            return;
                        }

                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Member_ID :"+res.getString(0)+"\n");
                            buffer.append("Member_Name :"+res.getString(1)+"\n");
                            buffer.append("Group_ID :"+res.getString(2)+"\n");
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
