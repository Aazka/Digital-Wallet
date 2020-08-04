package com.example.hp.digitalwallet;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity {
    SQLiteHelper myDatabase;
    EditText firstname, lastname, Email, password;
    Spinner gender;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        myDatabase = new SQLiteHelper(this);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        Email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        gender = (Spinner) findViewById(R.id.Gender);
        btnAddData = (Button) findViewById(R.id.signup_btn);
        List<String> Gender = new ArrayList<>();
        Gender.add(0, "Select Gender");
        Gender.add(1, "Male");
        Gender.add(2, "Female");

        ArrayAdapter<String> GenderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Gender);
        GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(GenderAdapter);
        gender.setSelection(0);

        AddData();

    }

    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Gender = gender.getSelectedItem().toString();

                if (firstname.getText().toString().matches("") || lastname.getText().toString().matches("") || Email.getText().toString().matches("")
                        || password.getText().toString().matches("") || Gender.matches("Select Gender")) {
                    Toast.makeText(signup.this, "Please Enter Data", Toast.LENGTH_LONG).show();
                } else {
                    boolean isInserted = myDatabase.insertData(firstname.getText().toString(), lastname.getText().toString(), Email.getText().toString(), password.getText().toString(), Gender);

                    if (isInserted == true) {
                        SharedPreferences sharedPref = getSharedPreferences("Register", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("init", true);
                        editor.putString("firstname", firstname.getText().toString());
                        editor.putString("lastname", lastname.getText().toString());
                        editor.putString("email", Email.getText().toString());
                        editor.putString("password", password.getText().toString());
                        editor.putString("gender", gender.getSelectedItem().toString());
                        editor.commit();
                        editor.apply();
                        Toast.makeText(signup.this, "SignUp Successful", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(signup.this, MainActivity.class);
                        startActivity(i);


                    } else {
                        Toast.makeText(signup.this, "Not Sign In", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
 /*   public void OnClickSignUpSaves(View view)
    {
        gender=(Spinner)findViewById(R.id.Gender);
        String SPG= gender.getSelectedItem().toString();
        String fn=firstname.getText().toString();
        String ln=lastname.getText().toString();
        String em=Email.getText().toString();
        String pas=password.getText().toString();
        if(fn.matches("")||ln.matches("")||em.matches("")||pas.matches("")||SPG.matches("Select Gender"))
        {

            Toast.makeText(this, "Please Enter all fields", Toast.LENGTH_LONG).show();
        }
        else
        {
            SharedPreferences sharedPref = getSharedPreferences("Register", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("init",true);
            editor.putString("firstname", firstname.getText().toString());
            editor.putString("lastname", lastname.getText().toString());
            editor.putString("email", Email.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.putString("gender", gender.getSelectedItem().toString());
            editor.commit();
            editor.apply();
            Toast.makeText(this, "Sign_up_Successfull", Toast.LENGTH_LONG).show();
            Intent j=new Intent(this,MainActivity.class);
            startActivity(j);

        }
    }*/
}

