package com.example.grocerymark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar;
        DrawerLayout drawer;
        ActionBarDrawerToggle toggle;
        NavigationView navigationView;

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_product = database.getReference("Product");

        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view_order);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Intent signOut = new Intent(Order.this, HomeScreen.class);
                    startActivity(signOut);
                }else if(id == R.id.Cart){
                    Intent cart = new Intent(Order.this, Cart.class);
                    startActivity(cart);
                }else if(id == R.id.user){
                    Intent profile = new Intent(Order.this, Profile.class);
                    startActivity(profile);
                }else if(id == R.id.Orders){
                    Intent order = new Intent(Order.this, Order.class);
                    startActivity(order);
                }
                else if(id == R.id.Stores){
                    Intent stores = new Intent(Order.this, MapsActivity.class);
                    startActivity(stores);
                }
                else if(id == R.id.logout){
                    logout();
                }
                return true;
            }
        });

        drawer = findViewById(R.id.drawerOrder);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent signOut = new Intent(Order.this, MainActivity.class);
        startActivity(signOut);

    }
}
