package com.example.richa_764947_ft;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowUser extends AppCompatActivity {

    DataBaseHelper mDatabase;
    EditText serchText;
    List<Person> personList;

    ListView listView;
    PersonAdpter personAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        mDatabase = new DataBaseHelper(this);
        listView = findViewById(R.id.lvUser);
        serchText = findViewById(R.id.searchView);
        personList = new ArrayList<>();
        loadData();

        personAdpter = new PersonAdpter(this,R.layout.cell_user,personList,mDatabase);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(personAdpter);

        serchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ShowUser.this.personAdpter.getFilter().filter(s);
//                personAdpter.notifyDataSetChanged();

                String text = serchText.getText().toString();
                (personAdpter).filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void loadData() {


        Cursor cursor = mDatabase.getAllData();
        if (cursor.moveToFirst()){

            do {
                personList.add(new Person(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));


            }while (cursor.moveToNext());

            cursor.close();
        }


//        PersonAdpter personAdpter = new PersonAdpter(this,R.layout.cell_user,personList,mDatabase);
//        listView.setAdapter(personAdpter);

    }
}
