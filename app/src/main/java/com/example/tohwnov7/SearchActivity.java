package com.example.tohwnov7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.*;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSearchZip;
    Button buttonSearchZip, buttonGoToReport;
    TextView textViewPersonName, textViewBirdName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearchZip = findViewById(R.id.editTextSearchZip);
        buttonSearchZip = findViewById(R.id.buttonSearchZip);
        buttonGoToReport = findViewById(R.id.buttonGoToReport);
        textViewBirdName = findViewById(R.id.textViewBirdName);
        textViewPersonName = findViewById(R.id.textViewPersonName);

        buttonGoToReport.setOnClickListener(this);
        buttonSearchZip.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent goToReport = new Intent(this, MainActivity.class);
        String zipCode = editTextSearchZip.getText().toString();


        if (view == buttonGoToReport) {
            startActivity(goToReport);
        } else if (view == buttonSearchZip) {
            //search firebase for the DB

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(zipCode);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Bird value = dataSnapshot.getValue(Bird.class);

                    textViewBirdName.setText(value.birdName);
                    textViewPersonName.setText(value.personName);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                   // Log.w(TAG, "Failed to read value.", error.toException());
                   // Toast.makeText(this, error.toString(), 15).show();
                }
            });

        }
    }
}
