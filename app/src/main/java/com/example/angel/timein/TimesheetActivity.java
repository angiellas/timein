/*
08/12/2018
Author AngieSR

NOTE: This class was based on these tutorials: https://www.simplifiedcoding.net/firebase-realtime-database-crud/
*/



package com.example.angel.timein;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimesheetActivity extends AppCompatActivity {


    public static final String CLIENT_NAME = "clientName";
    public static final String CLIENT_ID = "clientId";

    private EditText clientName;
    private Button addClient;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth    firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseClients;
    private ListView lvClients;
    private List<Clients> clientsList;
    private String userId = "";
    private String clientUserId = "";
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesheet);

        databaseClients = FirebaseDatabase.getInstance().getReference("clients");

        clientName = (EditText) findViewById(R.id.etClientName);
        addClient = (Button) findViewById(R.id.btnAddClient);
        lvClients = (ListView) findViewById(R.id.lvClients);
        clientsList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userId = user.getUid();

        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClient();
            }
        });
        lvClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Clients client = clientsList.get(position);

                Intent intent = new Intent(getApplicationContext(), AddShiftActivity.class);
                intent.putExtra(CLIENT_ID, client.getClientId());
                intent.putExtra(CLIENT_NAME, client.getClientName());

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseClients.addValueEventListener(new ValueEventListener() {
            @Override
            //executed when there's any changes on the database; fetches all values inside specified reference, all data inside datasnapshot
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientsList.clear();
                for(DataSnapshot clientSnapshot: dataSnapshot.getChildren()){
                    Clients client = clientSnapshot.getValue(Clients.class);
                    clientUserId = client.getUserId();
                    Log.i("Client with User id: " + clientUserId, "Info");
                    if (client.getUserId().equals(userId.toString())) {
                        clientsList.add(client);
                    }
                }
                ClientList adapter = new ClientList(TimesheetActivity.this, clientsList);
                lvClients.setAdapter(adapter);
            }

            @Override
            //executed when there's an error
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addClient(){
        String name = clientName.getText().toString().trim();

        if(!name.isEmpty()){
            String id = databaseClients.push().getKey();
            Clients client = new Clients(id, name, userId);
            databaseClients.child(id).setValue(client);
            Toast.makeText(this, "Client added", Toast.LENGTH_LONG).show();
            clientName.getText().clear();
        }
        else{
            Toast.makeText(TimesheetActivity.this, "You should enter a name", Toast.LENGTH_LONG).show();
        }

    }

}
