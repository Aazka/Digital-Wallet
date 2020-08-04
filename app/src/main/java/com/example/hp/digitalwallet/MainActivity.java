package com.example.hp.digitalwallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SQLiteHelper mydb;
    String currentD,targetD,amount;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPref=getSharedPreferences("Register", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        boolean check = sharedPref.getBoolean("init",false);
        if(check==true)
        {
            editor.putBoolean("init",true);
            editor.commit();

        }
        else if(check == false)
        {
            Intent i = new Intent(this,login.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerView.findViewById(R.id.header);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,profilesetting.class);
                startActivity(i);
            }
        });
        TextView navUsername = (TextView) headerView.findViewById(R.id.UserName);
        navUsername.setText(sharedPref.getString("firstname","") + " " + sharedPref.getString("lastname",""));
        TextView navUserEmail = (TextView) headerView.findViewById(R.id.UserEmail);
        navUserEmail.setText(sharedPref.getString("email",""));
        navigationView.setNavigationItemSelectedListener(this);


        mydb = new SQLiteHelper(MainActivity.this);
        TextView show_data = (TextView) findViewById(R.id.cureent_balance_text);
        String email = sharedPref.getString("email", "");
        Cursor F = mydb.onCheck2(email);

        while (F.moveToNext()) {
            currentD = F.getString(1);
            targetD = F.getString(2);
            amount = F.getString(3);
        }
        if (F.getCount() == 0) {
            show_data.setText("current amount 0 target date 0");
        }
        else {
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            Date current = new Date();
            Date pre = new Date(targetD);
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            if (current.after(pre)) {
                show_data.setText("current amount 0 target date 0 current_after_pre");
                boolean isInserted=mydb.insertDataBudged("0","0",amount);
                if(isInserted == true)
                {
                    Toast.makeText(MainActivity.this,"Data insert in 0",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Data not insert in 0",Toast.LENGTH_LONG).show();
                }
            }
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetValue();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            new android.support.v7.app.AlertDialog.Builder(this)
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.goals) {
            Intent I = new Intent(this,Goals.class);
            startActivity(I);

        }
        else if(id == R.id.logout)
        {   SharedPreferences sharedPref=getSharedPreferences("Register", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPref.edit();
            editor.putBoolean("init",false);
            editor.commit();
            Intent I = new Intent(this,login.class);
            startActivity(I);
        }

        else if(id == R.id.groupsharing)
        {
            Intent I = new Intent(this,groupsharing.class);
            startActivity(I);
        }
        else if(id == R.id.settings)
        {

            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);

        }
        else if(id == R.id.about)
        {

            Intent i = new Intent(this, About.class);
            startActivity(i);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SetValue() {
        final TextView show_data=(TextView)findViewById(R.id.cureent_balance_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.init_balance, null);
        final EditText current_value = (EditText) mView.findViewById(R.id.editText_init_value);
        final EditText target_text = (EditText) mView.findViewById(R.id.target_date);
        Button set_B = (Button) mView.findViewById(R.id.set_button);
        set_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date currentDate = new Date();
                Calendar c = Calendar.getInstance();
                int td=Integer.parseInt(target_text.getText().toString());
                c.add(Calendar.DATE, td);
                Date currentDatePlusOne = c.getTime();

                boolean isInserted=mydb.insertDataBudged(dateFormat.format(currentDate),//insert data call from helper class
                        dateFormat.format(currentDatePlusOne),
                        current_value.getText().toString());
                if(isInserted == true)
                {
                    Toast.makeText(MainActivity.this,"Data insert in budgedCycle",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Data not insert in budgedCycle",Toast.LENGTH_LONG).show();
                }
                show_data.setText("current amount"+" "+current_value.getText().toString()+" "+"target_Date"+" "+dateFormat.format(currentDatePlusOne));

            }
        });
        builder.setView(mView);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}
