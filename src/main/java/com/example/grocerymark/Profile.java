package com.example.grocerymark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.grocerymark.Model.Product;
import com.example.grocerymark.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Profile extends AppCompatActivity {
    MaterialEditText edtName,edtAddress,edtPhone;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    User user;



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference user_table = database.getReference("User");


    FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navi_view_profile);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Intent signOut = new Intent(Profile.this, HomeScreen.class);
                    startActivity(signOut);
                }else if(id == R.id.Cart){
                    Intent cart = new Intent(Profile.this, Cart.class);
                    startActivity(cart);
                }else if(id == R.id.Stores){
                    Intent stores = new Intent(Profile.this, MapsActivity.class);
                    startActivity(stores);
                }
                else if(id == R.id.logout){
                    logout();
                }
                return true;
            }
        });

        drawer = findViewById(R.id.drawerProfile);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtAddress = (MaterialEditText)findViewById(R.id.edtAddress);

        user = new User();

        user_table.child(uid.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                edtName.setText(user.getName());
                edtAddress.setText(user.getAddress());
                edtPhone.setText(user.getPhone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent signOut = new Intent(Profile.this, MainActivity.class);
        startActivity(signOut);

    }

    public void update(View view){

        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();
        String phone = edtPhone.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(Profile.this, "Please enter the name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(address)){
            Toast.makeText(Profile.this, "Please enter the address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(Profile.this, "Please enter the phone", Toast.LENGTH_SHORT).show();
            return;
        }


        user_table.child(uid.getUid()).child("name").setValue(name);
        user_table.child(uid.getUid()).child("address").setValue(address);
        user_table.child(uid.getUid()).child("phone").setValue(phone);

        Toast.makeText(Profile.this, "User updated successfully.", Toast.LENGTH_SHORT).show();

        Intent goBack = new Intent(Profile.this, HomeScreen.class);
        startActivity(goBack);
    }

    public void goBackHome(View view){
        Intent backHome = new Intent(Profile.this, HomeScreen.class);
        startActivity(backHome);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
