package com.example.hwk3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private List<Restaurant> mRestaurants = new ArrayList<>();
    private List<Restaurant> filteredRestaurants = new ArrayList<>();
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;


    private float rating = 0;

    private ActivityResultLauncher<Intent> otherActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        setUpRecycler();

        otherActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 0) {
                            Intent resultIntent = result.getData();
                            if (resultIntent != null) {
                                rating = resultIntent.getExtras().getFloat("rating");
                                filterRestaurants();
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuItemAddButton:
                 intent = new Intent(RestaurantListActivity.this, AddRestaurant.class);
                otherActivityLauncher.launch(intent);
                return true;
            case R.id.menuItemFilter:
                intent = new Intent(RestaurantListActivity.this,FilterList.class);
                otherActivityLauncher.launch(intent);
                return true;
            case R.id.menuItemClearFilter:
                rating = 0;
                adapter.setData(mRestaurants);
                return true;
            case R.id.menuItemInfo:
                intent = new Intent(RestaurantListActivity.this, AppInfo.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void filterRestaurants() {
        filteredRestaurants.clear();
        for (int i = 0; i < mRestaurants.size();i++) { // go through entire current list
            if (mRestaurants.get(i).getRating() >= rating) {
                filteredRestaurants.add(mRestaurants.get(i)); // add it if meets minimum
            }
        }
        adapter.setData(filteredRestaurants); // set data to new filtered list
    }

    void setUpRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRestaurants);
        storeDataInArray();
        adapter = new RestaurantAdapter(mRestaurants);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeDataInArray();

    }

    // gets fresh values from database and resets recycler
    void storeDataInArray() {
        db = FirebaseFirestore.getInstance();
        db.collection("Hwk3Restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mRestaurants.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Restaurant temp = new Restaurant(); // will hold temp Restaurant obj. adding it to list
                                temp.setName(document.getString("name"));
                                temp.setLocation(document.getString("location"));
                                temp.setRating((document.getDouble("rating")));
                                mRestaurants.add(temp);
                            }

                            if (rating > 0) { // If there is a filter applied, use it
                                filterRestaurants();
                            } else {
                                adapter.setData(mRestaurants);
                            }

                        } else {
                            Log.w("MYDEBUG", "Error getting documents.", task.getException());
                        }

                    }
                });// End of onComplete
    }
}