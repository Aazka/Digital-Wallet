package com.example.hp.digitalwallet;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class New_Goal extends AppCompatActivity {

    EditText goal_text1;
    EditText targetamount;
    EditText savedalready;
    EditText date;
    goal_database myGoaldatabase;
    Button view,Save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_goal);
        myGoaldatabase =new goal_database(this);
        goal_text1=(EditText) findViewById(R.id.goal_text1);
        targetamount=(EditText)findViewById(R.id.target_amount);
        savedalready=(EditText)findViewById(R.id.saved_already);
        date=(EditText)findViewById(R.id.date);
        view=(Button)findViewById(R.id.view_btn);
        Save=(Button)findViewById(R.id.Done);
        Bundle firstData =getIntent().getExtras();

        if(firstData==null)
        {
            return;
        }

        String theMessage =firstData.getString("goal_text");
         goal_text1=(EditText) findViewById(R.id.goal_text1);

        goal_text1.setText(theMessage);
        OnClickSaveData();
        viewGoal();
    }

    public void OnClickSaveData(){
   /*   SharedPreferences shared=getSharedPreferences("Goals",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shared.edit();

        editor.putString("goal",goal_text1.toString());
        editor.putString("targetamount",targetamount.toString());
        editor.putString("savedalready",savedalready.toString());
        editor.putString("date",date.toString());
        editor.apply();
        Toast.makeText(this,"saved",Toast.LENGTH_LONG).show();*/
       Save.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       boolean isInserted = myGoaldatabase.insertData(goal_text1.getText().toString(),
                               targetamount.getText().toString(), savedalready.getText().toString(),date.getText().toString());
                       if (isInserted == true) {
                           Toast.makeText(New_Goal.this, "Data Inserted",Toast.LENGTH_LONG ).show();
                       }
                       else
                       {
                           Toast.makeText(New_Goal.this, "Data not Inserted",Toast.LENGTH_LONG ).show();

                       }
                   }
               }
       );

    }

    public void viewGoal(){
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res= myGoaldatabase.getAllData();

                        if(res.getCount()==0)
                        {
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Goal_ID :"+res.getString(0)+"\n");
                            buffer.append("Goal_Name :"+res.getString(1)+"\n");
                            buffer.append("Goal_Amount :"+res.getString(2)+"\n");
                            buffer.append("Goal_SAmount:"+res.getString(3)+"\n");
                            buffer.append("Goal_Date:"+res.getString(4)+"\n");
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
