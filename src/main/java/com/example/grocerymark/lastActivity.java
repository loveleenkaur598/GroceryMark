package com.example.grocerymark;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class lastActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_viewlast);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Intent home = new Intent(lastActivity.this, HomeScreen.class);
                    startActivity(home);
                } else if(id == R.id.Cart){
                    Intent cart = new Intent(lastActivity.this, Cart.class);
                    startActivity(cart);
                }
                else if(id == R.id.logout){
                    logout();
                }
                return true;
            }
        });
//
        drawer = findViewById(R.id.drawerlast);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent signOut = new Intent(lastActivity.this, MainActivity.class);
        startActivity(signOut);

    }

    public void goToCart(View view) {

        Intent signOut = new Intent(lastActivity.this, Cart.class);
        startActivity(signOut);

    }


    public void goBackHome(View view) {

        Intent signOut = new Intent(lastActivity.this, HomeScreen.class);
        startActivity(signOut);

    }

}
