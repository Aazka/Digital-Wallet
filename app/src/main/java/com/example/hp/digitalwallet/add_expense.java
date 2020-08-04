package com.example.hp.digitalwallet;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_expense extends AppCompatActivity {

    EditText MemId,ExpenseName,ExpenseAmount;
    Button AddExpensebtn,ViewExpensebtn;
    Database mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        mydatabase=new Database(this);
        MemId=(EditText)findViewById(R.id.mem_Id);
        ExpenseName=(EditText)findViewById(R.id.expense_name);
        ExpenseAmount=(EditText)findViewById(R.id.expense_amount);
        AddExpensebtn=(Button)findViewById(R.id.add_expense);
        ViewExpensebtn=(Button)findViewById(R.id.view_expense);
        AddExpenseData();
        ViewExpenseData();
    }
    public void AddExpenseData(){
        AddExpensebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted=mydatabase.ExpenseData(MemId.getText().toString(),ExpenseName.getText().toString(),ExpenseAmount.getText().toString());

                        if(isInserted==true)
                        {
                            Toast.makeText(add_expense.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(add_expense.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );
    }

    public void ViewExpenseData(){
        ViewExpensebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res= mydatabase.getExpenseData();

                        if(res.getCount()==0)
                        {
                            showMessage("Error ","Nothing found");
                            return;
                        }

                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Member_ID :"+res.getString(0)+"\n");
                            buffer.append("Expense_Name :"+res.getString(1)+"\n");
                            buffer.append("Expense_Amount :"+res.getString(2)+"\n");
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
