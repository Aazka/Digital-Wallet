package com.example.hp.digitalwallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Goals extends AppCompatActivity {

    EditText goal_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        goal_text =(EditText) findViewById(R.id.Goal_text);
    }

    public void OnClickCreateGoal(View view){
        Intent i=new Intent(this,New_Goal.class);
        goal_text =(EditText) findViewById(R.id.Goal_text);

        String userMessage = goal_text.getText().toString();
        i.putExtra("goal_text",userMessage);

        startActivity(i);

    }

    public void OnClickVehical(View view){
        Intent i=new Intent(this,New_Goal.class);


        String userMessage = "New Vehical";
        i.putExtra("goal_text",userMessage);

        startActivity(i);
    }

    public void OnClickEducation(View view){
        Intent i=new Intent(this,New_Goal.class);


        String userMessage = "Education";
        i.putExtra("goal_text",userMessage);

        startActivity(i);
    }

    public void OnClickCharity(View view){
        Intent i=new Intent(this,New_Goal.class);


        String userMessage = "Charity";
        i.putExtra("goal_text",userMessage);

        startActivity(i);
    }
}
