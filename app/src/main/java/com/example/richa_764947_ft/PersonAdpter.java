package com.example.richa_764947_ft;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersonAdpter extends ArrayAdapter {

    Context  mContext;
    int layoutRes;
    List<Person> personList;
    DataBaseHelper mDatabase;
    ArrayList<Person> arraylist;

    public PersonAdpter(@NonNull Context mContext, int layoutRes, List<Person> personList, DataBaseHelper mDatabase) {
        super(mContext, layoutRes,personList);
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.personList = personList;
        this.arraylist = new ArrayList<Person>();
        this.arraylist.addAll(personList);
        this.mDatabase = mDatabase;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutRes,null);
        TextView tvname = v.findViewById(R.id.tv_Fname);
        TextView tvsalary = v.findViewById(R.id.tv_Lname);
        TextView tvdept = v.findViewById(R.id.tv_Phone);
        TextView tvjoinDate = v.findViewById(R.id.tv_Address);


        final Person person = personList.get(position);
        tvname.setText(person.getFname());
        tvsalary.setText(person.getLname());
        tvdept.setText(person.getPhone());
        tvjoinDate.setText(person.getAddress());

        v.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee(person);
            }
        });

        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmoloyee(person);
            }
        });

        return  v;

    }

    private void deleteEmoloyee(final Person person) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  String sql = "DELETE FROM employees WHERE id=?";
               // mDatabase.execSQL(sql,new Integer[]{employee.getId()});


               if( mDatabase.deletePerson(person.getId())){
                   loadData();
               }


            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateEmployee(final Person person) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Update Person");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View customLayout = inflater.inflate(R.layout.update_user, null);
        builder.setView(customLayout);

        final EditText updateFName = customLayout.findViewById(R.id.update_Fname);
        final EditText updateLName = customLayout.findViewById(R.id.update_Lname);
        final EditText updatePhone = customLayout.findViewById(R.id.update_phone);
        final EditText updateAddress = customLayout.findViewById(R.id.update_Address);


        updateFName.setText(person.getFname());
        updateLName.setText(person.getLname());
        updatePhone.setText(person.getPhone());
        updateAddress.setText(person.getAddress());


        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        customLayout.findViewById(R.id.btn_update_emp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = updateFName.getText().toString().trim();
                String lname = updateLName.getText().toString().trim();

                String phone = updatePhone.getText().toString().trim();
                String address = updateAddress.getText().toString().trim();


                if (fname.isEmpty()){
                    updateFName.setError("Name is Mandadory");
                    updateFName.requestFocus();
                    return;

                }
                if (lname.isEmpty()){
                    updateLName.setError("Last Name is Mandadory");
                    updateLName.requestFocus();
                    return;
                }
                if (phone.isEmpty()){
                    updatePhone.setError("Phone Number is Mandadory");
                    updatePhone.requestFocus();
                    return;
                }
                if (address.isEmpty()){
                    updateAddress.setError("Address is Mandadory");
                    updateAddress.requestFocus();
                    return;
                }

                if (mDatabase.updatePersonData(person.getId(),fname,lname,phone,address)){
                    Toast.makeText(mContext, "PERSON DATA UPDATED", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(mContext, "PERSON DATA IS NOT UPDATED", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }
        });



    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        personList.clear();
        if (charText.length() == 0) {
            personList.addAll(arraylist);
        } else {
            for (Person person : arraylist) {
                if (person.getPhone().toLowerCase(Locale.getDefault())
                        .contains(charText) || person.getFname().toLowerCase(Locale.getDefault())
                        .contains(charText)|| person.getLname().toLowerCase(Locale.getDefault())
                        .contains(charText) || person.getAddress().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    personList.add(person);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void loadData() {

        String sql = "SELECT * FROM employees";
       // Cursor c = mDatabase.rawQuery(sql,null);
        Cursor c = mDatabase.getAllData();
        personList.clear();
        if (c.moveToFirst()){


            do {
                personList.add(new Person(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));

            }while (c.moveToNext());
            c.close();

        }
        notifyDataSetChanged();
    }

}
