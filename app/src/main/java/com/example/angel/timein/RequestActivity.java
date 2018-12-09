package com.example.angel.timein;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RequestActivity extends AppCompatActivity {

    private TextView request;
    private TextView firstDay;
    private TextView lastDay;
    private EditText name;
    private EditText email;
    private EditText type;
    private EditText dayStart;
    private EditText dayEnd;
    private Button submitRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        request = (TextView) findViewById(R.id.tvRequest);
        firstDay = (TextView) findViewById(R.id.tvFirstDay);
        lastDay = (TextView) findViewById(R.id.tvLastDay);
        name = (EditText) findViewById(R.id.etName);
        email = (EditText) findViewById(R.id.etEmail);
        type =(EditText) findViewById(R.id.etTypeRequest);
        dayStart = (EditText) findViewById(R.id.etFirstDay);
        dayEnd = (EditText) findViewById(R.id.etLastDay);
        submitRequest = (Button) findViewById(R.id.btnSubmit);

        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = name.getText().toString();
                String email2 = email.getText().toString();
                String request = type.getText().toString();
                String firstDay = dayStart.getText().toString();
                String lastDay = dayEnd.getText().toString();

                if (firstName.isEmpty() || email2.isEmpty() || request.isEmpty() || firstDay.isEmpty() || lastDay.isEmpty()) {
                    Toast.makeText(RequestActivity.this, "Please enter all the details", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent submitIntent = new Intent(Intent.ACTION_SEND);
                    submitIntent.setType("message/rfc822");
                    submitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"time.in@outlook.com"});
                    submitIntent.putExtra(Intent.EXTRA_SUBJECT, "Holiday/Day off request");
                    submitIntent.putExtra(Intent.EXTRA_TEXT,
                                    "Name: " + name.getText().toString() + '\n' + "Email: " + email.getText().toString() + '\n'
                                    + "Type of request: " + type.getText().toString() + '\n'
                                    + "Starting on the: " + dayStart.getText().toString() + '\n'
                                    + "Ending on the: " + dayEnd.getText().toString());

                    try {
                        startActivity(Intent.createChooser(submitIntent, "Please, choose a client: "));
                    }
                    catch (ActivityNotFoundException error) {
                        Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_LONG).show();
                    }

                }
            }

        });
    }
}
