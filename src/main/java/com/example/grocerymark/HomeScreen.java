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

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();



        mainGrid = (GridLayout)findViewById(R.id.mainGrid);


        //Set Event
        setSingleEvent(mainGrid);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.home){
            Toast.makeText(this, "Home btn Clicked.", Toast.LENGTH_SHORT).show();
        }
        return true;
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
}