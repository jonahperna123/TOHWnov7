package com.example.tohwnov7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextBirdName, editTextPersonalName, editTextZipCode, editTextEmail;
    Button buttonSearch, buttonSubmit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        editTextBirdName = findViewById(R.id.editTextBirdName);
        editTextPersonalName = findViewById(R.id.editTextPersonalName);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        editTextEmail = findViewById(R.id.editTextEmail);

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
            String userInpEmail = editTextEmail.getText().toString();
            FirebaseUser user = mAuth.getCurrentUser();
            String email = "";
            int importance = 0;
            if (user != null) {
                email = user.getEmail();
            }
            if (!email.equals(userInpEmail)) {
                Toast.makeText(MainActivity.this, "Error, email you entered must match current user", Toast.LENGTH_LONG).show();
            } else {
                DatabaseReference myRef = database.getReference("birds");

                Bird birdEntry = new Bird(birdName, zipCode, personalName, email, importance);
                myRef.push().setValue(birdEntry);
                Toast.makeText(MainActivity.this, "Bird entered", Toast.LENGTH_LONG).show();
            }




        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nav, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.menuSearch){
            Intent goToSearch = new Intent(this, SearchActivity.class);
            startActivity(goToSearch);
        } else if (item.getItemId() == R.id.menuGoToBird) {
           //do nothing, already on bird report
        } else if (item.getItemId() == R.id.menuLogout) {
            Intent logoutIntent = new Intent(this, loginActivity.class);
            startActivity(logoutIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
