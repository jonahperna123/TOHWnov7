package com.example.tohwnov7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

            // Get a reference to our posts
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("tohw4-6edf7/" + zipCode);

        }
    }
}
