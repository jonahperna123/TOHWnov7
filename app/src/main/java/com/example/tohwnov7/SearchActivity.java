package com.example.tohwnov7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    Button buttonSearchZip, buttonGoToReport, buttonLike;
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
        buttonLike = findViewById(R.id.buttonLike);

        buttonGoToReport.setOnClickListener(this);
        buttonSearchZip.setOnClickListener(this);
        buttonLike.setOnClickListener(this);

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
                }
            });

        } else if (view == buttonLike) {

            if (zipCode.equals("")) {
                Toast.makeText(SearchActivity.this, "please enter a valid zipcode first", LENGTH_LONG).show();
            } else {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference(zipCode);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Bird value = dataSnapshot.getValue(Bird.class);

                        textViewBirdName.setText(value.birdName);
                        textViewPersonName.setText(value.personName);
                        int importance = value.importance;
//                        ++importance;
//                        Bird updatedBird = new Bird(value.birdName, value.zipCode, value.personName, value.email, importance);
//                        myRef.setValue(updatedBird);
//                        Toast.makeText(SearchActivity.this, "importance added", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });



            }
        }
    } //onClick()


    //menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nav, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.menuSearch){
            //do nothing already on search menu
        } else if (item.getItemId() == R.id.menuGoToBird) {
            Intent reportIntent = new Intent(this, MainActivity.class);
            startActivity(reportIntent);
        } else if (item.getItemId() == R.id.menuLogout) {
            Intent logoutIntent = new Intent(this, loginActivity.class);
            startActivity(logoutIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
