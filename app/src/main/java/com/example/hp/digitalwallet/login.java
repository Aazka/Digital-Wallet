package com.example.hp.digitalwallet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    SQLiteHelper myDatabase;
    EditText emailInput;
    EditText passwordInput;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        emailInput = findViewById(R.id.user_plain_id);
        passwordInput = findViewById(R.id.password_plain_id);
        Login=findViewById(R.id.button_login_id);
        myDatabase=new SQLiteHelper(this);
        login();
    }
    public void signin(View view)
    {
        Intent i= new Intent(login.this,signup.class);
        startActivity(i);
    }
    public void login()
    {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDatabase.onCheck(emailInput.getText().toString());
                res.moveToNext();
                if(res.getCount()==0)
                {
                    Toast.makeText(login.this,"SignIn First",Toast.LENGTH_LONG).show();
                }
                 else if(!(res.getString(3).matches(passwordInput.getText().toString())))
                {
                    Toast.makeText(login.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences sharedPref = getSharedPreferences("Register", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPref.edit();
                    editor.putString("firstname", res.getString(0));
                    editor.putString("lastname", res.getString(1));
                    editor.putString("email", res.getString(2));
                    editor.putString("password", res.getString(3));
                    editor.putString("gender", res.getString(4));
                    editor.putBoolean("init",true);
                    editor.commit();
                    Toast.makeText(login.this,"Login Successful",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(login.this, MainActivity.class);
                    startActivity(i);

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}