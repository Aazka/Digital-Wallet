package com.example.hp.digitalwallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class profilesetting extends AppCompatActivity {

    EditText FN ,LN,P,CP;
    Spinner G;
    SQLiteHelper myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesetting);
        myDatabase=new SQLiteHelper(this);
        FN = findViewById(R.id.firstname);
        LN = findViewById(R.id.firstname2);
        P = findViewById(R.id.password);
        CP = findViewById(R.id.repoassword);
        G = findViewById(R.id.spinner);
        SharedPreferences sharedPref = getSharedPreferences("Register", Context.MODE_PRIVATE);
        String fn = sharedPref.getString("firstname","");
        String ln = sharedPref.getString("lastname","");
        String gender = sharedPref.getString("gender","");
        FN.setText(fn);
        LN.setText(ln);
        List<String> Genders = new ArrayList<>();
        Genders.add(0,"Select Gender");
        Genders.add(1,"Male");
        Genders.add(2,"Female");
        ArrayAdapter<String> GenderAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Genders);
        GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        G.setAdapter(GenderAdapter);
        if(gender.matches("Male"))
        {
            G.setSelection(1);
        }
        else
        {
            G.setSelection(2);
        }
    }

    public void logout(View view)
    {
        SharedPreferences sharedPref=getSharedPreferences("Register", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putBoolean("init",false);
        editor.commit();
        Intent i = new Intent(this,login.class);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.check, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Save: {
                SharedPreferences sharedPref=getSharedPreferences("Register", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPref.edit();
                if(FN.getText().toString().matches("") || LN.getText().toString().matches("")|| G.getSelectedItem().toString().matches("Select Gender"))
                {
                    Toast.makeText(this, "Enter Valid Setting", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String pas = sharedPref.getString("password","");
                    String em = sharedPref.getString("email","");
                    myDatabase.updateData(FN.getText().toString(),LN.getText().toString(),em,pas, G.getSelectedItem().toString());
                    editor.putBoolean("init",true);
                    editor.putString("firstname", FN.getText().toString());
                    editor.putString("lastname", LN.getText().toString());
                    editor.putString("gender", G.getSelectedItem().toString());
                    editor.commit();
                    editor.apply();
                    Toast.makeText(this, "Settings Saved", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this,MainActivity.class);
                    startActivity(i);
                }
                return true;
            }
            default:
                return false;
        }
    }
    public void changpassword(View view)
    {
        SharedPreferences sharedPref = getSharedPreferences("Register", Context.MODE_PRIVATE);
        String p = sharedPref.getString("password","");
        String fn = sharedPref.getString("firstname","");
        String ln = sharedPref.getString("lastname","");
        String gender = sharedPref.getString("gender","");

        if(P.getText().toString().matches(CP.getText().toString()))
        {
            if(P.getText().toString().matches(p))
            {
                Toast.makeText(this,"You have entered previous password",Toast.LENGTH_LONG).show();
            }
            else {
                String em = sharedPref.getString("email","");
                myDatabase.updateData(fn,ln,em,P.getText().toString(),gender);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("init", true);
                editor.putString("password", P.getText().toString());
                editor.commit();
                editor.apply();
                Toast.makeText(this, "Password Changed", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this,"Password doesn't matched",Toast.LENGTH_LONG).show();
        }
    }



}
