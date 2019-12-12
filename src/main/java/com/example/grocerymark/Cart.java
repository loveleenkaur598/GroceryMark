package com.example.grocerymark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.grocerymark.Model.Product;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class Cart extends AppCompatActivity {

    ListView listView;
    FirebaseListAdapter adapter;
    Product product;
    TextView categories;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // get the list of stories titles and contents in string array
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_viewCart);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Intent signOut = new Intent(Cart.this, HomeScreen.class);
                    startActivity(signOut);
                }else if(id == R.id.Cart){
                    Intent cart = new Intent(Cart.this, Cart.class);
                    startActivity(cart);
                }
                else if(id == R.id.logout){
                    //logout();
                }
                return true;
            }
        });

        drawer = findViewById(R.id.drawerCart);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        product = new Product();

        listView = (ListView)findViewById(R.id.listCart);

        //categories = (TextView)findViewById(R.id.categories);

        //Toast.makeText(Cart.this, type, Toast.LENGTH_SHORT).show();

        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser() ;
        Query query = FirebaseDatabase.getInstance().getReference().child("Product").child(uid.getUid());

        FirebaseListOptions<Product> options = new FirebaseListOptions.Builder<Product>()
                .setLayout(R.layout.cartview_layout)
                .setQuery(query,Product.class)
                .build();
        final List<Integer> total = new ArrayList<Integer>();

        // Add to end of the list
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model,final int position) {
                TextView name = v.findViewById(R.id.tvName);
                TextView price= v.findViewById(R.id.tvPrice);
                TextView date= v.findViewById(R.id.textView4);
                Button deleteEntry = v.findViewById(R.id.deleteEntry);

                ImageView image = v.findViewById(R.id.imageView);
                final Product product = (Product) model;
                name.setText(product.getProductName());
                price.setText("$" + product.getPrice());
                total.add(Integer.parseInt(product.getPrice()));

                date.setText(product.getDate());
                Glide.with(image).load(product.getProductImage()).into(image);

                deleteEntry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference del = FirebaseDatabase.getInstance().getReference().
                                child("Product").child(uid.getUid()).child(adapter.getRef(position).getKey());

                        del.removeValue();
                        Toast.makeText(getApplicationContext(),"Item removed", Toast.LENGTH_LONG).show();

                    }
                });
            }
        };

        listView.setAdapter(adapter);

        //Toast.makeText(getApplicationContext(),String.valueOf(total[0]) , Toast.LENGTH_LONG).show();
    }

    public void checkOut(View view){
        Intent checkOut = new Intent(Cart.this, CheckOut.class);
        startActivity(checkOut);
    }


    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
