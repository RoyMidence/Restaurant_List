package com.example.hwk3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRestaurant extends AppCompatActivity {
    private FirebaseFirestore db;

    EditText editTextName, editTextLocation;
    RatingBar ratingBarAddRestaurant;
    Button buttonAddRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        ratingBarAddRestaurant = findViewById(R.id.ratingBarAddRestaurant);
        buttonAddRestaurant = findViewById(R.id.buttonAddRestaurant);

        buttonAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText().toString().trim().equals("") ||
                editTextLocation.getText().toString().trim().equals("") ||
                ratingBarAddRestaurant.getRating() == 0) {
                    Toast.makeText(getApplicationContext(),"Fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    db = FirebaseFirestore.getInstance();

                    Map<String, Object> data = new HashMap<>();
                    data.put("name", editTextName.getText().toString().trim());
                    data.put("location", editTextLocation.getText().toString().trim());
                    data.put("rating", ratingBarAddRestaurant.getRating());

                    Task<DocumentReference> addedDocRef =
                            db.collection("Hwk3Restaurants")
                                    .add(data)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        // Code for successfully adding goes here…
                                        finish();
                                    }
                            });

                }
            }
        });

    }
}