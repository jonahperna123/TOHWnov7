package com.example.tohwnov7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.firebase.database.ChildEventListener;
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
    int importance;

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
        String zipCodeSearch = editTextSearchZip.getText().toString();


        if (view == buttonGoToReport) {
            startActivity(goToReport);
        } else if (view == buttonSearchZip) {
            //search firebase for the DB

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("birds");

            myRef.orderByChild("zipCode").equalTo(zipCodeSearch).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                    Bird searchValue = dataSnapshot.getValue(Bird.class);

                    textViewBirdName.setText(searchValue.birdName);
                    textViewPersonName.setText(searchValue.personName);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (view == buttonLike) {

            if (zipCodeSearch.equals("")) {
                Toast.makeText(SearchActivity.this, "please enter a valid zipcode first", LENGTH_LONG).show();
            } else {

                //search firebase for the DB

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("birds");

                myRef.orderByChild("zipCode").equalTo(zipCodeSearch).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                        Bird searchValue = dataSnapshot.getValue(Bird.class);
                        String myKey = dataSnapshot.getKey();

                        textViewBirdName.setText(searchValue.birdName);
                        textViewPersonName.setText(searchValue.personName);
                        importance = searchValue.importance + 1;


                        myRef.child(myKey).child("importance").setValue(importance);

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //myRef.setValue(currentBird);
                Toast.makeText(SearchActivity.this, "importance added", Toast.LENGTH_LONG).show();


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
