package com.example.grocerymark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.grocerymark.Model.ItemList;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;





public class Categories extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    FirebaseListAdapter adapter;
    ItemList itemList;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // get the list of stories titles and contents in string array
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            navigationView = findViewById(R.id.navi_view);
            navigationView.setNavigationItemSelectedListener(this);

            drawer = findViewById(R.id.drawer);
            toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
            drawer.addDrawerListener(toggle);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        itemList = new ItemList();

        listView = (ListView)findViewById(R.id.listView);

        Intent itemType = getIntent();
        String type = itemType.getStringExtra("itemType");

            Toast.makeText(Categories.this, type, Toast.LENGTH_SHORT).show();


            Query query = FirebaseDatabase.getInstance().getReference().child(type);

            FirebaseListOptions<ItemList> options = new FirebaseListOptions.Builder<ItemList>()
                .setLayout(R.layout.listview_layout)
                .setQuery(query,ItemList.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name = v.findViewById(R.id.tvName);
                TextView price= v.findViewById(R.id.tvPrice);
                ImageView image = v.findViewById(R.id.imageView);

                ItemList itemList = (ItemList) model;
                name.setText(itemList.getName());
                price.setText(itemList.getPrice());
                Glide.with(image).load(itemList.getImage()).into(image);

                // Picasso.with(image).load(itemList.getImage()).into(image);

            }
        };
            listView.setAdapter(adapter);
    }


    public void onStart(){
            super.onStart();
            adapter.startListening();
    }

    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.home){
            Toast.makeText(this, "Home btn Clicked.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
