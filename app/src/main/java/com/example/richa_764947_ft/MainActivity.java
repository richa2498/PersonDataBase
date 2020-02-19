package com.example.richa_764947_ft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText fname,lname,phone,address;
    DataBaseHelper mDatabase;
    TextView tv;
    ArrayList<String> number;
    boolean isSame = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fname = findViewById(R.id.etFname);
        lname = findViewById(R.id.etLname);
        phone = findViewById(R.id.etPhone);
        address = findViewById(R.id.etAddress);
        tv =  findViewById(R.id.tvViewData);

        findViewById(R.id.btnAddPerson).setOnClickListener(this);
        findViewById(R.id.tvViewData).setOnClickListener(this);

        mDatabase = new DataBaseHelper(this);
        loadData();

            tv.setText("You have "+loadData()+" Contacts");



    }
    private int loadData() {

        Cursor c = mDatabase.getAllData();
        number = new ArrayList<>();
        if (c.moveToFirst()){


            do {
               number.add(c.getString(3));

            }while (c.moveToNext());
            c.close();

        }
       return c.getCount();


    }

    @Override
    protected void onStart() {
        super.onStart();
        fname.setText("");
        lname.setText("");
        phone.setText("");
        address.setText("");
        loadData();
        tv.setText("You have "+loadData()+" Contacts");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddPerson:
                addPersonData();
                break;

            case R.id.tvViewData:
                Intent intent = new Intent(this,ShowUser.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }

    private void addPersonData() {

        String fName = fname.getText().toString().trim();
        String lName = lname.getText().toString().trim();
        String mPhone = phone.getText().toString().trim();
        String mAddress = address.getText().toString().trim();


        if (fName.isEmpty()){
            fname.setError("Name field is mandadory");
            fname.requestFocus();
            return;
        }
        if (lName.isEmpty()){
            lname.setError("Last Name can't be empty");
            lname.requestFocus();
            return;
        }
        if (mPhone.isEmpty()){
            phone.setError("Phone Number can't be empty");
            phone.requestFocus();
            return;
        }
        if (mAddress.isEmpty()){
            address.setError("Address can't be empty");
            address.requestFocus();
            return;
        }
        for (int i =0;i<number.size();i++){
            if (number.contains(mPhone)){
                isSame = true;
            }
            else {
                isSame = false;
            }
        }
        if (!isSame){

            if (mDatabase.addEmployee(fName,lName,mPhone,mAddress)){
                Toast.makeText(this, "Person Is addaed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Person is not addaed", Toast.LENGTH_SHORT).show();
            }
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Phone Number Already Exist");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }
}
