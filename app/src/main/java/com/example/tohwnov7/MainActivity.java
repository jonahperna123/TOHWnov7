package com.example.tohwnov7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextBirdName, editTextPersonalName, editTextZipCode;
    Button buttonSearch, buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBirdName = findViewById(R.id.editTextBirdName);
        editTextPersonalName = findViewById(R.id.editTextPersonalName);
        editTextZipCode = findViewById(R.id.editTextZipCode);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSubmit = findViewById(R.id.submitButton);

        buttonSearch.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        Intent goToSearch = new Intent(this, SearchActivity.class);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Toast.makeText(MainActivity.this, (database.getReference("birds")).toString(), Toast.LENGTH_SHORT).show();

        if (view == buttonSearch) {
            //go to search Intent
            startActivity(goToSearch);
        } else if (view == buttonSubmit) {
            String birdName = editTextBirdName.getText().toString();
            String personalName = editTextPersonalName.getText().toString();
            String zipCode = editTextZipCode.getText().toString();
            DatabaseReference myRef = database.getReference(zipCode);

            Bird birdEntry = new Bird(birdName, zipCode, personalName);
            myRef.setValue(birdEntry);



        }

    }
}
