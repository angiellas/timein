/*
08/12/2018
Author AngieSR

NOTE: This class was based on these tutorials: https://www.simplifiedcoding.net/firebase-realtime-database-crud/
*/

package com.example.angel.timein;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddShiftActivity extends AppCompatActivity {

    private TextView textViewClientName;
    private EditText editTextDate;
    private EditText editTextTimeStart;
    private EditText editTextTimeEnd;
    private ListView listViewShifts;
    private Button buttonAddShift;
    List <Shift> shiftList;
    DatabaseReference databaseShifts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        textViewClientName = (TextView) findViewById(R.id.tvClientName);
        editTextDate = (EditText) findViewById(R.id.etDate);
        editTextTimeStart = (EditText) findViewById(R.id.etTimeStart);
        editTextTimeEnd = (EditText) findViewById(R.id.etTimeEnd);
        listViewShifts = (ListView) findViewById(R.id.lvShifts);
        buttonAddShift = (Button) findViewById(R.id.btnAddShift);
        shiftList = new ArrayList<>();

        Intent intent = getIntent();

        String id = intent.getStringExtra(TimesheetActivity.CLIENT_ID);
        String name = intent.getStringExtra(TimesheetActivity.CLIENT_NAME);

        textViewClientName.setText(name);

        databaseShifts = FirebaseDatabase.getInstance().getReference("shifts").child(id);

        buttonAddShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveShift();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseShifts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shiftList.clear();
                for(DataSnapshot shiftSnapshot: dataSnapshot.getChildren()){
                    Shift shift = shiftSnapshot.getValue(Shift.class);
                    shiftList.add(shift);
                }
                ShiftList adapter = new ShiftList(AddShiftActivity.this, shiftList);
                listViewShifts.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveShift(){
        String date = editTextDate.getText().toString().trim();
        String timeStart = editTextTimeStart.getText().toString().trim();
        String timeEnd = editTextTimeEnd.getText().toString().trim();

        if(!date.isEmpty()&& !timeStart.isEmpty() && !timeEnd.isEmpty()){
            String id = databaseShifts.push().getKey();

            Shift shift = new Shift (id, date, timeStart, timeEnd);

            databaseShifts.child(id).setValue(shift);

            Toast.makeText(AddShiftActivity.this, "Your shift was saved", Toast.LENGTH_LONG).show();
            editTextDate.getText().clear();
            editTextTimeStart.getText().clear();
            editTextTimeEnd.getText().clear();
        }
        else{
            Toast.makeText(AddShiftActivity.this, "Please, enter all details", Toast.LENGTH_LONG).show();
        }


    }
}
