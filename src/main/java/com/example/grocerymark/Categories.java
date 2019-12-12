package com.example.grocerymark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerymark.Model.Product;

import com.bumptech.glide.Glide;
import com.example.grocerymark.Model.ItemList;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class Categories extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    FirebaseListAdapter adapter;
    ItemList itemList;
    TextView categories;

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

        categories = (TextView)findViewById(R.id.categories);
        categories.setText(type);

        Toast.makeText(Categories.this, type, Toast.LENGTH_SHORT).show();

        Query query = FirebaseDatabase.getInstance().getReference().child(type);

        FirebaseListOptions<ItemList> options = new FirebaseListOptions.Builder<ItemList>()
                .setLayout(R.layout.listview_layout)
                .setQuery(query,ItemList.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model,final int position) {
                TextView name = v.findViewById(R.id.tvName);
                TextView price= v.findViewById(R.id.tvPrice);
                ImageView image = v.findViewById(R.id.imageView);
                Button sub = v.findViewById(R.id.button7);
                Button add = v.findViewById(R.id.button6);
                Button addToCart = v.findViewById(R.id.button8);
                final TextView itemCount = v.findViewById(R.id.textView4);
                final ItemList itemList = (ItemList) model;
                name.setText(itemList.getName());
                price.setText(itemList.getPrice());
                Glide.with(image).load(itemList.getImage()).into(image);
                // Picasso.with(image).load(itemList.getImage()).into(image);


                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String count = itemCount.getText().toString();
                        int present_value_int = Integer.parseInt(count);
                        if(present_value_int != 0){
                            final String itemName = itemList.getName();
                            String Price = itemList.getPrice();
                            int itemPrice = (int) Double.parseDouble(String.valueOf(Double.parseDouble(Price) * Double.parseDouble(String.valueOf(present_value_int))));
                            final String totalPrice = String.valueOf("$" + itemPrice);
                            final String itemImage = itemList.getImage();

                            final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                            final String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                            final String itemCount = String.valueOf(present_value_int);
                            final int random = new Random().nextInt(20000) + 10000;

                            Toast.makeText(Categories.this, itemName+" "+itemImage+" "+itemCount+" "+
                                    totalPrice+" "+currentDate+" "+currentTime, Toast.LENGTH_SHORT).show();


                            //Init Firebase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference table_product = database.getReference("Product");


                            table_product.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(String.valueOf(random)).exists())
                                    {
                                        //mDialog.dismiss();
                                        //Toast.makeText(SignUp.this, "Phone Number already register !", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser() ;
                                          Product product = new Product (itemName,itemImage,itemCount,
                                                totalPrice,currentDate,currentTime);
                                        table_product.child(uid.getUid()).child(String.valueOf(random)).setValue(product);
                                        //Toast.makeText(Categories.this, itemName+" "+itemImage+" "+itemCount+" "+
                                               // totalPrice+" "+currentDate+" "+currentTime, Toast.LENGTH_SHORT).show();

                                        Intent nextPage = new Intent(Categories.this,lastActivity.class);
                                        startActivity(nextPage);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });





                        }else{
                            Toast.makeText(Categories.this, "Please select item count.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String count = itemCount.getText().toString();
                        int present_value_int = Integer.parseInt(count);

                        if(present_value_int != 0){
                            present_value_int--;
                            itemCount.setText(String.valueOf(present_value_int));
                        }
                        //Toast.makeText(Categories.this, count, Toast.LENGTH_SHORT).show();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String count = itemCount.getText().toString();
                        int present_value_int = Integer.parseInt(count);
                        present_value_int++;
                        itemCount.setText(String.valueOf(present_value_int));
                        //Toast.makeText(Categories.this, "hi from add", Toast.LENGTH_SHORT).show();
                    }
                });
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
