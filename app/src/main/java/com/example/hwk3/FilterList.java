package com.example.hwk3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class FilterList extends AppCompatActivity {
    RatingBar ratingBarFilter;
    Button buttonFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_list);

        ratingBarFilter = findViewById(R.id.ratingBarFilter);
        buttonFilter = findViewById(R.id.buttonFilter);

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBarFilter.getRating() == 0) {
                    Toast.makeText(getApplicationContext(),"Please Enter Rating", Toast.LENGTH_SHORT).show();
                } else {
                    Intent resultIntent = new Intent();
                    float rating = ratingBarFilter.getRating();
                    resultIntent.putExtra("rating",rating);
                    setResult(0, resultIntent);
                    finish();
                }
            }
        });


    }
}