package com.example.grocerymark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class HomeScreen extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // get the list of stories titles and contents in string array

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Intent signOut = new Intent(HomeScreen.this, HomeScreen.class);
                    startActivity(signOut);
                }else if(id == R.id.Cart){
                    Intent cart = new Intent(HomeScreen.this, Cart.class);
                    startActivity(cart);
                }else if(id == R.id.user){
                    Intent profile = new Intent(HomeScreen.this, Profile.class);
                    startActivity(profile);
                }
                else if(id == R.id.Stores){
                    Intent stores = new Intent(HomeScreen.this, MapsActivity.class);
                    startActivity(stores);
                }
                else if(id == R.id.logout){
                    logout();
                }
                else if(id == R.id.Orders){
                    Intent order = new Intent(HomeScreen.this, Order.class);
                    startActivity(order);
                }
                return true;
            }
        });

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);

    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent signOut = new Intent(HomeScreen.this, MainActivity.class);
        startActivity(signOut);

    }


    private void setSingleEvent(GridLayout mainGrid) {

        //Loop all child of main grid
        for(int i = 0; i<mainGrid.getChildCount();i++)
        {
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent categories = new Intent(HomeScreen.this, Categories.class);

                    switch (finalI) {
                        case 0:
                            categories.putExtra("itemType", "Vegetables");

                            startActivity(categories);
                            break;
                        case 1:
                            categories.putExtra("itemType", "Fruits");

                            startActivity(categories);
                            break;
                        case 2:
                            categories.putExtra("itemType", "Dairy");

                            startActivity(categories);
                            break;
                        case 3:
                            categories.putExtra("itemType", "Meat");

                            startActivity(categories);
                            break;
                        case 4:
                            categories.putExtra("itemType", "Frozen");

                            startActivity(categories);
                            break;
                        case 5:
                            categories.putExtra("itemType", "Bakery");

                            startActivity(categories);
                            break;
                    }
                    //Toast.makeText(HomeScreen.this,"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        if(menuItem.getItemId() == R.id.logout){
//            Toast.makeText(HomeScreen.this,"Clicked",Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}