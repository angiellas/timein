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

public class AvailabilityActivity extends AppCompatActivity {

    private TextView greeting;
    private TextView tellUs;
    private TextView instruction;
    private EditText userName;
    private EditText availability;
    private EditText userEmail;
    private Button submitAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        greeting = (TextView) findViewById(R.id.tvQuestion);
        tellUs = (TextView) findViewById(R.id.tvMessage);
        instruction = (TextView) findViewById(R.id.tvPlease);
        userName = (EditText) findViewById(R.id.etName);
        availability = (EditText) findViewById(R.id.etAvailabilityBox);
        userEmail = (EditText) findViewById(R.id.etEmail);
        submitAvailability = (Button) findViewById(R.id.btnSubmit);

        submitAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                String mail = userEmail.getText().toString();
                String message = availability.getText().toString();

                if (name.isEmpty() || message.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(AvailabilityActivity.this, "Please enter all the details", Toast.LENGTH_LONG).show();
                }

                else {

                    Intent submitIntent = new Intent(Intent.ACTION_SEND);
                    submitIntent.setType("message/rfc822");
                    submitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"time.in@outlook.com"});
                    submitIntent.putExtra(Intent.EXTRA_SUBJECT, "I am available to work!");
                    submitIntent.putExtra(Intent.EXTRA_TEXT,
                            "Name: " + userName.getText().toString() + '\n' + "Email: " + userEmail.getText().toString() + '\n' + "Availability: " + '\n' + availability.getText().toString());

                    try {
                        startActivity(Intent.createChooser(submitIntent, "Please, choose a client: "));
                    } catch (ActivityNotFoundException error) {
                        Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



    }
}
